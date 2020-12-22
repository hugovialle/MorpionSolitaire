package projetJavaTESTLINES;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;


public class Grid {
	
	 int square, width, height;
	 int score;
	 Point[][] points; //tous les emplacements de points sur la grille
	 int[][] pointsState; // point existant = 1 ; sinon 0
	 ArrayList<Line> lines;

	   
	   public Grid(int square, int wid, int hei) {
		   this.width = wid;
		   this.height = hei;
		   this.square = square;
		   this.score=0;
		   this.points = new Point[this.width/square][this.height/square];
		   this.pointsState = new int[this.width/square][this.height/square];
		   this.lines = new ArrayList<Line>();
		   
		   //define the state of starting cross points
		   for(int i = 0; i<this.width/this.square; i++) {
			   for(int j = 0; j<this.height/this.square; j++) {
				   Point p = new Point((wid/2-(square/2))%square+i*square-5, (wid/2-(square/2))%square+j*square-5);
				   points[i][j]=p;
				   if(j==9 || j==10 || j==15 || j==16) {
					   if(i==13 || i==16) {
						   pointsState[i][j]=1;
					   }
				   }
				   else if(j==8 || j==17) {
					   if(i>=13 && i<=16) {
						   pointsState[i][j]=1;
					   }
				   }
				   else if(j==11 || j==14) {
					   if(i>=10 && i<=13 || i>=16 && i<=19){
						   pointsState[i][j]=1;
					   }
				   }
				   else if(j==12 || j==13) {
					   if(i==10 || i==19) {
						   pointsState[i][j]=1;
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
		   
		   for(int i = 0; i<lines.size(); i++) {
			   g2d.setStroke(new BasicStroke(2));
		       g2d.setColor(Color.red);
			   lines.get(i).draw(g2d);
		   }
		   
		   g2d.setColor(Color.black);
		   for(int i = 0; i<this.width/this.square; i++) {
			   for(int j = 0; j<this.height/this.square; j++) {

				   if(pointsState[i][j]==1) {
					   if(points[i][j] instanceof MovePoint) { //draw player points
						   ((MovePoint) points[i][j]).draw(g2d);
					   }
					   else { //draw points of the starting cross 
						   g2d.setColor(Color.black);
						   g2d.fillOval((int)points[i][j].getX(), (int)points[i][j].getY(), 9, 9); // mettre des noms aux 2 dernieres variables
					   }
					   
				   }	   
			   }
		   }
	   }
	   
	   public void move(int x, int y) {
		   // coordonees par rapport aux lignes/colonnes (de 0,0 a 30,30)
		   int xRound = Math.round(x / this.square); 
	       int yRound = Math.round(y / this.square);
		   if (pointsState[xRound][yRound] == 0) {
			   // coordonees exactes de la fenetre (de 0,0 a 900,900)
			   int xPoint = xRound*this.square+this.square/2-5;
		       int yPoint = yRound*this.square+this.square/2-5;

		       Line lineToDraw = new Line();
		       if (isPossibleLine(xRound,yRound, lineToDraw)) {
		    	   addLine(lineToDraw);
		    	   MovePoint mp = new MovePoint(xPoint,yPoint,lineToDraw,score+1);
		    	   points[xRound][yRound]=mp;
		    	   pointsState[xRound][yRound]=1;
		    	   this.score++;

		     }
		   }
  
	   }
	   
	   private boolean isPossibleLine(int x, int y, Line line) {
		   if (checkLine(x,y,line,1,0) || 
			   checkLine(x,y,line,0,1) ||
			   checkLine(x,y,line,1,1) ||
			   checkLine(x,y,line,1,-1)
			   ) 
			   return true; 
		return false;
	   }
	   
	   private boolean checkLine(int x, int y, Line line,int dirX, int dirY) {
	        line.direction(dirX, dirY);
	        for (int i = -4; i < 1; i++) {
	            line.clear();
	            for (int j = 0; j < 5; j++) {
	                int y2 = y + dirY * (i + j);
	                int x2 = x + dirX * (i + j);
	                if ((pointsState[x2][y2] == 1 && !(points[x2][y2].isLocked(dirX, dirY))) || (pointsState[x2][y2] == 0 && x2==x && y2==y)) {

	                	line.addPoint(new Point(x2*this.square+this.square/2-5,y2*this.square+this.square/2-5));
	                }
	                else {
	                    break;
	                }
	            }
	            
	            if (line.lineSize() == 5) {
	                return true;
	            }
	        }
	        return false;
	   }
	   
	   public void addLine(Line line) {
		   lines.add(line);
		   
		   // Updates directions for every points of the line
		   if(line.getDirection().equals("RISE")) {
				for(int i = 0; i<line.lineSize(); i++) {
					int x = (line.getPoint(i).getX() -this.square/2 +5 )/ this.square ;
					int y = (line.getPoint(i).getY() -this.square/2 +5 )/ this.square;
					if(i!=4) points[x][y].lockDirection("UPRIGHT");
					if(i!=0) points[x][y].lockDirection("DOWNLEFT");
				}
		   }
		   if(line.getDirection().equals("FALL")) {
				for(int i = 0; i<line.lineSize(); i++) {
					int x = (line.getPoint(i).getX() -this.square/2 +5 )/ this.square;
					int y = (line.getPoint(i).getY() -this.square/2 +5 )/ this.square;
					if(i!=4) points[x][y].lockDirection("UPLEFT");
					if(i!=0) points[x][y].lockDirection("DOWRIGHT");
				}
		   }
			if(line.getDirection().equals("VERTI")) {
				for(int i = 0; i<line.lineSize(); i++) {
					int x = (line.getPoint(i).getX() -this.square/2 +5 )/ this.square;
					int y = (line.getPoint(i).getY() -this.square/2 +5 )/ this.square;
					if(i!=4) points[x][y].lockDirection("UP");
					if(i!=0) points[x][y].lockDirection("DOWN");
				}
			}
			if(line.getDirection().equals("HORI")) {
				for(int i = 0; i<line.lineSize(); i++) {
					int x = (line.getPoint(i).getX() -this.square/2 +5 )/ this.square;
					int y = (line.getPoint(i).getY() -this.square/2 +5 )/ this.square;
					if(i!=4) points[x][y].lockDirection("RIGHT");
					if(i!=0) points[x][y].lockDirection("LEFT");
				}
			}
				
	   }
	   
}