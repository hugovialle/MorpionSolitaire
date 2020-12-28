/*
 * @author ALVESYohann_VIALLEHugo
 * @version 18
 */
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

import gameManager.Scoreboard;

@SuppressWarnings("serial")
public class OptionPanel extends JPanel {

  private MorpionSolitaireWindow gameWindow;
  private JButton restartBtn,gridBtn,textBtn;
  private JTextField text;
  private JPanel scoreBoard;
  private JComboBox <String> gameType, playerType;

  /**
   * Instantiates a new option panel (right side of the window).
   * 
   * @param gameWindow : the frame of the window
   */
  public OptionPanel(MorpionSolitaireWindow gameWindow) {
    BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
    this.setLayout(boxlayout);

    this.gameWindow = gameWindow;

    OptionListener ol = new OptionListener();

    final String[] gameMode = {
      "5T",
      "5D"
    };
    final String[] playerMode = {
      "Human",
      "Computer"
    };
    gameType = new JComboBox <>(gameMode);
    gameType.addActionListener(ol);

    playerType = new JComboBox <>(playerMode);
    playerType.addActionListener(ol);

    restartBtn = new JButton("Restart");
    restartBtn.addActionListener(ol);
    JPanel username = new JPanel();
    JPanel top = new JPanel();
    JPanel middle = new JPanel();
    JPanel bottom = new JPanel();

    gridBtn = new JButton("Best grid");
    gridBtn.addActionListener(ol);

    text = new JTextField("Write username", 20);
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

  /**
   * Gets the game window.
   *
   * @return the game window
   */
  public MorpionSolitaireWindow getGameWindow() {
    return this.gameWindow;
  }

  /**
   * Sets the new game window
   *
   * @param gameWindow : the new game window
   */
  public void setGameWindow(MorpionSolitaireWindow gameWindow) {
    this.gameWindow = gameWindow;
  }

  /**
   * Gets the game type.
   *
   * @param gameType : 5D or 5T
   * @return FIVED or FIVET
   */
  private String getGameType(JComboBox <String> gameType) {

    String type = "" + gameType.getSelectedItem();
    if (type.equals("5D")) {
      return "FIVED";
    }
    else return "FIVET";
  }

  /**
   * Gets the player type.
   *
   * @param playerType : HUMAN or COMPUTER
   * @return HUMAN or COMPUTER
   */
  private String getPlayerType(JComboBox <String> playerType) {

    String player = "" + playerType.getSelectedItem();
    if (player.equals("Human")) {
      return "HUMAN";
    }
    else return "COMPUTER";
  }

  /**
   * The listener interface for receiving option events.
   * The class that is interested in processing a option
   * event implements this interface, and the object created
   * with that class is registered with a component using the
   * component's addOptionListener method. When
   * the option event occurs, that object's appropriate
   * method is invoked.
   *
   * @see OptionEvent
   * @see https://docs.oracle.com/javase/tutorial/uiswing/events/componentlistener.html
   * 
   */
  private class OptionListener implements ActionListener {

    /**
     * Every action done by user is invoking this function
     *
     * @param e : event created by an action
     */
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
          File outputfile = new File("src/record.png");
          try {
            ImageIO.write(img, "png", outputfile);
          } catch(IOException e2) {
            e2.printStackTrace();
          }
        }
      }
      else if (src == textBtn) {
        gameWindow.newUsername(text.getText());
        text.setText("Enter a username");
      }
      else if (src == gameType) {
        String selected = getGameType(gameType);
        gameWindow.newMode(selected);
      }
      else if (src == playerType) {
        String selected = getPlayerType(playerType);
        gameWindow.newPlayer(selected);
      }
      else if (src == gridBtn) {
        JFrame frame = new JFrame();
        frame.setTitle("Best grid");
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon("src/record.png"));
        frame.add(imageLabel);
        frame.pack();
        frame.setVisible(true);
      }

    }
  }
}