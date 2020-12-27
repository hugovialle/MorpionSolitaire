package gameView;

import java.awt.BorderLayout;



import javax.swing.JFrame;

public class Launcher {
	
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        String username = "Yohann";
        String mode = "FIVET";
        String playerType = "COMPUTER";
        OptionPanel panel = new OptionPanel();
        //MorpionSolitaire panel = new MorpionSolitaire(username,mode,playerType);
        
        /*frame.add(panel,BorderLayout.CENTER);
        frame.setTitle("Morpion solitaire");
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        JFrame frame2 = new JFrame();
        frame2.setLayout(new BorderLayout());
        Scoreboard panel2 = new Scoreboard();
        frame.add(panel2,BorderLayout.CENTER);
        frame2.setTitle("Morpion solitaire - Scoreboard");
        frame2.pack();
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setVisible(true);*/
    }	
}
