package gameengine;

import java.awt.*;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;
import tokens.TokenType;

/** Represents the GameBoard in which Intersections exist. */
public class GameBoardGui extends JPanel {

  private final Image backgroundImage;

  private final int[] X = {
    208, 208, 208, 265, 265, 265, 320, 320, 320, 375, 542, 430, 487, 542, 375, 375, 430, 430, 375,
    375, 375, 542, 487, 487
  };
  private final int[] Y = {
    178, 343, 508, 234, 343, 452, 288, 343, 398, 178, 178, 343, 343, 343, 234, 288, 288, 398, 398,
    452, 508, 508, 452, 234
  };
  private final Game currentGame;

  private final int[][] COORDINATES = {
    {0, 0, 3, 3}, {0, 3, 1, 3}, {0, 6, 3, 3}, {1, 1, 2, 2}, {1, 3, 1, 2}, {1, 5, 2, 2},
    {2, 2, 1, 1}, {2, 3, 1, 1}, {2, 4, 1, 1}, {3, 0, 3, 1}, {6, 0, 3, 3}, {4, 3, 1, 1},
    {5, 3, 1, 2}, {6, 3, 1, 3}, {3, 1, 2, 1}, {3, 2, 1, 1}, {4, 2, 1, 1}, {4, 4, 1, 1},
    {3, 4, 1, 1}, {3, 5, 2, 1}, {3, 6, 3, 1}, {6, 6, 3, 3}, {5, 5, 2, 2}, {5, 1, 2, 2}
  };

  HashMap<String, Intersection> intersectionMap = new HashMap<>();

  private String winnerDisplayerString = "";

  /**
   * Constructor for the GameBoard, puts everything together.
   *
   * @throws IOException Exception if the background image resource is not found.
   */
  public GameBoardGui(Game newGame) throws IOException {
    this.currentGame = newGame;

    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    String path = "/resources/META-INF/img/BoardImages/board600pxls.png";
    backgroundImage = ImageIO.read(getClass().getResource(path));
    for (int i = 0; i < X.length; i++) {

      String intersectionKey = String.valueOf(COORDINATES[i][0]) + COORDINATES[i][1];

      Intersection button = this.newButton(X[i], Y[i], COORDINATES[i]);
      this.intersectionMap.put(intersectionKey, button);
      add(button);
    }
    this.setLayout(null);
  }

