package projetJava;


import java.awt.Color;

import java.awt.Font;


import java.awt.Graphics;



public class MovePoint extends Point {

	private int number;
	final static int POINT_SIZE=15;

	public MovePoint(int x, int y,  int number) {	
		super(x,y);
		this.number=number;
		
	}
	
	public int getNumber() {
		return this.number;
	}
	
	public void draw(Graphics g) {
		
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
	}

}
