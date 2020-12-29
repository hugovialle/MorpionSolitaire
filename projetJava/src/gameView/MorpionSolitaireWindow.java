/*
 * @author ALVESYohann_VIALLEHugo
 * @version 18
 */
package gameView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MorpionSolitaireWindow extends JFrame {

  private MorpionSolitaireBoard gameBoard;
  private OptionPanel optionPanel;

  /**
   * Instantiates a new morpion solitaire window (the entire screen)
   * Call MorpionSolitaireBoard (grid on the left side) and OptionPanel (options on the right side)
   * to create the entire app graphic interface.
   */
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

  /**
   * Gets the board (window left side)
   *
   * @return gameBoard
   */
  public MorpionSolitaireBoard getBoard() {
    return this.gameBoard;
  }

  /**
   * Sets a new board
   *
   * @param gameBoard the new board implemented
   */
  public void setBoard(MorpionSolitaireBoard gameBoard) {
    this.gameBoard = gameBoard;
  }

  /**
   * Restart.
   * @see MorpionSolitaireBoard : restart() function
   */
  public void restart() {
    this.gameBoard.restart();
  }

  /**
   * Refresh the mode chosen by the player
   *
   * @param mode : FIVED or FIVET
   * @see MorpionSolitaireBoard : same function
   */
  public void newMode(String mode) {
    this.gameBoard.newMode(mode);
  }

  /**
   * Refresh the type of playing
   *
   * @param player : HUMAN or COMPUTER
   * @see MorpionSolitaireBoard : same function
   */
  public void newPlayer(String player) {
    this.gameBoard.newPlayer(player);
  }

  /**
   * Refresh the player username
   *
   * @param username : the new username
   * @see MorpionSolitaireBoard : same function
   */
  public void newUsername(String username) {
    this.gameBoard.newUsername(username);
  }
}