package projetJavaTESTLINES;

import java.awt.Graphics;
import java.util.ArrayList;

public class Line{

	private ArrayList<Point> line;
	private String direction;
	
	public Line() {
		this.line = new ArrayList<Point>();
		
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
	
//	public void lockPointsDirections() {
//		switch(this.direction) {
//		case("RISE"):
//			for(int i = 0; i<line.size(); i++) {
//				if(i!=4) line.get(i).lockDirection("UPRIGHT");
//				if(i!=0) line.get(i).lockDirection("DOWNLEFT");
//			}
//		case("FALL"):
//			for(int i = 0; i<line.size(); i++) {
//				if(i!=4) line.get(i).lockDirection("UPLEFT");
//				if(i!=0) line.get(i).lockDirection("DOWRIGHT");
//			}
//		case("VERTI"):
//			for(int i = 0; i<line.size(); i++) {
//				if(i!=4) line.get(i).lockDirection("UP");
//				if(i!=0) line.get(i).lockDirection("DOWN");
//			}
//		case("HORI"):
//			for(int i = 0; i<line.size(); i++) {
//				if(i!=4) line.get(i).lockDirection("RIGHT");
//				if(i!=0) line.get(i).lockDirection("LEFT");
//			}
//		}
//	}
}
