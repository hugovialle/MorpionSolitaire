/*
 * @author ALVESYohann_VIALLEHugo
 * @version 8
 */
package gameComponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class MovePoint extends Point {

  private int number;
  private final static int POINT_SIZE = 15;
  private final static int FONT_SIZE = 9;

  /**
   * Instantiates a new move point.
   *
   * @param x : coordinate X in frame
   * @param y : coordinate Y in frame
   * @param number : (number+1) of last move point
   */
  public MovePoint(int x, int y, int number) {
    super(x, y);
    this.number = number;
  }

  /**
   * Gets the number.
   *
   * @return number
   */
  public int getNumber() {
    return this.number;
  }

  /**
   * Draw the MovePoint in our graphic interface
   * source : https://docs.oracle.com/javase/7/docs/api/java/awt/Graphics.html
   * 
   * @param g : Graphics class
   
   */
  public void draw(Graphics g) {
    g.setColor(Color.black);
    g.fillOval(this.getX() - (POINT_SIZE / 4), this.getY() - (POINT_SIZE / 4), POINT_SIZE + 2, POINT_SIZE + 2);
    g.setColor(Color.white);
    g.fillOval(this.getX() - (POINT_SIZE / 4) + 1, this.getY() - (POINT_SIZE / 4) + 1, POINT_SIZE, POINT_SIZE);
    g.setColor(Color.red);
    if (number <= 9) {
      g.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 12));
      g.drawString(String.valueOf(number), this.getX() + 1, this.getY() + FONT_SIZE);
    }
    else if (number <= 99) {
      g.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 9));
      g.drawString(String.valueOf(number), this.getX() - 1, this.getY() + FONT_SIZE);
    }
    else {
      g.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 7));
      g.drawString(String.valueOf(number), this.getX() - 2, this.getY() + FONT_SIZE);
    }
  }

}