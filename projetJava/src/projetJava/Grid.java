package projetJava;


import java.awt.BasicStroke;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;


public class Grid {

	int square, width, height, score;
	int maxX,minX;
	int maxY,minY;
	Point[][] points; //tous les emplacements de points sur la grille
	int[][] pointsState; // point existant = 1 ; sinon 0
	ArrayList<Line> lines;
	ArrayList<Line> possibleLines;
	boolean checkGameOver;
	boolean isRedraw;
	String mode;
	

	public Grid(int square, int wid, int hei) {
		this.width = wid;
		this.height = hei;
		this.square = square;
		this.score=0;
		this.points = new Point[this.width/square][this.height/square];
		this.pointsState = new int[this.width/square][this.height/square];
		this.lines = new ArrayList<Line>();
		this.possibleLines = new ArrayList <Line>();
		// We define the clickable zone to check the possibleMoves every turn
		this.minX=9;
		this.maxX=20;	 
		this.minY=7;
		this.maxY=18;
		this.checkGameOver = false;
		this.isRedraw = false; // correct bugs on propositions lines where clicked point = line.getPoint(4)
		this.mode = "FIVET";
		
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

		if (possibleLines.size() > 1 && !checkGameOver) {
			for(Line l : possibleLines) {
				g2d.setColor(Color.yellow);
				g2d.fillOval(l.getPoint(4).getX(), l.getPoint(4).getY(), 9, 9);
			}
			isRedraw = true;
		}
	}

	public void move(int x, int y) {
		// coordonees par rapport aux lignes/colonnes (de 0,0 a 30,30)
		int xRound = Math.round(x / this.square); 
		int yRound = Math.round(y / this.square);
		if(possibleMoves().size()==0) System.out.println("GAME OVER : Score : "+score);
		if((maxX>=xRound) && (minX<=xRound) && (maxY>=yRound) && (minY<=yRound)) {
			if (pointsState[xRound][yRound] == 0 || possibleLines.size() > 1) {
				// coordonees exactes de la fenetre (de 0,0 a 900,900)
				int xPoint = xRound*this.square+this.square/2-5;
				int yPoint = yRound*this.square+this.square/2-5;
				if ( isPossibleLine(xRound,yRound) && !checkGameOver ) {
					System.out.println("-----------");
					System.out.println("taille possibleLines : " + possibleLines.size());
					if (possibleLines.size() == 1) { // if one line only is possible
						MovePoint mp = new MovePoint(xPoint,yPoint,score+1);
						addLine(possibleLines.get(0));
						if (mode == "FIVED") mp.copyDirections(points[xRound][yRound]);
						points[xRound][yRound]=mp;
						pointsState[xRound][yRound]=1;
						this.score++;
						possibleLines.clear(); 
						System.out.println(xPoint +","+ yPoint);
					}
					else if (possibleLines.size() > 1 ) { // if more than one line is possible 
						for (Line l : possibleLines) {
							int xClicked = l.getIndexclickedPoint().getX();
							int yClicked = l.getIndexclickedPoint().getY();
							if (xPoint == l.getPoint(4).getX() && yPoint == l.getPoint(4).getY() && isRedraw) {
								MovePoint mp = new MovePoint(xClicked,yClicked, score+1);
								addLine(l);
								
								int xRoundClicked = Math.round(xClicked / this.square); 
								int yRoundClicked = Math.round(yClicked / this.square);
								if (mode == "FIVED") mp.copyDirections(points[xRoundClicked][yRoundClicked]);
								points[xRoundClicked][yRoundClicked]=mp;
								pointsState[xRoundClicked][yRoundClicked]=1;
								this.score++;
								possibleLines.clear(); 
								isRedraw = false;
								break;
							}
						}
				}
				
				}
					System.out.println(13 + ","+10+" : " + points[16][10].isLocked(1, 0));
					System.out.println(14 + ","+10+" : " + points[15][10].isLocked(1, 0));
					System.out.println(15+ ","+10+" : " + points[14][10].isLocked(1, 0)); 
					System.out.println(16 + ","+10+" : " + points[13][10].isLocked(1, 0)); 
					System.out.println(17 + ","+10+" : " + points[12][10].isLocked(1, 0)); 
				}
			}
		
	}

	private boolean isPossibleLine(int x, int y) {
		if (isRedraw) return true;
		Line line = new Line();
		boolean horizontal = checkLine(x,y,line,1,0);
		boolean vertical = checkLine(x,y,line,0,1);
		boolean diag1 = checkLine(x,y,line,1,1);
		boolean diag2 = checkLine(x,y,line,1,-1);
		if (!checkGameOver) {
		System.out.println ("hori " + horizontal);
		System.out.println ("verti " + vertical);
		System.out.println ("diag1 " + diag1);
		System.out.println ("diag2 " + diag2);
		}
		if (horizontal ||vertical ||diag1 || diag2) {
			return true; 
		}
		else return false;
	}

	private boolean checkLine(int x, int y, Line line,int dirX, int dirY) {
		line.direction(dirX, dirY);
		line.clear();
		boolean findOneLine = false;
		for (int i = -4; i < 1; i++) {
			line.clear();
			for (int j = 0; j < 5; j++) {
				int y2 = y + dirY * (i + j);
				int x2 = x + dirX * (i + j);
				if ((pointsState[x2][y2] == 1 && !(points[x2][y2].isLocked(dirX, dirY))) || 
						(pointsState[x2][y2] == 0 && x2==x && y2==y && !(points[x2][y2].isLocked(dirX, dirY)))) {
					Point p = new Point(x2*this.square+this.square/2-5,y2*this.square+this.square/2-5);
					line.addPoint(p);
					
					
					if (x2 == x && y2 ==y && !checkGameOver) {
						line.setIndexclickedPoint(points[x2][y2]);
					}
				}
				else {            	
					break;
				}
			}  
			if (line.lineSize() == 5 && !checkGameOver) {
				Line l = new Line();
				l = line.copy();
				possibleLines.add(l);
				findOneLine = true;
			}
			else if(line.lineSize() == 5 && checkGameOver) {
				return true;
			}


		}

		if ((possibleLines.size() >= 1 && findOneLine) || isRedraw ) {
			if (isRedraw) return false;
			return true;
			
		}  
		return false;
	}

	public void addLine(Line line) {

		lines.add(line);	
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
					if(i!=4) points[x][y].lockDirection("UPLEFT");
					if(i!=0) points[x][y].lockDirection("DOWNRIGHT");
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

	public List<Point> possibleMoves() {
		checkGameOver = true;
		List<Point> possibleMoves = new ArrayList<Point>();
		for(int i = minX; i<=maxX; i++) {
			for(int j = minY; j<= maxY; j++) {
				if(isPossibleLine(i,j)) {
					possibleMoves.add(points[i][j]);
				}
			}
		}
		checkGameOver = false;
		return possibleMoves;
	}

}