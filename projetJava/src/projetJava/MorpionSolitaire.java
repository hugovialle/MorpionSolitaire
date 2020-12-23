package projetJavaTESTLINES;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;



@SuppressWarnings("serial")
public class MorpionSolitaire extends JPanel{

	Grid grid;
	int height, width;
	typeGame currentType;
	
	enum typeGame{
		FIVET, FIVED }
	
	public MorpionSolitaire(typeGame type) {
		setPreferredSize(new Dimension(900,900));
        setBackground(Color.white);
        this.grid = new Grid(30,900,900);
        
        addMouseListener(new MouseAdapter() {
			@Override
            public void mousePressed(MouseEvent e) {
                grid.move(e.getX(), e.getY());
                
                repaint();
            }
        });

    }
	
	@Override
    public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	            RenderingHints.VALUE_ANTIALIAS_ON); 
		
		g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
	            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		grid.draw(g2D);    
	}
	
	
}
