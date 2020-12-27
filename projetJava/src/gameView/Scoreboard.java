package gameView;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class Scoreboard extends JPanel{
	public Scoreboard() {
		
	}
	// source : https://fr.wikiversity.org/wiki/Java/Gestion_de_fichiers
	@SuppressWarnings("static-access")
	public void readScoreboard() {
		File fichier = new File("src/scoreboard.txt");
	    int car;
	    StringBuffer contenu = new StringBuffer("");
	    FileInputStream ftemp = null;
	    try {
	      ftemp = new FileInputStream(fichier);
	      while( (car = ftemp.read()) != -1)
	        contenu.append((char)car);
	      ftemp.close();
	    }
	    catch(FileNotFoundException e) {
	      System.out.println("Fichier introuvable");
	    }
	    catch(IOException ioe) {
	      System.out.println("Exception " + ioe);
	    }
	    System.out.println(contenu);
	    
	    
	    
	    
	    
	    
	    
	   
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		// better graphics for the window (see https://docs.oracle.com/javase/tutorial/2d/advanced/quality.html)
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
		   
	}
}