  /**
   * Creates a new Intersection with the given coordinates on the GameBoard JPanel.
   *
   * @param x The x coordinate of the new Intersection.
   * @param y The y coordinate of the new Intersection.
   * @return The newly created Intersection.
   */
  public Intersection newButton(int x, int y, int[] coordinates) {
    Intersection button = new Intersection(this.currentGame, coordinates);
    button.setLocation(x, y);
    button.setSize(50, 50);
    button.setOpaque(false);
    button.setContentAreaFilled(false);
    button.setBorderPainted(false);
    button.setFocusPainted(false);
    button.setIcon(
        new ImageIcon(new ImageIcon("").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
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

  /** Create the GUI by creating a new frame and adding the constraints. */
  public void createGui() {
    JFrame frame = new JFrame("Nine Men's Morris");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);

    // Create and set up the content pane.
    frame.getContentPane().add(this);
    frame.setSize(800, 800);

    // Display the window.
    frame.setVisible(true);
  }

  /**
   * Adds the image path to the winner display and shows it on the GUI
   *
   * @param image - string of the image path from root
   */
  public void showWinnerDisplay(String image) {
    setWinnerDisplayerString(image);
    add(winningPlayerDisplay(winnerDisplayerString));
  }

  /**
   * Sets the string of the winnerDisplay
   *
   * @param winnerDisplayerString - string image path from root
   */
  public void setWinnerDisplayerString(String winnerDisplayerString) {
    this.winnerDisplayerString = winnerDisplayerString;
  }

  /**
   * Creates a GUI element of the winner display
   *
   * @param winningPlayerColour - takes which player has won
   * @return JLabel of the winning player image
   */
  public JLabel winningPlayerDisplay(String winningPlayerColour) {
    String winnerImage = winningPlayerColour;
    Image image = null;
    if (winningPlayerColour != null) {
      try {
        image = ImageIO.read(Objects.requireNonNull(getClass().getResource(winnerImage)));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    JLabel winLabel = new JLabel();
    winLabel.setLocation(150, 260);
    winLabel.setSize(500, 200);
    winLabel.setOpaque(false);
    winLabel.setIcon(
        new ImageIcon(
            new ImageIcon(image).getImage().getScaledInstance(500, 200, Image.SCALE_SMOOTH)));
    return winLabel;
  }

  /**
   * Updates the display with which player is currently playing
   *
   */
  public void updatePlayerTurnDisplay(String newDisplay) {
    //this.winningPlayerDisplay("white");
    this.validate();
    this.repaint();
  }

  /**
   * Attach a single MillObserver to a set of Intersections by specifying their keys.
   *
   * @param millObserver The MillObserver being attached, which watches for Mills being formed.
   * @param intersectionKeys The keys of the Intersections to which the MillObservers are to be
   *     attached.
   */
  public void attachMillObserverByKey(MillObserver millObserver, String[] intersectionKeys) {
    for (String intersectionKey : intersectionKeys) {
      Intersection current = intersectionMap.get(intersectionKey);
      current.attachObserver(millObserver);
    }
  }

  /** Sets all Intersections as closed, disallowing Players from selecting them for any Action. */
  public void setIntersectionsAsClosed() {
    for (String key : intersectionMap.keySet()) {
      Intersection current = intersectionMap.get(key);
      current.setIllegalMoveState();
    }
  }

  /** Reset any visual highlights of Intersections. */
  public void unhighlightAllIntersections() {
    for (String key : intersectionMap.keySet()) {
      Intersection current = intersectionMap.get(key);
      if(!current.getMillState()) {
        current.resetIntersectionImage();
      }
      else{
        current.highlightMill();
      }
    }
  }

  /**
   * Sets the legal Intersections to travel to on the GameBoard, given the Intersection that has
   * just been selected for an Action.
   *
   * @param selectedIntersection The Intersection selected for an Action.
   * @return Always returns True
   */
  @SuppressWarnings("checkstyle:LocalVariableName")
  public boolean setLegalIntersections(
      Intersection selectedIntersection, boolean updateIntersection) {
    int[] selectedCoordinates = selectedIntersection.getCoordinates();
    int xShift = selectedCoordinates[2];
    int yShift = selectedCoordinates[3];

    String leftKey = String.valueOf(selectedCoordinates[0] - xShift) + selectedCoordinates[1];
    String rightKey = String.valueOf(selectedCoordinates[0] + xShift) + selectedCoordinates[1];
    String downKey = String.valueOf(selectedCoordinates[0]) + (selectedCoordinates[1] - yShift);
    String upKey = String.valueOf(selectedCoordinates[0]) + (selectedCoordinates[1] + yShift);

    String[] keyList = {leftKey, rightKey, downKey, upKey};

    boolean hasMoveableIntersection = false;

    for (String currentKey : keyList) {
      if (intersectionMap.containsKey(currentKey)) {
        Intersection currentIntersection = intersectionMap.get(currentKey);
        if (currentIntersection.isEmpty()) {
          if (updateIntersection) {
            currentIntersection.setLegalMoveState();
          }
          hasMoveableIntersection = true;
        }
      }
    }

    return hasMoveableIntersection;
  }

  /**
   * Set the legal Intersections to jump to on the GameBoard.
   *
   * @return True if there are any Intersections to jump to, false if not.
   */
  public boolean setLegalJumpIntersections() {
    boolean isMoveable = false;
    for (String key : intersectionMap.keySet()) {
      Intersection current = intersectionMap.get(key);
      if (current.isEmpty()) {
        isMoveable = true;
        current.setLegalMoveState();
      }
    }
    return isMoveable;
  }

  /** Highlights all the Intersections that are currently open to move to. */
  public void highlightOpenIntersections() {
    for (String key : intersectionMap.keySet()) {
      Intersection current = intersectionMap.get(key);
      if (current.isLegalMove() && current.isEmpty()) {
        current.highlightAsOpen();
      }
    }
  }

  /** Highlight all the Intersections that are currently part of a mill. */
  public void setMillStates() {
    for (String key : intersectionMap.keySet()) {
      Intersection current = intersectionMap.get(key);
      current.setMillState();
      if (current.getMillState()) {
        current.highlightMill();
      }
    }
  }

  /**
   * Get the number of Intersections holding Tokens of a given TokenType that are in a mill.
   *
   * @param tokenType The TokenType for which Intersections with mills are being counted.
   * @return The number of Intersections in mills of a given TokenType.
   */
  public int getMillIntersectionCount(TokenType tokenType) {
    int millIntersectionCount = 0;
    for (String key : intersectionMap.keySet()) {
      Intersection current = intersectionMap.get(key);
      if (current.getMillState() && current.peekToken().getTokenType() == tokenType) {
        millIntersectionCount += 1;
      }
    }
    return millIntersectionCount;
  }

  /**
   * Ends the game when a winner has been reached
   */
  public void killGame() {
    for (String key : this.intersectionMap.keySet()) {
      Intersection current = this.intersectionMap.get(key);
      current.removeToken();
    }
  }

  /**
   * Checks if the token type has any legal moves left
   *
   * @param playerTokenType - Enum TokenType
   *
   * @return boolean whether the player has moves left or not
   */
  public boolean hasAnyLegalMoves(TokenType playerTokenType) {
    boolean hasLegalMoves = false;
    for (String key : intersectionMap.keySet()) {
      Intersection current = intersectionMap.get(key);
      if (current.peekToken() != null) {
        if (current.peekToken().getTokenType() == playerTokenType
            && this.setLegalIntersections(current, false)) {
          hasLegalMoves = true;
        }
      }
    }
    return hasLegalMoves;
  }
}
