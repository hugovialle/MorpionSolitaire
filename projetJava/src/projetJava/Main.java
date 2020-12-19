package projetJava;

import java.awt.*;

import javax.swing.JFrame;

public class Main extends JFrame{

    public static void main(String[] a) {		
    	
        		
        		
        		
                Grid grid = new Grid(40,750,750); //taille de la grille, colonnes, lignes
                JFrame f = new JFrame();
                f.setPreferredSize(new Dimension(750, 750));
    			f.setLocationRelativeTo(null);
                f.add(grid);
                f.pack();
                f.setVisible(true);    
                
                
                
                
            }
        
    }

