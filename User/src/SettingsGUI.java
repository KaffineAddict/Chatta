
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Vector;

/**
 *
 * @author Munky
 */
public class SettingsGUI extends JFrame {
    public String FontFace = "arial";
    public String FontColor = "black";
    public boolean SoundOn = false;
    
    JComboBox fonts;
    JComboBox colors;
    GraphicsEnvironment gEnv;
    Vector fontV; 
    Panel panel; 
    JLabel FLabel;
    JLabel CLabel;
    JCheckBox sound;
    
    
    SettingsGUI(String title) {
     super(title);
     addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent we) {
             hideMe();
         }
     });    
     
     
     panel = new Panel(new GridBagLayout());
     GridBagConstraints c = new GridBagConstraints();
     c.fill = GridBagConstraints.HORIZONTAL;
     
     FLabel = new JLabel("Select Your Font: ");
     c.gridx = 0;
     c.gridy = 0;
     c.gridwidth = 1;
     c.gridheight = 1;
     c.weighty = 0;
     panel.add(FLabel, c);     
     
     
     gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
     String envfonts[] = gEnv.getAvailableFontFamilyNames();
     fontV = new Vector();
     for (int i = 1; i < envfonts.length; i++) {
       fontV.addElement(envfonts[i]);
     }
          
     fonts = new JComboBox(fontV);     
     c.fill = GridBagConstraints.HORIZONTAL;
     c.gridx = 1;
     c.gridy = 0;
     c.gridwidth = 2;
     c.gridheight = 1;
     c.weighty = 1.0;
     panel.add(fonts, c);
     
     CLabel = new JLabel("Select A Color: ");
     c.gridx = 0;
     c.gridy = 1;
     c.gridwidth = 1;
     c.gridheight = 1;
     c.weighty = 0;
     panel.add(CLabel, c); 
     
     String items[] = new String[] {
         "black", "red", "green", "blue", "yellow", "aqua", "fuchsia",
         "gray", "lime", "maroon", "purple", "navy", "olive", "silver", 
         "teal"
     };
     
     colors = new JComboBox(items);     
     c.fill = GridBagConstraints.HORIZONTAL;
     c.gridx = 1;
     c.gridy = 1;
     c.gridwidth = 2;
     c.gridheight = 1;
     c.weighty = 1.0;
     panel.add(colors, c);
     
     sound = new JCheckBox("Sound Alerts");
     c.fill = GridBagConstraints.HORIZONTAL;
     c.gridx = 0;
     c.gridy = 2;
     c.gridwidth = 1;
     c.gridheight = 1;
     c.weighty = 1.0;
     panel.add(sound, c);

     
     this.setDefaultCloseOperation(HIDE_ON_CLOSE);
     this.add(panel);
     Dimension d = new Dimension();
     d.height = 200;
     d.width = 300;
     this.setMinimumSize(d);
    }
    
    public void hideMe(){        
        FontFace = fonts.getSelectedItem().toString();
        FontColor = colors.getSelectedItem().toString();
        SoundOn = sound.isSelected();
    }
          
}