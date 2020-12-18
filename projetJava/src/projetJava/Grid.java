package projetJava;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.JPanel;


public class Grid extends JPanel {

   int width, height, columns, rows;
   
   ArrayList<Point> points = new ArrayList<>();
   
   
   public Grid(int wid,int hei, int cols,int rws) {
	   this.width = wid;
	   this.height = hei;
	   this.setSize(this.width, this.height);
	   this.columns = cols;
	   this.rows = rws;
   }
   
   public void paint(Graphics g) {
	   g.setColor(Color.gray);
	   //draw rows
	   for(int i = 0; i < rows; i++) {
		   g.drawLine(0, i*(height/rows), width, i*(height/rows));
	   }
	   
	   //draw columns
	   for (int i =0; i<columns; i++) {
		   g.drawLine(i*(height/rows), 0, i*(height/rows), height);
	   }
	   
	   this.pointDraw(g);
   }

   public void pointDraw(Graphics g) {
	   Point p = new Point(rows-5,columns-3);
	   g.setColor(Color.black);
	   g.fillOval(p.getX(), p.getY(), 10, 10);//coordonées, taille du point
   }
   
   

}

