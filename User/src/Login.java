import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Munky
 */
public class Login extends JFrame implements ActionListener {
    
    Panel panel; 
    JLabel ELabel;
    JLabel PLabel;
    JLabel Fail;
    JTextField Email;
    JTextField Password;
    JCheckBox Register;
    JButton Login;
    String IP = "72.250.140.22";
    int portNum = 4816;
    Socket s;
    boolean didConnect = false;
    public BufferedReader br;
    public PrintWriter pw;
    public boolean LoggedIn = false;
    public boolean showChat = false;
    public String userName = "";
    String LIC;
    
    Login(String title) {
     super(title);
     addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent we) {
             System.exit(0);
         }
     });    
     
     
     panel = new Panel(new GridBagLayout());
     GridBagConstraints c = new GridBagConstraints();
     c.fill = GridBagConstraints.HORIZONTAL;
     
     Fail = new JLabel("Login To Chata");
     c.fill = GridBagConstraints.HORIZONTAL;
     c.gridx = 0;
     c.gridy = 0;
     c.gridwidth = 1;
     c.gridheight = 1;
     c.weighty = 1.0;
     c.weightx = 1.0;
     panel.add(Fail, c);
     
     ELabel = new JLabel("Username: ");   
     c.gridx = 0;
     c.gridy = 1;
     c.gridwidth = 3;
     c.gridheight = 1;
     c.weighty = 1.0;
     c.weightx = 1.0;
     panel.add(ELabel, c);  
     
     Email = new JTextField();
     c.gridx = 0;
     c.gridy = 2;
     c.gridwidth = 3;
     c.gridheight = 1;
     c.weighty = 1.0;
     c.weightx = 1.0;
     panel.add(Email, c);
     
     PLabel = new JLabel("Password: ");
     c.gridx = 0;
     c.gridy = 3;
     c.gridwidth = 3;
     c.gridheight = 1;
     c.weighty = 1.0;
     c.weightx = 1.0;
     panel.add(PLabel, c); 
     
     Password = new JTextField();
     c.gridx = 0;
     c.gridy = 4;
     c.gridwidth = 3;
     c.gridheight = 1;
     c.weighty = 1.0;
     c.weightx = 1.0;
     panel.add(Password, c); 
     
     Register = new JCheckBox("Create User");
     Register.addActionListener(this);
     c.fill = GridBagConstraints.NONE;
     c.gridx = 0;
     c.gridy = 5;
     c.gridwidth = 1;
     c.gridheight = 1;
     c.weighty = 1.0;
     c.weightx = 1.0;
     panel.add(Register, c);
     
     Login = new JButton("Login");
     Login.addActionListener(this);
     c.fill = GridBagConstraints.NONE;
     c.gridx = 0;
     c.gridy = 6;
     c.gridwidth = 1;
     c.gridheight = 1;
     c.weighty = 1.0;
     c.weightx = 1.0;
     panel.add(Login, c);
     
    
    this.add(panel);
    Dimension d = new Dimension();
    d.height = 250;
    d.width = 250;
    this.setMinimumSize(d);
    }

    public void actionPerformed(ActionEvent e) {

        if(e.getActionCommand().equals("Login")){
            Login.setEnabled(false);
            Login.setText("Logging in...");
            connect(IP, portNum);
            sendData();
            try {
                LIC = br.readLine();
            } catch (IOException ex) { System.out.println(ex); }
            if(LIC.equals("SUCCESS")){
                LoggedIn = true;
                userName = Email.getText();   
                try {
                    load();
                } catch (Exception ex) {}
            } else {
                LoggedIn = false;
                Login.setEnabled(true);
                Login.setText("Login");
                Fail.setText("Bad Username Or Password");                   
            }                           
        }
        
        if(e.getActionCommand().equals("Register")){
            Login.setEnabled(false);
            Login.setText("Registering...");
            connect(IP, portNum);
            sendReg();
            try {
                LIC = br.readLine();
                System.out.println(LIC);
            } catch (IOException ex) { System.out.println(ex); }
            if(LIC.equals("SUCCESS")){
                Email.setText("");
                Password.setText("");
                Fail.setText("User Created You May Login");
                Login.setText("Login"); 
                Register.setSelected(false);
                Login.setEnabled(true);
            } else { 
                Fail.setText("Name In Use Or Other Error");
                Login.setEnabled(true);
                Register.setSelected(false);
                Email.setText("");
                Password.setText("");
                Login.setText("Login");       
            }
        }
        
        if(e.getActionCommand().equals("Create User")){
            if(Register.isSelected()) {
               Login.setText("Register");
            } else {
               Login.setText("Login");
            }                       
        }
        
    }
    
    public void connect(String address, int port){
        try {
       if(!didConnect){
         s = new Socket(address, port);      
         InputStream is = s.getInputStream();
         InputStreamReader isr = new InputStreamReader(is);
         br = new BufferedReader(isr);
         OutputStream os = s.getOutputStream();
         pw = new PrintWriter(os, true);
         didConnect=true;
       }
     }
     catch(Exception e){ System.out.println(e); }
    }

    private void sendData() {
        pw.println("LOGIN#" + Email.getText() + "#" + Password.getText());
    }
            
    private void sendReg() {
        pw.println("REGISTER#" + Email.getText()  + "#" + Password.getText());
    }
    
    public void load() throws IOException, Exception{
        ClientCode cc = new ClientCode("Goetu Chata");
        cc.setSize(300, 250);
        cc.init();
        this.setVisible(false);
    }
        
    
}
