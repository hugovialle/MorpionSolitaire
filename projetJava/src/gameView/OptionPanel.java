package gameView;

import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class OptionPanel extends JPanel{
	
	private MorpionSolitaireWindow gameWindow;
	
	/** The button that when clicked, restarts the game. */
	private JButton restartBtn;
	
	private JButton gridBtn;
	
	private JTextField text; 
	
	private JButton textBtn;
	
	private JPanel scoreBoard;
		
	private JComboBox<String> gameType;
	private JComboBox<String> playerType;
	
	public OptionPanel(MorpionSolitaireWindow gameWindow) {		
		BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(boxlayout);
		
		this.gameWindow = gameWindow;
		
		OptionListener ol = new OptionListener();
		
		final String[] gameMode = {"5T", "5D"};
		final String[] playerMode = {"Human", "Computer"};
		gameType = new JComboBox<>(gameMode);
		gameType.addActionListener(ol);
		
		playerType = new JComboBox<>(playerMode);
		playerType.addActionListener(ol);
		
		restartBtn = new JButton("Restart");
		restartBtn.addActionListener(ol);
		JPanel username = new JPanel();
		JPanel top = new JPanel();
		JPanel middle = new JPanel();
		JPanel bottom = new JPanel();
		
		gridBtn = new JButton("Best grid");
		gridBtn.addActionListener(ol);
		
        text = new JTextField("Enter your username", 16); 
        textBtn = new JButton("Enter");
        textBtn.addActionListener(ol);
        
        scoreBoard = new Scoreboard();
		
        username.add(text);
        username.add(textBtn);
		top.add(new JLabel("Select player : "));
		top.add(playerType);		
		
		middle.add(new JLabel("Select grid type : "));
		middle.add(gameType);
		
		bottom.add(restartBtn);
		bottom.add(gridBtn);
		
		this.add(username);
		this.add(top);
		this.add(middle);
		this.add(bottom);
		this.add(Box.createRigidArea(new Dimension(300, 350)));
		this.add(scoreBoard);
		
	}
	
	public MorpionSolitaireWindow getGameWindow() {
		return this.gameWindow;
	}
	
	public void setGameWindow(MorpionSolitaireWindow gameWindow) {
		this.gameWindow=gameWindow;
	}
	
	private String getGameType(JComboBox<String> gameType) {

		String type = "" + gameType.getSelectedItem();
		if(type.equals("5D")) {
			return "FIVED";
		}
		else return "FIVET";
	}
	
	private String getPlayerType(JComboBox<String> playerType) {

		String player = "" + playerType.getSelectedItem();
		if(player.equals("Human")) {
			return "HUMAN";
		}
		else return "COMPUTER";
	}
	
	
	private class OptionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			// No window to update
			if (gameWindow == null) {
				return;
			}
			
			Object src = e.getSource();

			if (src == restartBtn) {
				gameWindow.restart();
				if (gameWindow.getBoard().isRecord()) {
	            	BufferedImage img = new BufferedImage(gameWindow.getBoard().getWidth(), gameWindow.getBoard().getHeight(), BufferedImage.TYPE_INT_RGB);
	            	gameWindow.getBoard().paint(img.getGraphics());
	                File outputfile = new File("/Users/hugovialle/Desktop/MorpionSolitaire/projetJava/src/record.png");
	                try {
	    				ImageIO.write(img, "png", outputfile);
	    			} catch (IOException e2) {
	    				e2.printStackTrace();
	    			}
	            }
			}
			else if(src == textBtn) {
				System.out.println("Here");
				gameWindow.newUsername(text.getText());
				text.setText("Enter a username");
			}
			else if (src == gameType) {
				String selected = getGameType(gameType);
				gameWindow.newMode(selected);
			} 
			else if(src == playerType) {
				String selected = getPlayerType(playerType);
				gameWindow.newPlayer(selected);
			} 
			else if(src == gridBtn) {
				JFrame frame = new JFrame();
				frame.setTitle("Best grid");
			    JLabel imageLabel = new JLabel(); 
		        imageLabel.setIcon(new ImageIcon("/Users/hugovialle/Desktop/MorpionSolitaire/projetJava/src/record.png"));
			    frame.add(imageLabel);
			    frame.pack(); 
				frame.setVisible(true);
			}
			
		}
	}
}
