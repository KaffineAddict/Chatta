/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Blakerz
 */

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;



public class ClientCode extends JFrame implements ActionListener, Runnable {
 JTextField tf;
 JEditorPane ep;
 JScrollPane sp;
 BufferedReader br;
 PrintWriter pw;
 Boolean nameSet=false;
 Boolean justSet=true;
 Boolean firstTime=true;
 Clip msgSound = null;
 String FontColor = "black";
 URL msgSoundPath = ClassLoader.getSystemClassLoader().getResource("sounds/msg.wav");
 AudioInputStream ais;
 JPanel panel; 
 SettingsGUI SGUI;
 static Login Login;
 JMenuBar menuBar;
 JMenu menu;
 JMenuItem menuItem;
 Boolean LoggedIn = false;
 static ClientCode cc;

 
 //load emotes
 String smile = ClassLoader.getSystemClassLoader().getResource("emotes/smileface.gif").toString();
 String angry = ClassLoader.getSystemClassLoader().getResource("emotes/angry.gif").toString();
 String bigsmile = ClassLoader.getSystemClassLoader().getResource("emotes/biggrin.gif").toString();
 String blink = ClassLoader.getSystemClassLoader().getResource("emotes/blink.gif").toString();
 String blushing = ClassLoader.getSystemClassLoader().getResource("emotes/blushing.gif").toString();
 String bored = ClassLoader.getSystemClassLoader().getResource("emotes/bored.gif").toString();
 String confused = ClassLoader.getSystemClassLoader().getResource("emotes/confused1.gif").toString();
 String cool = ClassLoader.getSystemClassLoader().getResource("emotes/cool.gif").toString();
 String crying = ClassLoader.getSystemClassLoader().getResource("emotes/crying.gif").toString();
 String cursing = ClassLoader.getSystemClassLoader().getResource("emotes/cursing.gif").toString();
 String tongue = ClassLoader.getSystemClassLoader().getResource("emotes/tongue.gif").toString();
 
//72.250.140.22
 
  public static void main(String args[]){
       Login = new Login("Login To Chata");    
       Login.setVisible(true);
  }
  
  ClientCode(String title) throws IOException, Exception {
     super(title);
     addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent we) {
             System.exit(0);        
         }
     });        
            
     //init sounds
     try {             
     ais = AudioSystem.getAudioInputStream(msgSoundPath);
     msgSound = AudioSystem.getClip();
     msgSound.open(ais);
     } catch (Exception e) { }
     
     //init options
     SGUI = new SettingsGUI("Options");
     SGUI.setSize(300, 200);
     SGUI.setVisible(false);
     
     //init login
