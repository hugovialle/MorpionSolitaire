package projetJava;
import java.awt.BasicStroke;
import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics2D;

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
		
		//dessine la ligne des 5 points
        ((Graphics2D) g).setStroke(new BasicStroke(3));
        g.setColor(Color.red);
		g.drawLine(line.get(0).getX()+4, line.get(0).getY()+4, line.get(4).getX()+4, line.get(4).getY()+4);
		
		g.setColor(Color.black);
		g.fillOval(this.getX()-(POINT_SIZE/4), this.getY()-(POINT_SIZE/4), POINT_SIZE+2, POINT_SIZE+2);
		g.setColor(Color.white);
		g.fillOval(this.getX()-(POINT_SIZE/4)+1, this.getY()-(POINT_SIZE/4)+1, POINT_SIZE, POINT_SIZE);
		g.setColor(Color.red);
		if(number<=9) {
			g.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 12));
			g.drawString(String.valueOf(number), this.getX()+1, this.getY()+9);
		}
		else if(number<=99){
			g.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 9));
			g.drawString(String.valueOf(number), this.getX()-1, this.getY()+9);
		}
		else {
			g.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 7));
			g.drawString(String.valueOf(number), this.getX()-2, this.getY()+9);
		}
		
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
