package projetJava;

import java.util.ArrayList;

public class Line<Point>{

	private ArrayList<Point> line;
	private String direction;
	
	public Line() {
		this.line = new ArrayList<Point>();
		
	}
	
	public void addPoint(Point p) {
		line.add(p);
	}
	
	public int lineSize() {
		return line.size();
	}
	
	public Point getPoint(int i) {
		return line.get(i);
	}
	
	
	
}
