/*
 * @author ALVESYohann_VIALLEHugo
 * @version 18
 */
package gameManager;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import gameComponents.*;

public class Grid {

  private int square,width,height;
  private int score;
  private int maxX,minX,maxY,minY; //specify the border of the grid
  private Point[][] points; // every point places on the grid
  private int[][] pointsState; // state of the point means if he's existant or not on the grid (1 : exist, 0 : not exist)
  private ArrayList <Line> allDrawnLines, possibleLines; // allDrawnLines : lines drawn by the player // possibleLines : lines possibles for the player movepoint
  private boolean checkGameOver;
  private boolean redrawDone;  // check the repaint of the grid when the player clicks (cf draw() function)
  private String mode; // 5D or 5T
  private String username; 
  private boolean isRecord;
  private final static int gap = 5; //gap found to place points centered in intersection lines
  private final static int POINT_SIZE = 9;
  private final static String filePath = "projetJava/src/scoreboard.txt";

  /**
   * Instantiates a new grid.
   *
   * @param square : side length of a square (or a cell)
   * @param wid : frame width
   * @param hei : frame height
   * @param gameType : can be FIVED or FIVET
   * @param uname : player username
   */
  public Grid(int square, int wid, int hei, String gameType, String uname) {
    this.width = wid;
    this.height = hei;
    this.square = square;
    this.score = 0;
    this.points = new Point[this.width / square][this.height / square];
    this.pointsState = new int[this.width / square][this.height / square];
    this.allDrawnLines = new ArrayList <Line> ();
    this.possibleLines = new ArrayList <Line> ();
    // We define the clickable zone to check the possibleMoves every turn
    this.minX = 9;
    this.maxX = 20;
    this.minY = 7;
    this.maxY = 18;
    this.checkGameOver = false;
    this.redrawDone = false;
    this.mode = gameType;
    this.username = uname;
    this.isRecord = false;

    //define the state of starting cross points
    for (int i = 0; i < this.width / this.square; i++) {
      for (int j = 0; j < this.height / this.square; j++) {
        int x = (wid / 2 - (square / 2)) % square + i * square - gap;
        int y = (wid / 2 - (square / 2)) % square + j * square - gap;
        Point p = new Point(x, y);
        points[i][j] = p;
        if (j == 9 || j == 10 || j == 15 || j == 16) {
          if (i == 13 || i == 16) {
            pointsState[i][j] = 1;
          }
        }
        else if (j == 8 || j == 17) {
          if (i >= 13 && i <= 16) {
            pointsState[i][j] = 1;
          }
        }
        else if (j == 11 || j == 14) {
          if (i >= 10 && i <= 13 || i >= 16 && i <= 19) {
            pointsState[i][j] = 1;
          }
        }
        else if (j == 12 || j == 13) {
          if (i == 10 || i == 19) {
            pointsState[i][j] = 1;
          }
        }
      }
    }
  }

