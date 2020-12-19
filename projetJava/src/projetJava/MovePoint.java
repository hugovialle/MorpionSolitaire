package projetJava;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

@SuppressWarnings("serial")


public class MovePoint extends Point {

	int number;
	final static int POINT_SIZE=15;
	
	public MovePoint(int x, int y, int number) {
		super(x,y);
		this.number=number;
	}
	
	public void draw(Graphics g) {
		
		g.setColor(Color.black);
		g.fillOval(x-(POINT_SIZE/2), y-(POINT_SIZE/2), POINT_SIZE+2, POINT_SIZE+2);
		g.setColor(Color.white);
		g.fillOval(x-(POINT_SIZE/2)+1, y-(POINT_SIZE/2)+1, POINT_SIZE, POINT_SIZE);
		g.setColor(Color.black);
        g.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 8));
		g.drawString(String.valueOf(number), this.x-5, this.y+5);

	}
}
