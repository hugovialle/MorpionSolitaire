/*
 * @author ALVESYohann_VIALLEHugo
 * @version 11
 */
package gameComponents;

import java.awt.Graphics;
import java.util.ArrayList;

public class Line {

  private ArrayList < Point > line;
  
  private String direction;
  
  private Point clickedPoint;

  /**
   * Instantiates a new line.
   */
  public Line() {
    this.line = new ArrayList < Point > ();
    this.clickedPoint = null;
    this.direction = null;
  }

  /**
   * Gets the direction.
   *
   * @return the direction
   */
  public String getDirection() {
    return this.direction;
  }

  /**
   * Adds the point in Line
   *
   * @param p : point to be included in the line
   */
  public void addPoint(Point p) {
    line.add(p);
  }

  /**
   * Line size.
   *
   * @return the line size
   */
  public int lineSize() {
    return line.size();
  }

  /**
   * Set up the direction of the line. 
   * HORI : left/right
   * RISE : bottomleft/upright
   * FALL : upleft/bottomright
   * VERTI : up/bottom
   *
   * @param dirX : 1 for HORI, RISE, and FALL, 0 for VERTI
   * @param dirY : 0 for HORI, -1 for RISE, 1 for FALL and VERTI
   */
  public void direction(int dirX, int dirY) {
    if (dirX == 1 && dirY == 0) {
      this.direction = "HORI";
    }
    else if (dirX == 1 && dirY == -1) {
      this.direction = "RISE";
    }
    else if (dirX == 0 && dirY == 1) {
      this.direction = "VERTI";
    }
    else {
      this.direction = "FALL";
    }
  }

  /**
   * Gets the point at the i line index
   *
   * @param i : line index
   * @return Linepoint(i)
   */
  public Point getPoint(int i) {
    return line.get(i);
  }

  /**
   * Make the Object Line empty
   */
  public void clear() {
    this.line.clear();
  }

  /**
   * Draw the line in our graphic interface
   * source :  https://docs.oracle.com/javase/7/docs/api/java/awt/Graphics.html
   * 
   * @param g : Graphics class
   */
  public void draw(Graphics g) {
    g.drawLine(line.get(0).getX() + 4, line.get(0).getY() + 4, line.get(4).getX() + 4, line.get(4).getY() + 4);
  }

  /**
   * Copy this line into a new one.
   *
   * @return the new line 
   * using function : checkLine()
   */
  public Line copy() {
    Line l = new Line();
    l.line = new ArrayList < Point > (this.line);
    l.direction = this.direction;
    l.clickedPoint = this.clickedPoint;
    return l;
  }

  /**
   * Sets the clicked point.
   * Useful for remember the point clicked when player has to chose between propositions.
   *
   * @param cp : the movepoint clicked by the user
   * using function : checkLine()
   */
  public void setclickedPoint(Point cp) {
    this.clickedPoint = cp;
  }

  /**
   * Gets the clicked point.
   *
   * @return the movepoint clicked by the user
   */
  public Point getclickedPoint() {
    return clickedPoint;
  }
}