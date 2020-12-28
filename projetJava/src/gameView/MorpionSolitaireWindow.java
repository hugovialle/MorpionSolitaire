package gameView;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import gameManager.MorpionSolitaireBoard;

@SuppressWarnings("serial")
public class MorpionSolitaireWindow extends JFrame {

  private MorpionSolitaireBoard gameBoard;

  private OptionPanel optionPanel;

  public MorpionSolitaireWindow() {
    //Inputs by default :
	String username = "Unknown";
    String mode = "FIVET";
    String playerType = "HUMAN";
    JPanel container = new JPanel();
    this.setLayout(new BorderLayout());

    gameBoard = new MorpionSolitaireBoard(username, mode, playerType);
    optionPanel = new OptionPanel(this);
    optionPanel.setPreferredSize(new Dimension(300, 900));

    container.setLayout(new BorderLayout());
    container.add(gameBoard, BorderLayout.CENTER);
    container.add(optionPanel, BorderLayout.EAST);

    this.add(container);
    this.setTitle("Morpion solitaire");
    this.pack();
    this.setLocationRelativeTo(null);
  }

  public MorpionSolitaireBoard getBoard() {
    return this.gameBoard;
  }

  public void setBoard(MorpionSolitaireBoard gameBoard) {
    this.gameBoard = gameBoard;
  }

  public void restart() {
    this.gameBoard.restart();
  }

  public void newMode(String mode) {
    this.gameBoard.newMode(mode);
  }

  public void newPlayer(String player) {
    this.gameBoard.newPlayer(player);
  }

  public void newUsername(String username) {
    this.gameBoard.newUsername(username);
  }
}