/*
 * @author ALVESYohann_VIALLEHugo
 * @version 9
 */
package gameComponents;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Point {
  
  private int x;
  private int y;
  private Map <String,Boolean> lockedDirections; // false : direction not locked, true : direction locked

  /**
   * Instantiates a new point.
   *
   * @param x : coordinate X in frame
   * @param y : coordinate Y in frame
   */
  public Point(int x, int y) {
    this.x = x;
    this.y = y;
    this.lockedDirections = new HashMap <String,Boolean> ();
    setupDirections(lockedDirections);
  }

  /**
   * Gets the x coordinate
   *
   * @return x coordinate
   */
  public int getX() {
    return x;
  }

  /**
   * Gets the y coordinate
   *
   * @return y coordinate
   */
  public int getY() {
    return y;
  }

  /**
   * Lock point directions 
   * change in lockDirections HashMap the key (direction) value by true
   * @param direction : can be UP,DOWN,LEFT,RIGHT,UPLEFT,UPRIGHT,DOWNLEFT,DOWNRIGHT
   */
  public void lockDirection(String direction) {
    this.lockedDirections.replace(direction, true);
  }

  /**
   * Checks if the point is locked in the given direction.
   *
   * @param direction : can be UP,DOWN,LEFT,RIGHT,UPLEFT,UPRIGHT,DOWNLEFT,DOWNRIGHT
   * @return true if the direction is locked, else false
   */
  public boolean isLocked(String direction) {
    return this.lockedDirections.get(direction);
  }

  /**
   * Checks if the point is locked in the line direction
   *
   * @param dirX : 1 for HORI, RISE, and FALL, 0 for VERTI
   * @param dirY : 0 for HORI, -1 for RISE, 1 for FALL and VERTI
   * @return true if the point is locked in the line direction, else false
   */
  public boolean isLocked(int dirX, int dirY) {

    if (dirX == 1) {
      if (dirY == 0) {
        return (isLocked("LEFT") || isLocked("RIGHT"));
      }
      else if (dirY == -1) {
        return (isLocked("DOWNLEFT") || isLocked("UPRIGHT"));
      }
      else {
        return (isLocked("DOWNRIGHT") || isLocked("UPLEFT"));
      }
    }
    else {
      return (isLocked("UP") || isLocked("DOWN"));
    }

  }

  /**
   * Initiate directions on false
   *
   * @param map : Point directions associated with boolean isLocked
   * @see constructor
   */
  private void setupDirections(Map <String, Boolean> map) {
    map.put("UP", false);
    map.put("UPRIGHT", false);
    map.put("RIGHT", false);
    map.put("DOWNRIGHT", false);
    map.put("DOWN", false);
    map.put("DOWNLEFT", false);
    map.put("LEFT", false);
    map.put("UPLEFT", false);
  }

  /**
   * Copy point (on param) directions
   *
   * @param p : Point to copy
   */
  public void copyDirections(Point p) {
    this.lockedDirections = p.lockedDirections;
  }


 
  class PointSort implements Comparator < Point > {
	  
  	/**
  	 * Implementation of a Point Comparator to sort lines 
  	 *
  	 * @param o1 : Point 1 to compare
  	 * @param o2 : Point 2 to compare
  	 * @return 1 if o1.x > o2.x, -1 if o1.x < o2.x, else if o1.x=o2.x do the same with y
  	 * @see https://stackoverflow.com/questions/3077746/how-to-sort-an-array-of-objectspoints-in-java 
  	 */
  	@Override
    public int compare(Point o1, Point o2) {
      if (o1.getX() != o2.getX()) return (o1.getX() < o2.getX()) ? -1 : (o1.getX() > o2.getX()) ? 1 : 0;
      else return (o1.getY() < o2.getY()) ? -1 : (o1.getY() > o2.getY()) ? 1 : 0;
    }

  }
}