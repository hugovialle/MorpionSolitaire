package gameView;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import gameManager.Grid;



@SuppressWarnings("serial")
public class MorpionSolitaireBoard extends JPanel{

	private Grid grid;
	private String username;
	private String gameType;
	private String playerType;
	private JLabel scoreLabel;
	private int score;

	public MorpionSolitaireBoard(String username, String gameType, String playerType) {
		this.username=username;
		this.gameType=gameType;
		this.playerType=playerType;
		setPreferredSize(new Dimension(900,900));
        setBackground(Color.white);
        
        if (playerType == "HUMAN") {
        this.grid = new Grid(30,900,900,gameType, username);
        this.score=this.grid.getScore();
        scoreLabel = new JLabel("Score : "+this.grid.getScore());
		scoreLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 25));
	    scoreLabel.setBackground(Color.white);
		scoreLabel.setOpaque(true);

		this.add(scoreLabel);
        addMouseListener(new MouseAdapter() {
			@Override
            public void mousePressed(MouseEvent e) {
                grid.move(e.getX(), e.getY());
                score = grid.getScore();
                if(grid.isGameOver()) {
                	scoreLabel.setText("GAME OVER - Score : "+score);
                }
                else {
                	scoreLabel.setText("Score : "+score);
                }
                repaint();
            }
        });
        }
        else if (playerType =="COMPUTER") {
        	this.grid = new Grid(30,900,900,gameType, username);
				while (grid.launchAlgorithm()) {
					score = grid.getScore();
					scoreLabel.setText("Score : "+score);
					repaint();
				}
				
        }
        new Scoreboard();
        
    }
	
	@Override
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		// better graphics for the window (see https://docs.oracle.com/javase/tutorial/2d/advanced/quality.html)
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
		grid.draw(g2D);    
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getGameType() {
		return this.gameType;
	}
	
	public String getPlayerType() {
		return this.playerType;
	}

	public void restart() {
		this.grid = new Grid(30,900,900,this.gameType, this.username);
		resetScore();
		this.repaint();
		if(this.playerType=="COMPUTER") {
			while (grid.launchAlgorithm()) {
				score = grid.getScore();
				if(grid.isGameOver()) {
                	scoreLabel.setText("GAME OVER - Score : "+score);
                }
                else {
                	scoreLabel.setText("Score : "+score);
                }
				repaint();
			}
		}
	}
	
	public void newMode(String mode) {
		this.gameType=mode;
		restart();
	}
	
	public void newPlayer(String player) {
		this.playerType=player;
		restart();
	}
	
	public void resetScore() {
		this.score=0;
		scoreLabel.setText("Score : "+score);
	}
	
	public void newUsername(String username) {
		this.username=username;
		restart();
	}
	
	public boolean isRecord() {
		if (this.grid.isRecord()) return true;
		else return false;
		
	}
}
