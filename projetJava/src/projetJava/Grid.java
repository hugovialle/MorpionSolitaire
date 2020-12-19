package projetJava;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;


public class Grid extends JPanel {

   int width, height;
   final int POINT_SIZE = 10;
   ArrayList<Point> points = new ArrayList<>();
   int square;
   
   public Grid(int square, int wid, int hei) {
	   this.width = wid;
	   this.height = hei;
	   this.setSize(this.width, this.height);
	   this.square = square;
   }
   
   
   @Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		  Graphics2D g2d = (Graphics2D) g; // en graphics2d pour permettre de zoomer (scale)
		  this.draw(g2d);
		 
		   
	}
 
  


   public void draw(Graphics2D g2d) {
	   int Xmiddle = width/2;
	   int Ymiddle = height/2;
	   int xcal = (Xmiddle-(square/2))%square;
	   int ycal = (Ymiddle-(square/2))%square;
	  
	   g2d.setColor(Color.gray);
	   //draw rows
	   for(int i = 0; i <= width/square; i++) {
		   g2d.setColor(Color.gray);
		   g2d.drawLine(0, ycal+i*square, width, ycal+i*square);
		   
		   //AJOUT DE TOUS LES POINTS POSSIBLE DANS LARRAYLIST points
		   for (int j = 0; j <= width/square; j++) {
			   Point p = new Point(ycal+i*square-5, xcal+j*square-5);
			   points.add(p);
			   
		   }
		   
	   }
	   
	   //draw columns
	   for (int i =0; i<=height/square; i++) {
		   g2d.drawLine(xcal+i*square, 0, xcal+i*square, height);
	   }
	   g2d.setColor(Color.black);
	   
	   
	   this.addMouseListener(new MouseAdapter() { // bug : quand resize de fenetre ==> points disparaissent
 		     @Override
 		     public void mousePressed(MouseEvent e) {
 		    	
 		       //créer un point au clic */
 		    	
 		    	 //Test GRAPHIQUE
 		    	for (Point p : points) { 
 		    		getGraphics().fillOval((int)p.getX(),(int)p.getY(),POINT_SIZE,POINT_SIZE);
 		    	}
 		     }	    
 		  }); 
   }
}

