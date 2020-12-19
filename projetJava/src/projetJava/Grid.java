package projetJava;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class Grid {
	
	 int square, width, height;
	 int score;
	 Point[][] points;
	 int[][] pointState;

	   
	   public Grid(int square, int wid, int hei) {
		   this.width = wid;
		   this.height = hei;
		   this.square = square;
		   this.score=0;
		   this.points = new Point[this.width/square][this.height/square];
		   this.pointState = new int[this.width/square][this.height/square];
		   for(int i = 0; i<this.width/this.square; i++) {
			   for(int j = 0; j<this.height/this.square; j++) {
				   Point p = new Point((wid/2-(square/2))%square+i*square-5, (wid/2-(square/2))%square+j*square-5);
				   points[i][j]=p;
				   if(j==9 || j==10 || j==15 || j==16) {
					   if(i==13 || i==16) {
						   pointState[i][j]=1;
					   }
				   }
				   else if(j==8 || j==17) {
					   if(i>=13 && i<=16) {
						   pointState[i][j]=1;
					   }
				   }
				   else if(j==11 || j==14) {
					   if(i>=10 && i<=13 || i>=16 && i<=19){
						   pointState[i][j]=1;
					   }
				   }
				   else if(j==12 || j==13) {
					   if(i==10 || i==19) {
						   pointState[i][j]=1;
					   }
				   }
			   }
		   }
	   }
		
	   public void draw(Graphics2D g2d) {
		   int Xmiddle = width/2;
		   int Ymiddle = height/2;
		   int xcal = (Xmiddle-(square/2))%square;
		   int ycal = (Ymiddle-(square/2))%square;
		   g2d.setColor(Color.gray);
		   // draw lines
		   for(int i = 0; i < width/square; i++) {
			   g2d.setColor(Color.gray);
			   //draw rows
			   g2d.drawLine(0, ycal+i*square, width, ycal+i*square);
			   //draw cols
			   g2d.drawLine(xcal+i*square, 0, xcal+i*square, height);
			   
		   }
		   
		   //draw all points
		   g2d.setColor(Color.black);
		   for(int i = 0; i<this.width/this.square; i++) {
			   for(int j = 0; j<this.height/this.square; j++) {

				   if(pointState[i][j]==1) {
					   if(points[i][j] instanceof MovePoint) {
						   ((MovePoint) points[i][j]).draw(g2d);
					   }
					   else {
						   g2d.setColor(Color.black);
						   g2d.fillOval(points[i][j].x, points[i][j].y, 9, 9);
					   }
					   
				   }
				   
			   }
		   }
	   }
	   
	   public void move(int x, int y) {
		   int xRound = Math.round(x / this.square);
	       int yRound = Math.round(y / this.square);
	       int xPoint = xRound*this.square+this.square/2;
	       int yPoint = yRound*this.square+this.square/2;
		   MovePoint pp = new MovePoint(xPoint,yPoint,score);
		   points[xRound][yRound]=pp;
		   pointState[xRound][yRound]=1;
		   this.score++;
	   }
}