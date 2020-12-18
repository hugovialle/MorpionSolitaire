package projetJava;



public class Point {
	private int x;
	private int y;
	
	public Point(int px, int py) {
		this.x = px;
		this.y = py;
	}
	
	public String toString() {
		return "("+x+","+y+")";
	}
	
	
	
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
}