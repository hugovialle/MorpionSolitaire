package projetJava;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;


public class Grid extends JPanel {

   int width, height, columns, rows;
   final int POINT_SIZE = 10;
   ArrayList<Point> points = new ArrayList<>();
   
   
   public Grid(int wid,int hei, int cols,int rws) {
	   this.width = wid;
	   this.height = hei;
	   this.setSize(this.width, this.height);
	   this.columns = cols;
	   this.rows = rws;
	   
   }
   
   
   @Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		  Graphics2D g2d = (Graphics2D) g; // en graphics2d pour permettre de zoomer (scale)
		  this.draw(g2d);
		 
		   
	}
 
  


   public void draw(Graphics2D g2d) {
	   g2d.scale(3,3);
	   g2d.setColor(Color.gray);
	   //draw rows
	   for(int i = 0; i < rows; i++) {
		   g2d.drawLine(0, i*(height/rows), width, i*(height/rows));
	   }
	   
	   //draw columns
	   for (int i =0; i<columns; i++) {
		   g2d.drawLine(i*(height/rows), 0, i*(height/rows), height);
	   }
	   g2d.setColor(Color.black);
	   
	   
	   this.addMouseListener(new MouseAdapter() { // bug : quand resize de fenetre ==> points disparaissent
 		     @Override
 		     public void mousePressed(MouseEvent e) {
 		    	getGraphics().fillOval(e.getX(), e.getY(), POINT_SIZE, POINT_SIZE); //créer un point au clic
 		    	
 		        
 		     }
 		    
 		  });
	   
   }
  


}

