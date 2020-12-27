package gameView;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import gameManager.Grid;



@SuppressWarnings("serial")
public class MorpionSolitaire extends JPanel{

	private Grid grid;

	public MorpionSolitaire(String username, String gameType, String playerType) {
		setPreferredSize(new Dimension(900,900));
        setBackground(Color.white);
        
        if (playerType == "HUMAN") {
        this.grid = new Grid(30,900,900,gameType, username);
        addMouseListener(new MouseAdapter() {
			@Override
            public void mousePressed(MouseEvent e) {
                grid.move(e.getX(), e.getY());
                repaint();
            }
        });
        }
        else if (playerType =="COMPUTER") {
        	this.grid = new Grid(30,900,900,gameType, username);
				while (grid.launchAlgorithm()) {
					repaint();
				}
        }
        Scoreboard sb = new Scoreboard();
        sb.readScoreboard();
      
        
    }
	
	@Override
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		// better graphics for the window (see https://docs.oracle.com/javase/tutorial/2d/advanced/quality.html)
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
		grid.draw(g2D);    
	}
	
	
}
