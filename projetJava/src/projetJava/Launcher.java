package projetJavaTESTLINES;
import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Launcher {
	
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        MorpionSolitaire panel = new MorpionSolitaire();
        frame.add(panel,BorderLayout.CENTER);
        frame.setTitle("Morpion solitaire");
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }	
}
