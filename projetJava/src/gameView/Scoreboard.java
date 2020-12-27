package gameView;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Scoreboard extends JPanel{
	public Scoreboard() {
		// source : https://fr.wikiversity.org/wiki/Java/Gestion_de_fichiers
		File fichier = new File("/src/scoreboard.txt");
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

		this.setLayout(new GridLayout(11,0)); 

		JLabel l2 = new JLabel("RECENT SCORES ON THIS APP :"); 
		l2.setFont(new Font("Sans-Serif", Font.BOLD, 17));
		this.add(l2);
		String contenuByLine[] = contenu.toString().split("\n");
		for (int i = contenuByLine.length; i > contenuByLine.length-10; i--) {
			JLabel scoreByLine = new JLabel(contenuByLine[i-1].toString());
			scoreByLine.setFont(new Font("Sans-Serif", Font.PLAIN, 13));
			this.add(scoreByLine);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		// better graphics for the window (see https://docs.oracle.com/javase/tutorial/2d/advanced/quality.html)
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 

	}
}