  /**
   * Draw every components on the grid
   * at each player mouse pressed : draw is reinvoked
   * source : https://docs.oracle.com/javase/7/docs/api/java/awt/Graphics2D.html
   * 
   * @param g2d : Graphics2D class
   */
  public void draw(Graphics2D g2d) {
    int Xmiddle = width / 2;
    int Ymiddle = height / 2;
    int xcal = (Xmiddle - (square / 2)) % square;
    int ycal = (Ymiddle - (square / 2)) % square;

    // draw background lines of the grid
    for (int i = 0; i < width / square; i++) {
      g2d.setColor(Color.gray);
      //draw rows
      g2d.drawLine(0, ycal + i * square, width, ycal + i * square);
      //draw cols
      g2d.drawLine(xcal + i * square, 0, xcal + i * square, height);
    }

    // draw player lines
    for (int i = 0; i < allDrawnLines.size(); i++) {
      g2d.setStroke(new BasicStroke(2));
      g2d.setColor(Color.red);
      allDrawnLines.get(i).draw(g2d);
    }

    //draw player points
    g2d.setColor(Color.black);
    for (int i = 0; i < this.width / this.square; i++) {
      for (int j = 0; j < this.height / this.square; j++) {
        if (pointsState[i][j] == 1) {
          if (points[i][j] instanceof MovePoint) { ((MovePoint) points[i][j]).draw(g2d);
          }
          //draw starting cross lines
          else {
            g2d.setColor(Color.black);
            g2d.fillOval((int) points[i][j].getX(), (int) points[i][j].getY(), POINT_SIZE, POINT_SIZE);
          }
        }
      }
    }

    //draw lines propositions if more than one exists
    if (possibleLines.size() > 1 && !checkGameOver) {
      Point p = null;
      for (Line l: possibleLines) {
        g2d.setColor(Color.yellow);
        if (p != null && p.getX() == l.getPoint(4).getX() && p.getY() == l.getPoint(4).getY()) {
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

  /**
   * When player make a mouse click : move is invoked.
   * Check if the movePoint clicked by the player can be drawn or not
   * Ask for propositions if more than one line is possible
   * @param x : x coordinate where player clicked
   * @param y : y coordinate where player clicked
   */
  public void move(int x, int y) {
	  assert (x < width);
	  assert (y < height);
    // row & columns coordinate calculations  (0,30)
    int xRound = Math.round(x / this.square);
    int yRound = Math.round(y / this.square);
    if (possibleMoves().size() == 0) {
      System.out.println("GAME OVER : Score : " + score);
      writeInScoreboard("HUMAN"); //see below
    }
    if ((maxX >= xRound) && (minX <= xRound) && (maxY >= yRound) && (minY <= yRound)) {
      if (pointsState[xRound][yRound] == 0 || possibleLines.size() > 1) {
        // coordinates in the window (from 0,0 to 900,900)
        int xPoint = xRound * this.square + this.square / 2 - gap;
        int yPoint = yRound * this.square + this.square / 2 - gap;

        if (isPossibleLine(xRound, yRound) && !checkGameOver) {
          if (possibleLines.size() == 1) { // if one line only is possible
            MovePoint mp = new MovePoint(xPoint, yPoint, score + 1);
            lockPointsDirections(possibleLines.get(0));
            if (mode == "FIVED") mp.copyDirections(points[xRound][yRound]);
            points[xRound][yRound] = mp;
            pointsState[xRound][yRound] = 1;
            this.score++;
            possibleLines.clear();
          }

          else if (possibleLines.size() > 1) { // if more than one line is possible 
            for (Line l: possibleLines) {
              int xClicked = l.getclickedPoint().getX();
              int yClicked = l.getclickedPoint().getY();
              if (xPoint == l.getPoint(4).getX() && yPoint == l.getPoint(4).getY() && redrawDone) {
                MovePoint mp = new MovePoint(xClicked, yClicked, score + 1);
                lockPointsDirections(l);
                int xRoundClicked = Math.round(xClicked / this.square);
                int yRoundClicked = Math.round(yClicked / this.square);
                if (mode == "FIVED") mp.copyDirections(points[xRoundClicked][yRoundClicked]);
                points[xRoundClicked][yRoundClicked] = mp;
                pointsState[xRoundClicked][yRoundClicked] = 1;
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

  /**
   * Function to play the game as a computer
   * No mouse click by player, just check all possible moves and chose one randomly
   * if more than one line is possible on the point chosen, chose the line randomly also
   *
   * @return true if moves still possible, else false
   */
  public boolean launchAlgorithm() {
    if (possibleMoves().size() > 0) {

      int i = (int) Math.floor(Math.random() * possibleMoves().size()); //random point chose 
      int x = possibleMoves().get(i).getX();
      int y = possibleMoves().get(i).getY();

      int xRound = Math.round(x / this.square);
      int yRound = Math.round(y / this.square);

      int xPoint = xRound * this.square + this.square / 2 - gap;
      int yPoint = yRound * this.square + this.square / 2 - gap;
      if (xRound > 0 && isPossibleLine(xRound, yRound)) {
        MovePoint mp = new MovePoint(xPoint, yPoint, score + 1);
        int j = (int) Math.floor(Math.random() * possibleLines.size()); // random line chose
        lockPointsDirections(possibleLines.get(j));
        if (mode == "FIVED") mp.copyDirections(points[xRound][yRound]);
        points[xRound][yRound] = mp;
        pointsState[xRound][yRound] = 1;
        this.score++;
        possibleLines.clear();
      }
      return true;
    }
    else if (possibleMoves().size() == 0) {
      writeInScoreboard("COMPUTER");
    }
    return false;
  }

  /**
   * Write in scoreboard.txt the score of the game when it's game over.
   *
   * @param playerType : HUMAN or COMPUTER
   * @see move & launchAlgorithm functions
   */
  private void writeInScoreboard(String playerType) {
    File file = new File(filePath);
    FileWriter fileWritter;
    try {
      fileWritter = new FileWriter(file, true);
      BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
      bufferWritter.newLine();
      bufferWritter.write(username + " : " + score + " (" + mode + ")  (" + playerType + ")");
      bufferWritter.close();
      fileWritter.close();
      Scanner sc = new Scanner(file);
      if (score > sc.nextInt()) {
        RandomAccessFile writer = new RandomAccessFile(file, "rw"); // to overwrite the first line with the best score
        writer.seek(0);
        writer.writeBytes("" + score);
        writer.close();
        this.isRecord = true;
      }
      sc.close();
    } catch(IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * Checks if a line is drawable
   *
   * @param x : point coordinate X
   * @param y : point coordinate Y
   * @return true, if at least one line is possible
   */
  private boolean isPossibleLine(int x, int y) {
    if (redrawDone) return true;
    Line line = new Line();
    boolean horizontal = checkLine(x, y, line, 1, 0);
    boolean vertical = checkLine(x, y, line, 0, 1);
    boolean diag1 = checkLine(x, y, line, 1, 1);
    boolean diag2 = checkLine(x, y, line, 1, -1);
    if (horizontal || vertical || diag1 || diag2) {
      return true;
    }
    else return false;
  }

  /**
   * Check if a line is drawable in a specific direction (HORI,VERTI,RISE,FALL)
   * Fill the possibleLines ArrayList if at least one line is possible
   *
   * @param x : point coordinate X
   * @param y : point coordinate Y
   * @param line : the new Line which will be copied in possibleLines if it's a complete line
   * @param dirX : 1 for HORI, RISE, and FALL, 0 for VERTI
   * @param dirY : 0 for HORI, -1 for RISE, 1 for FALL and VERTI
   * @return true if at least one line is drawable in the direction, else false
   */
  private boolean checkLine(int x, int y, Line line, int dirX, int dirY) {
    line.direction(dirX, dirY);
    line.clear();
    boolean findOneLine = false; //bug fix where points directions were locked even if they're not possible
    // check both sides of the directions
    for (int i = -4; i < 1; i++) {
      line.clear();
      for (int j = 0; j < 5; j++) {
        int x2 = x + dirX * (i + j);
        int y2 = y + dirY * (i + j);
        if ((pointsState[x2][y2] == 1 && !(points[x2][y2].isLocked(dirX, dirY,i+j))) || (pointsState[x2][y2] == 0 && x2 == x && y2 == y && !(points[x2][y2].isLocked(dirX, dirY,i+j)))) {
          Point p = new Point(x2 * this.square + this.square / 2 - gap, y2 * this.square + this.square / 2 - gap);
          line.addPoint(p);
          if (x2 == x && y2 == y && !checkGameOver) {
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
      else if (line.lineSize() == 5 && checkGameOver) { // to check if lines are still possible
        return true;
      }
    }

    if ((possibleLines.size() >= 1 && findOneLine)) {
      return true;
    }

    else return false;
  }

  /**
   * Player chose the line to draw. 
   * After this, we want to lock every point of this line in the line direction
   *
   * @param line : line to draw
   */
  private void lockPointsDirections(Line line) {
    allDrawnLines.add(line);
    for (int i = 0; i < line.lineSize(); i++) {
      int x = (line.getPoint(i).getX() - this.square / 2 + 5) / this.square;
      int y = (line.getPoint(i).getY() - this.square / 2 + 5) / this.square;
      if (mode == "FIVET") {
        switch (line.getDirection()) {
        case "RISE":
          if (i != 4) points[x][y].lockDirection("UPRIGHT");
          if (i != 0) points[x][y].lockDirection("DOWNLEFT");
          break;
        case "FALL":
          if (i != 4) points[x][y].lockDirection("DOWNRIGHT");
          if (i != 0) points[x][y].lockDirection("UPLEFT");
          break;
        case "VERTI":
          if (i != 4) points[x][y].lockDirection("DOWN");
          if (i != 0) points[x][y].lockDirection("UP");
          break;
        case "HORI":
          if (i != 4) points[x][y].lockDirection("RIGHT");
          if (i != 0) points[x][y].lockDirection("LEFT");
          break;
        }
      }
      else if (mode == "FIVED") {
        switch (line.getDirection()) {
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
      minX = Math.min(minX, x - 1);
      minY = Math.min(minY, y - 1);
      maxX = Math.max(maxX, x + 1);
      maxY = Math.max(maxY, y + 1);
    }
  }

  /**
   * Check if a move is still possible from the player
   * if not, game is over
   *
   * @return every possible moves for the player
   */
  private List <Point> possibleMoves() {
    checkGameOver = true;
    List < Point > allMovesPossible = new ArrayList < Point > ();
    for (int i = minX; i <= maxX; i++) {
      for (int j = minY; j <= maxY; j++) {
        if (isPossibleLine(i, j)) {
          allMovesPossible.add(points[i][j]);
        }
      }
    }
    checkGameOver = false;
    return allMovesPossible;
  }

  /**
   * Gets the score.
   *
   * @return player score
   */
  public int getScore() {
    return score;
  }

  /**
   * Checks if it's game over.
   *
   * @return true if game is over, else false
   */
  public boolean isGameOver() {
    if (possibleMoves().size() == 0) return true;
    else return false;
  }

  /**
   * Checks if it's a new record on the app
   *
   * @return true if it's a new record, else false
   */
  public boolean isRecord() {
    if (isRecord) return true;
    else return false;
  }

}