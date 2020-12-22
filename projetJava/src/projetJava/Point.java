package projetJava;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Point {
    private int x;
    private int y;
    private Map <String, Boolean> lockedDirections; // false : direction not locked, true : direction locked
  
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.lockedDirections = new HashMap<String, Boolean>();
        setupDirections(lockedDirections);
    }


	public int getX() {
        return x;
    }

 
    public int getY() {
        return y;
    }
    
    public void lockDirection (String direction) {
    	this.lockedDirections.put(direction, true);
    }
    
    public boolean isLocked(String direction) {
    	return this.lockedDirections.get(direction);
    }
    
    private void setupDirections(Map<String, Boolean> map) {
		// TODO Auto-generated method stub
		map.put("UP",false);
		map.put("UPRIGHT",false);
		map.put("RIGHT",false);
		map.put("DOWNRIGHT",false);
		map.put("DOWN",false);
		map.put("DOWNLEFT",false);
		map.put("LEFT",false);
		map.put("UPLEFT",false);
	}
    
    
    public boolean lineValidation(ArrayList<Point> line) {
		// sort Points in the line ArrayList (cf PointSort class below)
    	
		Collections.sort(line, new PointSort());
		if (line.get(0).getX() < line.get(1).getX()) { 
			if (line.get(0).getY() == line.get(1).getY()) { //horizontal lock checker
				for (int i = 1; i < line.size()-1 ; i++) {
					if (line.get(0).isLocked("RIGHT") || (line.get(i).isLocked("RIGHT") && line.get(i).isLocked("LEFT"))
							|| line.get(line.size()-1).isLocked("LEFT")) return false;
				}
			}
			if(line.get(0).getY() > line.get(1).getY()){ //diagonal DOWNLEFT->UPRIGHT lock checker
				for (int i = 1; i < line.size()-1 ; i++) {
					if (line.get(0).isLocked("UPRIGHT") || (line.get(i).isLocked("UPRIGHT") && line.get(i).isLocked("DOWNLEFT"))
							|| line.get(line.size()-1).isLocked("DOWNLEFT")) return false;
				}
			}
		}
		if (line.get(0).getY() < line.get(1).getY()) { 
			if (line.get(0).getX() == line.get(1).getX()) { //vertical lock checker
				for (int i = 1; i < line.size()-1; i++) {
					if (line.get(0).isLocked("UP") || (line.get(i).isLocked("UP") && line.get(i).isLocked("DOWN"))
							|| line.get(line.size()-1).isLocked("DOWN")) return false;
				}
			}
			if(line.get(0).getX() < line.get(1).getX()){ //diagonal UPLEFT->DOWNRIGHT lock checker
				for (int i = 1; i < line.size()-1; i++) {
					if (line.get(0).isLocked("DOWNRIGHT") || (line.get(i).isLocked("DOWNRIGHT") && line.get(i).isLocked("UPLEFT"))
							|| line.get(line.size()-1).isLocked("UPLEFT")) return false;
				}
			}
		}
		// If the points of the line are not locked in the line direction then :
		return true;
	}
    
 // Impl�mentation d'une comparaison entre points
 	// Source : https://stackoverflow.com/questions/3077746/how-to-sort-an-array-of-objectspoints-in-java 
 	class PointSort implements Comparator<Point>{
 		@Override
 		public int compare(Point o1, Point o2) {
 			if (o1.getX() != o2.getX()) return (o1.getX() < o2.getX())? -1 : (o1.getX() > o2.getX()) ? 1 : 0;
 			else  return (o1.getY() < o2.getY())? -1 : (o1.getY() > o2.getY()) ? 1 : 0;
 		}

 	}
}
