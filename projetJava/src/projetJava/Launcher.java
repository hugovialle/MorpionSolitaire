package projetJava;

import java.awt.BorderLayout;


import javax.swing.JFrame;

import projetJava.MorpionSolitaire.typeGame;

public class Launcher {
	
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        MorpionSolitaire panel = new MorpionSolitaire(typeGame.FIVET);
        //OptionPanel option = new OptionPanel();
        frame.add(panel,BorderLayout.CENTER);
        frame.setTitle("Morpion solitaire");
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }	
}
