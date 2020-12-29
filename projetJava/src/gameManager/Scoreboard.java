/*
 * @author ALVESYohann_VIALLEHugo
 * @version 17
 */
package gameManager;

import java.awt.Font;
import java.awt.GridLayout;
import java.io.*;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Scoreboard extends JPanel {
	private final static String filePath = "./src/scoreboard.txt";
  /**
   * Instantiates a new scoreboard.
   * Everytime a player finish his game, his score and the parameters chosen
   * are saved in the scoreboard.txt file by writing it on the last line
   * source : https://fr.wikiversity.org/wiki/Java/Gestion_de_fichiers
   */
  public Scoreboard() {
    File fichier = new File(filePath);
    int car;
    StringBuffer contenu = new StringBuffer("");
    FileInputStream ftemp = null;
    try {
      ftemp = new FileInputStream(fichier);
      while ((car = ftemp.read()) != -1)
      contenu.append((char) car);
      ftemp.close();
    }
    catch(FileNotFoundException e) {
      System.out.println("Fichier non trouve. Veuillez changer le chemin d'acc�s");
    }
    catch(IOException ioe) {
      System.out.println("Exception " + ioe);
    }

    this.setLayout(new GridLayout(11, 0));

    JLabel l2 = new JLabel("RECENT SCORES ON THIS APP :");
    l2.setFont(new Font("Sans-Serif", Font.BOLD, 17));
    this.add(l2);
    String contenuByLine[] = contenu.toString().split("\n");
    for (int i = contenuByLine.length; i > contenuByLine.length - 10; i--) {
      JLabel scoreByLine = new JLabel(contenuByLine[i - 1].toString());
      scoreByLine.setFont(new Font("Sans-Serif", Font.PLAIN, 13));
      this.add(scoreByLine);
    }
  }
}