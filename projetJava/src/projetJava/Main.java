package projetJava;

import java.awt.*;

import javax.swing.JFrame;

public class Main extends JFrame{

    public static void main(String[] a) {		
                Grid g = new Grid(500,500,20,20); //taille de la grille, colonnes, lignes
                JFrame f = new JFrame();
                f.setPreferredSize(new Dimension(500, 500));
    			f.setLocationRelativeTo(null);
                f.add(g);
                f.pack();
                f.setVisible(true);    
            }
        
    }

