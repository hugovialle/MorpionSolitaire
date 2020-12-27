package gameManager;


import java.awt.BasicStroke;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedWriter;
import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import components.*;


public class Grid {

	private int square, width, height, score;
	private int maxX,minX;
	private int maxY,minY;
	private Point[][] points; //tous les emplacements de points sur la grille
	private int[][] pointsState; // point existant = 1 ; sinon 0
	private ArrayList<Line> allDrawnLines;
	private ArrayList<Line> possibleLines;
	private boolean checkGameOver;
	private boolean redrawDone;
	private String mode;
	private String username;
	private boolean isRecord;

	private final static int gap = 5; //gap found to place points centered in intersection lines
	private final static int POINT_SIZE = 9;

	public Grid(int square, int wid, int hei, String gameType, String uname) {
		this.width = wid;
		this.height = hei;
		this.square = square;
		this.score=0;
		this.points = new Point[this.width/square][this.height/square];
		this.pointsState = new int[this.width/square][this.height/square];
		this.allDrawnLines = new ArrayList<Line>();
		this.possibleLines = new ArrayList <Line>();
		// We define the clickable zone to check the possibleMoves every turn
		this.minX=9;
		this.maxX=20;	 
		this.minY=7;
		this.maxY=18;
		this.checkGameOver = false;
		this.redrawDone = false; // wait the repaint of the grid when the player clicks (cf draw() function)
		this.mode = gameType;
		this.username = uname;
		this.isRecord = false;


		//define the state of starting cross points
		for(int i = 0; i<this.width/this.square; i++) {
			for(int j = 0; j<this.height/this.square; j++) {
				int x = (wid/2-(square/2))%square+i*square-gap;
				int y = (wid/2-(square/2))%square+j*square-gap;
				Point p = new Point(x,y);
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

		// draw background lines of the grid
		for(int i = 0; i < width/square; i++) {
			g2d.setColor(Color.gray);
			//draw rows
			g2d.drawLine(0, ycal+i*square, width, ycal+i*square);
			//draw cols
			g2d.drawLine(xcal+i*square, 0, xcal+i*square, height);
		}

		// draw player lines
		for(int i = 0; i<allDrawnLines.size(); i++) {
			g2d.setStroke(new BasicStroke(2));
			g2d.setColor(Color.red);
			allDrawnLines.get(i).draw(g2d);
		}

		//draw player points
		g2d.setColor(Color.black);
		for(int i = 0; i<this.width/this.square; i++) {
			for(int j = 0; j<this.height/this.square; j++) {
				if(pointsState[i][j]==1) {
					if(points[i][j] instanceof MovePoint) { 
						((MovePoint) points[i][j]).draw(g2d);
					}
					//draw starting cross lines
					else { 
						g2d.setColor(Color.black);
						g2d.fillOval((int)points[i][j].getX(), (int)points[i][j].getY(), POINT_SIZE, POINT_SIZE); 
					}
				}	   
			}
		}

		//draw lines propositions if more than one exists
		if (possibleLines.size() > 1 && !checkGameOver) {
			Point p = null;
			for(Line l : possibleLines) {
				g2d.setColor(Color.yellow);
				if (p !=null &&  p.getX() == l.getPoint(4).getX() && p.getY() == l.getPoint(4).getY()){
					g2d.fillOval(l.getPoint(0).getX(), l.getPoint(0).getY(), POINT_SIZE, POINT_SIZE);
				}
				else {
					g2d.fillOval(l.getPoint(4).getX(), l.getPoint(4).getY(), POINT_SIZE, POINT_SIZE);
					p = new Point(l.getPoint(4).getX(), l.getPoint(4).getY());
				}

			}
			redrawDone = true; 
		}
	}

	public void move(int x, int y) {
		// row & columns coordinate calculations  (0,30)
		int xRound = Math.round(x / this.square); 
		int yRound = Math.round(y / this.square);
		if(possibleMoves().size()==0) {
			System.out.println("GAME OVER : Score : "+score);
			writeInScoreboard("HUMAN");      

		}
		if((maxX>=xRound) && (minX<=xRound) && (maxY>=yRound) && (minY<=yRound)) {
			if (pointsState[xRound][yRound] == 0 || possibleLines.size() > 1) {
				// coordinates in the window (from 0,0 to 900,900)
				int xPoint = xRound*this.square+this.square/2-gap;
				int yPoint = yRound*this.square+this.square/2-gap;

				if (isPossibleLine(xRound,yRound) && !checkGameOver ) {	
					if (possibleLines.size() == 1) { // if one line only is possible
						MovePoint mp = new MovePoint(xPoint,yPoint,score+1);
						addLine(possibleLines.get(0));
						if (mode == "FIVED") mp.copyDirections(points[xRound][yRound]);
						points[xRound][yRound]=mp;
						pointsState[xRound][yRound]=1;
						this.score++;
						possibleLines.clear(); 
					}

					else if (possibleLines.size() > 1 ) { // if more than one line is possible 
						for (Line l : possibleLines) {
							int xClicked = l.getIndexclickedPoint().getX();
							int yClicked = l.getIndexclickedPoint().getY();
							if (xPoint == l.getPoint(4).getX() && yPoint == l.getPoint(4).getY() && redrawDone) {
								MovePoint mp = new MovePoint(xClicked,yClicked, score+1);
								addLine(l);
								int xRoundClicked = Math.round(xClicked / this.square); 
								int yRoundClicked = Math.round(yClicked / this.square);
								if (mode == "FIVED") mp.copyDirections(points[xRoundClicked][yRoundClicked]);
								points[xRoundClicked][yRoundClicked]=mp;
								pointsState[xRoundClicked][yRoundClicked]=1;
								this.score++;
								possibleLines.clear(); 
								redrawDone = false;
								break;
							}
						}
					}

				}
			}
		}
	}

	public boolean launchAlgorithm() {
		if (possibleMoves().size() > 0 ) {

			int i = (int) Math.floor(Math.random() * possibleMoves().size());
			int x = possibleMoves().get(i).getX();
			int y = possibleMoves().get(i).getY();

			int xRound = Math.round(x / this.square); 
			int yRound = Math.round(y / this.square);

			int xPoint = xRound*this.square+this.square/2-gap;
			int yPoint = yRound*this.square+this.square/2-gap;
			if (xRound > 0 && isPossibleLine(xRound,yRound)) {

				MovePoint mp = new MovePoint(xPoint,yPoint,score+1);
				int j = (int) Math.floor(Math.random() * possibleLines.size());
				addLine(possibleLines.get(j));
				if (mode == "FIVED") mp.copyDirections(points[xRound][yRound]);
				points[xRound][yRound]=mp;
				pointsState[xRound][yRound]=1;
				this.score++;
				possibleLines.clear();
			}
			return true;
		}
		else if(possibleMoves().size()==0) {
			writeInScoreboard("COMPUTER");
		}
		return false;
	}
	
	private void writeInScoreboard(String playerType) {
		File file =new File("/src/scoreboard.txt");
		FileWriter fileWritter;
		try {
			fileWritter = new FileWriter(file,true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.newLine();
			bufferWritter.write(username + " : " + score + " (" + mode + ")  ("+playerType+")");
			bufferWritter.close();
			fileWritter.close();
			Scanner sc = new Scanner(file);
			if (score > sc.nextInt() ) {
				RandomAccessFile writer = new RandomAccessFile(file, "rw");
				writer.seek(0);
				writer.writeBytes(""+score);
				writer.close();
				this.isRecord = true;
			}
			sc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}


	private boolean isPossibleLine(int x, int y) {
		if (redrawDone) return true;
		Line line = new Line();
		boolean horizontal = checkLine(x,y,line,1,0);
		boolean vertical = checkLine(x,y,line,0,1);
		boolean diag1 = checkLine(x,y,line,1,1);
		boolean diag2 = checkLine(x,y,line,1,-1);
		if (horizontal || vertical || diag1 || diag2) {
			return true; 
		}
		else return false;
	}

	private boolean checkLine(int x, int y, Line line,int dirX, int dirY) {
		line.direction(dirX, dirY);
		line.clear();
		boolean findOneLine = false; //bug fix where points directions were locked even if they're not possible

		// check both sides of the directions
		for (int i = -4; i < 1; i++) { 
			line.clear();
			for (int j = 0; j < 5; j++) { 
				int x2 = x + dirX * (i + j);
				int y2 = y + dirY * (i + j);
				if ((pointsState[x2][y2] == 1 && !(points[x2][y2].isLocked(dirX, dirY))) || 
						(pointsState[x2][y2] == 0 && x2==x && y2==y && !(points[x2][y2].isLocked(dirX, dirY)))) {
					Point p = new Point(x2*this.square+this.square/2-gap,y2*this.square+this.square/2-gap);
					line.addPoint(p);
					if (x2 == x && y2 ==y && !checkGameOver) {
						line.setclickedPoint(points[x2][y2]);
					}
				}
				else {            	
					break;
				}
			}  
			if (line.lineSize() == 5 && !checkGameOver) { // line found, enter in the lines found tab
				Line l = new Line();
				l = line.copy();
				possibleLines.add(l);
				findOneLine = true;
			}
			else if(line.lineSize() == 5 && checkGameOver) { // to check if lines are still possible
				return true;
			}
		}

		if ((possibleLines.size() >= 1 && findOneLine) )  {
			return true;	
		}  

		else return false;
	}

	private void addLine(Line line) {
		allDrawnLines.add(line);	
		for(int i = 0; i<line.lineSize(); i++) {
			int x = (line.getPoint(i).getX() -this.square/2 +5 )/ this.square;
			int y = (line.getPoint(i).getY() -this.square/2 +5 )/ this.square;
			if (mode == "FIVET" ){
				switch(line.getDirection()) {
				case "RISE":
					if(i!=4) points[x][y].lockDirection("UPRIGHT");
					if(i!=0) points[x][y].lockDirection("DOWNLEFT");
					break;
				case "FALL":
					if(i!=4) points[x][y].lockDirection("DOWNRIGHT");
					if(i!=0) points[x][y].lockDirection("UPLEFT");
					break;
				case "VERTI":
					if(i!=4) points[x][y].lockDirection("DOWN");
					if(i!=0) points[x][y].lockDirection("UP");
					break;
				case "HORI":
					if(i!=4) points[x][y].lockDirection("RIGHT");
					if(i!=0) points[x][y].lockDirection("LEFT");
					break;
				}
			}
			else if (mode == "FIVED" ){
				switch(line.getDirection()) {
				case "RISE":
					points[x][y].lockDirection("UPRIGHT");
					points[x][y].lockDirection("DOWNLEFT");
					break;
				case "FALL":
					points[x][y].lockDirection("UPLEFT");
					points[x][y].lockDirection("DOWNRIGHT");
					break;
				case "VERTI":
					points[x][y].lockDirection("DOWN");
					points[x][y].lockDirection("UP");
					break;
				case "HORI":
					points[x][y].lockDirection("RIGHT");
					points[x][y].lockDirection("LEFT");
					break;
				}
			}
			minX=Math.min(minX, x-1);
			minY=Math.min(minY, y-1);	
			maxX=Math.max(maxX, x+1);
			maxY=Math.max(maxY, y+1);
		}
	}

	private List<Point> possibleMoves() {
		checkGameOver = true;
		List<Point> allMovesPossible = new ArrayList<Point>();
		for(int i = minX; i<=maxX; i++) {
			for(int j = minY; j<= maxY; j++) {
				if(isPossibleLine(i,j)) {
					allMovesPossible.add(points[i][j]);
				}
			}
		}
		checkGameOver = false;
		return allMovesPossible;
	}

	public int getScore() {
		return score;
	}

	public boolean isGameOver() {
		if (possibleMoves().size()==0) return true;
		else return false;
	}

	public boolean isRecord() {
		if (isRecord) return true;
		else return false;
	}

}