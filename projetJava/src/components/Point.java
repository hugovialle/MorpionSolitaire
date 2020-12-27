package components;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Point  {
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
    
    public void lockDirection(String direction) {
    	this.lockedDirections.replace(direction, true);
    }
    
    public boolean isLocked(String direction) {
    	return this.lockedDirections.get(direction);
    }
    
    public boolean isLocked(int x, int y) {
    	
    		if(x==1) {
        		if(y==0) {
        			return (isLocked("LEFT") || isLocked("RIGHT"));
        		}
        		else if(y==-1) {
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
    
   
    private void setupDirections(Map<String, Boolean> map) {
		map.put("UP",false);
		map.put("UPRIGHT",false);
		map.put("RIGHT",false);
		map.put("DOWNRIGHT",false);
		map.put("DOWN",false);
		map.put("DOWNLEFT",false);
		map.put("LEFT",false);
		map.put("UPLEFT",false);
	}
    
    public void copyDirections(Point p) {
    	this.lockedDirections = p.lockedDirections;
    }
    
    public void printDirections() {
    	System.out.print(this.getX() + ","+this.getY() + " : ");
    	for (String key : lockedDirections.keySet()) {
    	    if(lockedDirections.get(key)) {
    	    	System.out.print(key + " , ");
    	    }
    	}
    	System.out.println("");
    }
    
    // Implementation d'une comparaison entre points
 	// Source : https://stackoverflow.com/questions/3077746/how-to-sort-an-array-of-objectspoints-in-java 
 	class PointSort implements Comparator<Point>{
 		@Override
 		public int compare(Point o1, Point o2) {
 			if (o1.getX() != o2.getX()) return (o1.getX() < o2.getX())? -1 : (o1.getX() > o2.getX()) ? 1 : 0;
 			else  return (o1.getY() < o2.getY())? -1 : (o1.getY() > o2.getY()) ? 1 : 0;
 		}

 	}
}
