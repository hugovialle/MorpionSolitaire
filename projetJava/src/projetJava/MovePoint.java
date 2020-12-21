package projetJava;
import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;


public class MovePoint extends Point {

	private int number;
	final static int POINT_SIZE=15;
	private ArrayList<Point> line;

	public MovePoint(int x, int y, ArrayList<Point> line, int number) {	
		super(x,y);
		this.number=number;
		this.line = line;
		
	}
	
	
	
	
	public void draw(Graphics g) {
		
		g.setColor(Color.black);
		g.fillOval(this.getX()-(POINT_SIZE/2), this.getY()-(POINT_SIZE/2), POINT_SIZE+2, POINT_SIZE+2);
		g.setColor(Color.white);
		g.fillOval(this.getX()-(POINT_SIZE/2)+1, this.getY()-(POINT_SIZE/2)+1, POINT_SIZE, POINT_SIZE);
		g.setColor(Color.black);
		g.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 8));
		g.drawString(String.valueOf(number), this.getX()-5, this.getY()+5);
		
		
		
		//dessine la ligne des 5 points
		g.drawLine(line.get(0).getX(), line.get(0).getY(), line.get(4).getX(), line.get(4).getY());
		lockDirections();
		
		
	}

	
	/* Every point has 8 possible directions (see Point class)
	 * Lock a direction is used to avoid players to overlay lines, which is one of the game constraints
	 * If every directions of a point are locked, then this point can't be used to create another line.
	 */
	
	private void lockDirections() {
		
		if (line.get(0).getX() < line.get(1).getX()) { 
			if (line.get(0).getY() == line.get(1).getY()) { //horizontal lock 
				for (Point p : line) {
					if (p != line.get(0)) {
						p.lockDirection("LEFT");
					}
					if (p != line.get(4)) {
						p.lockDirection("RIGHT");
					}
				}
			}
			if(line.get(0).getY() > line.get(1).getY()){ //diagonal DOWNLEFT->UPRIGHT lock 
				for (Point p : line) {
				if (p != line.get(0)) {
					p.lockDirection("DOWNLEFT");
				}
				if (p != line.get(4)) {
					p.lockDirection("UPRIGHT");
				}
				}
			}
		}
		if (line.get(0).getY() < line.get(1).getY()) { 
			if (line.get(0).getX() == line.get(1).getX()) { //vertical lock 
				for (Point p : line) {
					if (p != line.get(0)) {
						p.lockDirection("UP");
					}
					if (p != line.get(4)) {
						p.lockDirection("DOWN");
					}
				}
			}
			if(line.get(0).getX() < line.get(1).getX()){ //diagonal UPLEFT->DOWNRIGHT lock 
				for (Point p : line) {
				if (p != line.get(0)) {
					p.lockDirection("UPLEFT");
				}
				if (p != line.get(4)) {
					p.lockDirection("DOWNRIGHT");
				}
				}
			}
		}
		
	}

}
