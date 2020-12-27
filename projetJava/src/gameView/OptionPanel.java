package gameView;



import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;  



@SuppressWarnings("serial")
public class OptionPanel extends JPanel{

	private JFrame f; 
	private String username;
	private String gameType;
	private String playerType;
	
	public OptionPanel(){  
		
	    f=new JFrame();   
	    username=JOptionPane.showInputDialog(f,"Enter username");  
	             
	    //... Text to put on the buttons.
        String[]  choices = {
            "5T Mode", 
            "5D Mode"
            };

            int response = JOptionPane.showOptionDialog(
                              f                      // Center in window.
                             , "Select your mode"        // Message
                             , "Mode selection"               // Title in titlebar
                             , JOptionPane.YES_NO_OPTION  // Option type
                             , JOptionPane.PLAIN_MESSAGE  // messageType
                             , null                       // Icon (none)
                             , choices                    // Button text as above.
                             , ""    // Default button's label
                           );
            
            switch (response) {
                case 0: 
                    gameType = "FIVET";
                    break;
                    
                case 1:
                	gameType = "FIVED";
                    break;
                
                case -1:
                    System.exit(0);
                    
                default:
                    JOptionPane.showMessageDialog(null, "Unexpected response " + response);
            }
            
         
            
            MorpionSolitaire panel = new MorpionSolitaire(username,gameType,"COMPUTER");
            JFrame frame = new JFrame();
            frame.add(panel,BorderLayout.CENTER);
            frame.setTitle("Morpion solitaire");
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

         
    }
}
