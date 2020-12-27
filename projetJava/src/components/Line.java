package components;


import java.awt.Graphics;
import java.util.ArrayList;

public class Line{

	private ArrayList<Point> line;
	private String direction;
	private Point clickedPoint;
	
	public Line() {
		this.line = new ArrayList<Point>();
		this.clickedPoint = null;
		this.direction = null;
	}
	
	public String getDirection() {
		return this.direction;
	}
	
	public void addPoint(Point p) {
		line.add(p);
	}
	
	
	public int lineSize() {
		return line.size();
	}
	
	public void direction(int dirX, int dirY) {
		if(dirX==1 && dirY==0) { 
			this.direction="HORI";
		}
		else if(dirX==1 && dirY==-1) {
			this.direction="RISE";
		}
		else if(dirX==0 && dirY==1) {
			this.direction="VERTI";
		}
		else {
			this.direction="FALL";
		}
	}
	
	public Point getPoint(int i) {
		return line.get(i);
	}
	
	public void clear() {
		this.line.clear();
	}
	
	public void draw(Graphics g) {
		g.drawLine(line.get(0).getX()+4, line.get(0).getY()+4, line.get(4).getX()+4, line.get(4).getY()+4);
	}
	
	public Line copy() {
		Line l = new Line();
		l.line = new ArrayList<Point>(this.line);
		l.direction = this.direction;
		l.clickedPoint = this.clickedPoint;
		return l;
	}

	public void setclickedPoint(Point indexclickedPoint) {
		this.clickedPoint = indexclickedPoint;
	}
	
	public Point getIndexclickedPoint() {
		return clickedPoint;
	}
}