//     Login = new Login("Login To Chata");    
//     Login.setVisible(true);
     
     //init panel
     panel = new JPanel(new GridBagLayout());
     GridBagConstraints c = new GridBagConstraints();
     c.fill = GridBagConstraints.HORIZONTAL;

     
     ep = new JEditorPane();
     ep.setEditorKit(new HTMLEditorKit());
     sp = new JScrollPane(ep);
     ep.setEditable(false);
     ep.setContentType("text/html");
     ep.addHyperlinkListener(new HyperlinkListener() {
            public void hyperlinkUpdate(HyperlinkEvent r) {
             if(r.getEventType() == HyperlinkEvent.EventType.ACTIVATED){
                BareBonesBrowserLaunch.openURL(r.getURL().toString());
            }
          }
        });

     c.fill = GridBagConstraints.BOTH;
     c.gridx = 0;
     c.gridy = 0;
     c.gridwidth = 3;
     c.gridheight = 2;
     c.weighty = 1.0;
     panel.add(sp, c);
     
     tf = new JTextField("", 25);
     tf.addActionListener(this);
     c.fill = GridBagConstraints.HORIZONTAL;
     c.gridx = 0;
     c.gridy = 2;
     c.gridwidth = 3;
     c.gridheight = 1;
     c.weightx = 1.0;
     c.weighty = 0.0;
     panel.add(tf, c);
     
     this.add(panel);
     Dimension d = new Dimension();
     d.height = 250;
     d.width = 300;
     this.setMinimumSize(d);
     
     menuBar = new JMenuBar();
     menu = new JMenu("File");      
     menuBar.add(menu);
     menuItem = new JMenuItem("Options");
     menuItem.addActionListener(this);
     menu.add(menuItem);
     menuItem = new JMenuItem("Exit");
     menuItem.addActionListener(this);
     menu.add(menuItem);
     
     this.setJMenuBar(menuBar); 
  }

      
   
  public void actionPerformed(ActionEvent ae) {
      
      try {   
          String str = tf.getText(); 
          
       if(ae.getActionCommand().equals("Options") || ae.getActionCommand().equals("Exit")){
           if(ae.getActionCommand().equals("Options")){
               SGUI.setVisible(true);
           }
           
           
           if(ae.getActionCommand().equals("Exit")){
               System.exit(0); 
           }
           
       } else {
              
          if(str.equalsIgnoreCase("exit")){
             str = null;
             Login.pw.println("USER_EXIT");
             Login.pw.println("User left the chat client.");
             System.exit(0);
          } 
          
          
          if(str.equalsIgnoreCase("users")){
             str = null;
             tf.setText("");
             Login.pw.println("NUM_USERS");
          }  
          
          if(str.equalsIgnoreCase("*options*")){
             str = null;
             tf.setText("");
             SGUI.setVisible(true);
          }
          
          if(str.equalsIgnoreCase("names")){
             str = null;
             tf.setText("");
             Login.pw.println("NAMES");
          } 
          
          if(str.startsWith("fontcolor")){
             String[] color = str.split("-");
             str = null;
             FontColor = color[1];
             tf.setText("");
          }                                                
              
                  if(!str.isEmpty()){
                    str = "<b>" + Login.userName + ":</b> " + "<font face=\"" + SGUI.FontFace + "\" color=\"" + SGUI.FontColor + "\">" + str + "</font>";
                    Login.pw.println(str);
                    tf.setText("");
                  }              
        }
      }
      catch(Exception e){ 
            try { } catch (Exception ex) { }
     }
  }
  
 
 public void init(){
     this.setVisible(true);
     Thread thread = new Thread(this);
     thread.start();
 }
  
  public void run() {     
     while(true){
      try {                        
//              if(Login.showChat){
//                  showThis();
//                  Login.pw.println("User joined the chat client.");
//                  Login.showChat = false;
//              }
              if(Login.LoggedIn){
              String str = Login.br.readLine();
              //replace emotes with images...
              str = str.replace(":)", "<img src=" + smile + " />");
              str = str.replace(":D", "<img src=" + bigsmile + " />");
              str = str.replace(">(", "<img src=" + angry + " />");
              str = str.replace("o_O", "<img src=" + blink + " />");
              str = str.replace(":$", "<img src=" + blushing + " />");
              str = str.replace(":|", "<img src=" + bored + " />");
              str = str.replace("?_?", "<img src=" +confused + " />");
              str = str.replace("8)", "<img src=" + cool + " />");
              str = str.replace(":'(", "<img src=" + crying + " />");
              str = str.replace("****", "<img src=" + cursing + " />");
              str = str.replace("=p", "<img src=" + tongue + " />");
              str = str.replace(":p", "<img src=" + tongue + " />");
              
              
              Document doc = (Document) ep.getDocument();           
              
              ((HTMLEditorKit)ep.getEditorKit()).read(new java.io.StringReader ("<html><body>"+str+"</body></html>"),ep.getDocument(), ep.getDocument().getLength());
              ep.setCaretPosition(doc.getLength());                    
              
              if(SGUI.SoundOn){
               if (msgSound.isRunning()){
                   msgSound.stop();   // Stop the player if it is still running
               }              
                   msgSound.setFramePosition(0); // rewind to the beginning
                   msgSound.start();     // Start playing
              }

              
              if(!this.isFocused()){
                  this.setVisible(true);
              }        
      } 
      }
      catch(Exception e){ 
                try { } catch (Exception ex) { }
      }     
      
   } 
  }
}
