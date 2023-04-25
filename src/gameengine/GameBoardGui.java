package gameengine;

import gameplayers.Player;
import tokens.Token;
import tokens.TokenType;

import java.awt.*;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Represents the GameBoard in which Intersections exist.
 */
public class GameBoardGui extends JPanel {
  private final Image backgroundImage;
  private final int[] X = {208,208,208,265,265,265,320,320,320,375,542,430,487,542,375,375,430,430,375,375,375,542,487,487};
  private final int[] Y = {178,343,508,234,343,452,288,343,398,178,178,343,343,343,234,288,288,398,398,452,508,508,452,234};
  private final Game currentGame;

  private JLabel playerTurnLabel;
  /**
   * Constructor for the GameBoard, puts everything together.
   * @throws IOException Exception if the background image resource is not found.
   */
  public GameBoardGui(Game newGame) throws IOException {
    this.currentGame = newGame;

    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    String path = "img/BoardImages/board600pxls.png";
    backgroundImage = ImageIO.read(new File(path));

    playerTurnLabel = new JLabel();
    playerTurnLabel.setText("Player 1 Turn!");
    playerTurnLabel.setSize(500,500);
    playerTurnLabel.setLocation(280,370);
    playerTurnLabel.setFont(new Font("BM Hanna 11Yrs Old", Font.PLAIN, 35));
    add(playerTurnLabel);


    for (int i = 0; i < X.length; i++) {
      Intersection button = this.newButton(X[i],Y[i]);
      add(button);
    }

    /*JButton label = this.newButton(115,282);
    label.setIcon(new ImageIcon(new ImageIcon("img/BoardImages/cover.png").getImage().getScaledInstance(70, 50, Image.SCALE_SMOOTH)));
    JButton label2 = this.newButton(115,297);
    label2.setIcon(new ImageIcon(new ImageIcon("img/BoardImages/cover.png").getImage().getScaledInstance(70, 50, Image.SCALE_SMOOTH)));
    add(label);
    add(label2);*/

    this.setLayout(null);
  }

  /**
   * Creates a new Intersection with the given coordinates on the GameBoard JPanel.
   *
   * @param x The x coordinate of the new Intersection.
   * @param y The y coordinate of the new Intersection.
   * @return The newly created Intersection.
   */
  public Intersection newButton(int x, int y) {
    Intersection button = new Intersection(this.currentGame);
    button.setLocation(x, y);
    button.setSize(50,50);
    button.setOpaque(false);
    button.setContentAreaFilled(false);
    button.setBorderPainted(false);
    button.setFocusPainted(false);
    button.setIcon(new ImageIcon(new ImageIcon("").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
    button.addActionListener(button);
    return button;
  }

  /**
   * Built in class that draws the background of the GameBoard.
   *
   * @param g - Graphic image that is to be background.
   */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    // Draw the background image.
    Image scaled = backgroundImage.getScaledInstance(600, 600, Image.SCALE_DEFAULT);
    g.drawImage(scaled, 100, 100, this);
  }

  /**
   * Create the GUI by creating a new frame and adding the constraints.
   *
   * @throws IOException Throws exception upstream from GameBoardGui.
   */
  public void createGUI() throws IOException {
    JFrame frame = new JFrame("Nine Men's Morris");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);

    // Create and set up the content pane.
    frame.getContentPane().add(new GameBoardGui(this.currentGame));
    frame.setSize(800, 800);

    // Display the window.
    frame.setVisible(true);
  }

  /**
   * Scale an image to whichever size is needed.
   *
   * @param path The path to the image resource for the ImageIcon.
   * @param width The scaled width of the ImageIcon.
   * @param height The scaled height of the ImageIcon.
   * @return The scaled ImageIcon.
   */
  private ImageIcon getScaledImage(String path, int width, int height) {
    ImageIcon imageIcon = new ImageIcon(path);
    Image image = imageIcon.getImage();
    Image newImage =
        image.getScaledInstance(
            width, height, java.awt.Image.SCALE_SMOOTH);
    imageIcon = new ImageIcon(newImage);
    return imageIcon;
  }

  private void decreasePlayerTokens(Token token, Player player){
    int whiteTokenCount = 0;
    int blackTokenCount = 0;
    int current = 0;

    int X = 140;
    int Y = 288;
    int dist = 10;

    if (token.getTokenType() == TokenType.WHITE){
      X = 640;
      whiteTokenCount = player.getTokenCount();
      current = whiteTokenCount;
    }
    else {
      blackTokenCount = player.getTokenCount();
      current = blackTokenCount;
    }

    for (int i = 0; i < current; i++) {
      JLabel label = new JLabel();
      label.setLocation(X,Y+(current * dist));
      label.setIcon(new ImageIcon("img/BoardImages/cover.png"));
      add(label);
    }
  }

  public void updatePlayerTurnDisplay(String newDisplay){


  }
}

