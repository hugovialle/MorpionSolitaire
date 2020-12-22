package projetJava;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Grid {
	
	 int square, width, height;
	 int score;
	 Point[][] points; //tous les emplacements de points sur la grille
	 int[][] pointsState; // point existant = 1 ; sinon 0

	   
	   public Grid(int square, int wid, int hei) {
		   this.width = wid;
		   this.height = hei;
		   this.square = square;
		   this.score=0;
		   this.points = new Point[this.width/square][this.height/square];
		   this.pointsState = new int[this.width/square][this.height/square];
		   
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
		       //System.out.println("Point arrondi : " + xRound+","+yRound);
		       //System.out.println("Point (coordon�es Jframe) : " + xPoint+","+yPoint);
		       ArrayList<Point> lineToDraw = new ArrayList<Point>();
		       if (isPossibleLine(xRound,yRound, lineToDraw)) {
		    	   Point p = new Point(xPoint,yPoint);
		    	   if(p.lineValidation(lineToDraw)) {
		    		   MovePoint pp = new MovePoint(xPoint,yPoint,lineToDraw,score+1);
		    		   points[xRound][yRound]=pp;
		    		   pointsState[xRound][yRound]=1;
		    		   this.score++;
		    	}
		     }
		   }
  
	   }
	   
	// Un point peut faire parti d'une ligne si et seulement si ce point n'a pas cette direction de ligne occup�e
	   // Exemple : on veut faire une ligne horizontale : il faut check si le point n'est pas "occup�" horizontalement 
	   // un point a 8 occupations possibles : haut, bas, gauche, droite, diag HG, diag HD, diag BG, diag BD
	   private boolean isPossibleLine(int x, int y, ArrayList<Point> line) {
		   if (checkHVLines(x,y,line,1,0) || 
			   checkHVLines(x,y,line,0,1) ||
			   checkDiagLines(x,y,line)
			   ) 
			   return true; 
		return false;
	   }
	   
	  
	   
	   private boolean checkHVLines(int x, int y, ArrayList<Point> line,int dirX, int dirY) {
		// ajout du point cliqu� dans la ligne
		   line.add(points[x][y]);
		   int cmpX=dirX;
		   int cmpY=dirY;
		   
		   while (pointsState[x-cmpX][y-cmpY]==1 && points[x][y].getX() != x+cmpX && points[x][y].getY() != y+cmpY) {
			   if (line.size()==5) break;
			   line.add(points[x-cmpX][y-cmpY]);
			   //System.out.println("Point trouve : ("+(x-cmpX)+","+(y-cmpY)+")");
			   if(dirX != 0) cmpX++;
			   if(dirY != 0) cmpY++;
		   }
		   if (line.size()==5) return true; // au lieu de ces returns trues, il faut stocker ces lignes dans un tableau
		   									// pour pouvoir proposer au joueur si y'a plusieurs choix possibles
		   
		   cmpX = dirX;
		   cmpY = dirY;
		   while (pointsState[x+cmpX][y+cmpY]==1 && points[x][y].getX() != x+cmpX && points[x][y].getY() != y+cmpY ) {
			   if (line.size()==5) break;
			   line.add(points[x+cmpX][y+cmpY]);
			   System.out.println("Point trouve : ("+(x+cmpX)+","+(y+cmpY)+")");
			   if(dirX != 0) cmpX++;
			   if(dirY != 0) cmpY++;
		   }
		   if (line.size()==5)  return true; 
		   line.clear();
		// if no line is drawable : 
		   return false;
	   }
	   
	   private boolean checkDiagLines(int x, int y, ArrayList<Point> line) {
		   int increment = 1;
		   line.add(points[x][y]);
		
		// VERIF DIAGONAL UPLEFT<-->DOWNRIGHT
		   while (pointsState[x+increment][y+increment]==1) {
			   if (line.size()==5) break;
			   line.add(points[x+increment][y+increment]);
			   increment++;
		   }
		   if (line.size()==5)  return true; 
		   increment = 1;
		   while (pointsState[x-increment][y-increment]==1) {
			   if (line.size()==5) break;
			   line.add(points[x-increment][y-increment]);
			   increment++;
		   }
		   if (line.size()==5) return true;  
		   else increment = 1;
		   line.clear();
		   
		   line.add(points[x][y]);
		  // VERIF DIAGONAL DOWNLEFT<-->UPRIGHT
		   while (pointsState[x-increment][y+increment]==1) {
			   if (line.size()==5) break;
			   line.add(points[x-increment][y+increment]);
			   increment++;
		   }
		   if (line.size()==5)  return true; 
		    increment = 1;
		   while (pointsState[x+increment][y-increment]==1) {
			   if (line.size()==5) break;
			   line.add(points[x+increment][y-increment]);
			   increment++;
		   }
		   if (line.size()==5)  return true; 
		   line.clear();	   	   
	// if no line is drawable:
		   return false;
	   }

}