package gameengine;

import java.awt.*;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;

import actions.Action;
import actions.PlaceTokenAction;
import actions.SelectTokenAction;
import gameplayers.GameState;
import gameplayers.Player;
import tokens.Token;
import tokens.TokenType;
import tutorials.TutorialManager;

/** Represents the GameBoard in which Intersections exist. */
public class GameBoardGui extends JPanel {

  private Image backgroundImage;

  private final int[] X = {
    208, 208, 208, 265, 265, 265, 320, 320, 320, 375, 542, 430, 487, 542, 375, 375, 430, 430, 375,
    375, 375, 542, 487, 487
  };
  private final int[] Y = {
    178, 343, 508, 234, 343, 452, 288, 343, 398, 178, 178, 343, 343, 343, 234, 288, 288, 398, 398,
    452, 508, 508, 452, 234
  };
  private final Game currentGame;

  private JLabel winLabel = new JLabel();

  private final int[][] COORDINATES = {
    {0, 0, 3, 3, 0},
    {0, 3, 1, 3, 1},
    {0, 6, 3, 3, 2},
    {1, 1, 2, 2, 3},
    {1, 3, 1, 2, 4},
    {1, 5, 2, 2, 5},
    {2, 2, 1, 1, 6},
    {2, 3, 1, 1, 7},
    {2, 4, 1, 1, 8},
    {3, 0, 3, 1, 9},
    {6, 0, 3, 3, 10},
    {4, 3, 1, 1, 11},
    {5, 3, 1, 2, 12},
    {6, 3, 1, 3, 13},
    {3, 1, 2, 1, 14},
    {3, 2, 1, 1, 15},
    {4, 2, 1, 1, 16},
    {4, 4, 1, 1, 17},
    {3, 4, 1, 1, 18},
    {3, 5, 2, 1, 19},
    {3, 6, 3, 1, 20},
    {6, 6, 3, 3, 21},
    {5, 5, 2, 2, 22},
    {5, 1, 2, 2, 23}
  };

  private final int[][] blackCovers = {
    {125, 278, 68, 180},
    {127, 279, 60, 180},
    {125, 278, 60, 180},
    {123, 276, 67, 180},
    {121, 276, 72, 180},
    {117, 278, 73, 180},
    {117, 279, 72, 180},
    {121, 279, 71, 180},
    {121, 276, 71, 180}
  };
  private final int[][] whiteCovers = {
    {615, 277, 65, 180},
    {615, 277, 65, 180},
    {612, 277, 65, 180},
    {611, 275, 67, 180},
    {609, 275, 70, 178},
    {609, 277, 68, 180},
    {609, 277, 65, 180},
    {609, 277, 65, 180},
    {609, 275, 65, 179}
  };

  HashMap<String, Intersection> intersectionMap = new HashMap<>();

  private String winnerDisplayerString = "";

  private Image blackTokenCover =
      ImageIO.read(getClass().getResource("/resources/META-INF/img/BoardImages/TokenCover0.png"));
  private Image whiteTokenCover =
      ImageIO.read(getClass().getResource("/resources/META-INF/img/BoardImages/TokenCover0.png"));

  JLabel blackCoverLabel = new JLabel();
  JLabel whiteCoverLabel = new JLabel();

  private Image toggleImage =
      ImageIO.read(getClass().getResource("/resources/META-INF/img/BoardImages/toggleOff.png"));

  private int totalTokens = 9;
  private boolean hasMoveHinting = false;

  private JLabel captionLabel;

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

    this.addAllIntersections();
    this.initialiseToggleHintButton();
    this.initialiseTutorialButtons();
    int[] blackCover = blackCovers[0];
    int[] whiteCover = whiteCovers[0];
    this.addIntialBlackCovers(blackCover[0], blackCover[1], blackCover[2], blackCover[3]);
    this.addIntialWhiteCovers(whiteCover[0], whiteCover[1], whiteCover[2], whiteCover[3]);
    this.initialiseCaption();

    this.setLayout(null);
  }

  /**
   * Set up the initial caption for the GUI to load when the game first launches and Swing
   * draws the interface
   */
  private void initialiseCaption() {
    JLabel label = new JLabel();
    label.setHorizontalAlignment(SwingConstants.CENTER);
    label.setVerticalAlignment(SwingConstants.CENTER);
    label.setText(
        "<html><div style='text-align: center;'>Place token on any open intersection</div></html>");
    label.setLocation(240, 587);
    label.setSize(320, 70);
    // label.setBackground(Color.BLUE);
    // label.setOpaque(true);
    label.setFont(new Font("BM Hanna 11yrs Old", Font.BOLD, 20));
    add(label);

    this.captionLabel = label;
  }

  /**
   * Changes the caption displayed on the GUI
   *
   * @param newText - string of new caption text
   */
  public void changeCaptionString(String newText) {
    this.captionLabel.setText(
        "<html><div style='text-align: center;'>" + newText + "</div></html>");
  }


  /**
   * Add the initial Jlabel of the black token covers on the GUI when it initialises
   *
   * @param xBound - x axis position
   * @param yBound - y axis position
   * @param width - width of the label
   * @param height - height of the label
   */
  public void addIntialBlackCovers(int xBound, int yBound, int width, int height) {
    System.out.println("Adding Black Cover");

    blackCoverLabel.setBounds(xBound, yBound, width, height);
    blackCoverLabel.setSize(65, 180);

    blackCoverLabel.setOpaque(false);

    blackCoverLabel.setIcon(
        new ImageIcon(
            new ImageIcon(blackTokenCover)
                .getImage()
                .getScaledInstance(width, height, Image.SCALE_SMOOTH)));

    add(blackCoverLabel, 1);
  }

  /**
   * Add the initial Jlabel of the white token covers on the GUI when it initialises
   *
   * @param xBound - x axis position
   * @param yBound - y axis position
   * @param width - width of the label
   * @param height - height of the label
   */
  public void addIntialWhiteCovers(int xBound, int yBound, int width, int height) {
    System.out.println("Adding white Cover");

    whiteCoverLabel.setBounds(xBound, yBound, width, height);
    whiteCoverLabel.setSize(65, 180);

    whiteCoverLabel.setOpaque(false);

    whiteCoverLabel.setIcon(
        new ImageIcon(
            new ImageIcon(whiteTokenCover)
                .getImage()
                .getScaledInstance(width, height, Image.SCALE_SMOOTH)));
    add(whiteCoverLabel, 0);
  }

  /**
   * When a new token is placed the cover image path gets replaced with another to
   * get rid of another token
   *
   * @param player - The player whos token was just placed
   *
   * @throws IOException - If the image path could not be found
   */
  public void updateCover(Player player) throws IOException {
    System.out.println("Updating cover");
    TokenType currentPlayer = player.getTokenType();
    int tokensLeft = player.getTokenBank().getTokensLeft();
    System.out.println("Tokens left:" + tokensLeft);
    int coverName = totalTokens - tokensLeft;
    System.out.println("cover name");
    System.out.println(coverName);
    System.out.println("Cover Name:" + coverName);
    if (currentPlayer == TokenType.BLACK) {
      System.out.println(coverName);
      String newimage = "/resources/META-INF/img/BoardImages/TokenCover" + coverName + ".png";
      try {
        blackTokenCover = ImageIO.read(getClass().getResource(newimage));
      } catch (IOException e) {
        System.out.println("Could not black token fetch Image");
      }
      int[] cover = blackCovers[coverName];
      addIntialBlackCovers(cover[0], cover[1], cover[2], cover[3]);
    } else {
      String newimage = "/resources/META-INF/img/BoardImages/TokenCover" + coverName + ".png";
      try {
        whiteTokenCover = ImageIO.read(getClass().getResource(newimage));
      } catch (IOException e) {
        System.out.println("Could not white token fetch image");
      }
      int[] cover = whiteCovers[coverName];
      System.out.println(cover[0]);
      this.addIntialWhiteCovers(cover[0], cover[1], cover[2], cover[3]);
    }
  }

  /**
   * Adds all the intersections to the game map
   */
  public void addAllIntersections() {
    for (int i = 0; i < X.length; i++) {

      String intersectionKey = String.valueOf(COORDINATES[i][0]) + COORDINATES[i][1];

      Intersection button = this.newButton(X[i], Y[i], COORDINATES[i]);
      this.intersectionMap.put(intersectionKey, button);
      add(button);
    }
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
   * Sets the toggle image from enable/disable when the user clicks it
   * @param toggleImage
   */
  public void setToggleImage(Image toggleImage) {
    this.toggleImage = toggleImage;
  }

  /**
   * Initialise the toggle buttong the GUI launches
   */
  private void initialiseToggleHintButton() {

    JButton button = new JButton();
    button.addActionListener(e -> this.changeMoveHintSettings(button));

    button.setLocation(580, 110);
    button.setSize(90, 40);
    button.setOpaque(false);
    button.setContentAreaFilled(false);
    button.setBorderPainted(false);
    button.setFocusPainted(false);

    if (this.currentGame.getGameState() == GameState.NORMAL) {
      System.out.println("enable");
      this.disableMoveHinting();
      try {
        setToggleImage(
            ImageIO.read(
                getClass().getResource("/resources/META-INF/img/BoardImages/toggleOff.png")));
      } catch (IOException e) {
        e.printStackTrace();
      }
      button.setIcon(
          new ImageIcon(
              new ImageIcon(toggleImage).getImage().getScaledInstance(90, 40, Image.SCALE_SMOOTH)));
      button.setBackground(Color.BLUE);
      add(button);
    } else if (this.currentGame.getGameState() == GameState.TUTORIAL) {
      try {
        setToggleImage(
            ImageIO.read(
                getClass().getResource("/resources/META-INF/img/BoardImages/toggleOn.png")));
      } catch (IOException e) {
        e.printStackTrace();
      }
      button.setIcon(
          new ImageIcon(
              new ImageIcon(toggleImage).getImage().getScaledInstance(90, 40, Image.SCALE_SMOOTH)));
      System.out.println("disable");
      this.enableMoveHinting();
    }
  }

  /**
   * Adds the image path to the winner display and shows it on the GUI
   *
   * @param image - string of the image path from root
   */
  public void showWinnerDisplay(String image) {
    setWinnerDisplayerString(image);
    remove(blackCoverLabel);
    remove(whiteCoverLabel);
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
    Image winnerImage = null;
    try {
      winnerImage = ImageIO.read(
                      getClass().getResource(winningPlayerColour));
    } catch (IOException e) {
      e.printStackTrace();
    }
    winLabel.setLocation(150, 260);
    winLabel.setSize(500, 200);
    winLabel.setOpaque(false);
    winLabel.setIcon(
        new ImageIcon(
            new ImageIcon(winnerImage).getImage().getScaledInstance(500, 200, Image.SCALE_SMOOTH)));
    return winLabel;
  }

  /** Updates the display with which player is currently playing */
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
      if (!current.getMillState()) {
        current.resetIntersectionImage();
      } else {
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
    System.out.println("legaltoken");
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
            System.out.println("legaltoken");
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
    if (this.hasMoveHinting) {
      for (String key : intersectionMap.keySet()) {
        Intersection current = intersectionMap.get(key);
        if ((current.isLegalMove() || !current.isTutorialLockedState()) && current.isEmpty()) {
          current.highlightAsOpen();
        }
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

  /** Ends the game when a winner has been reached */
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

  /**
   * Remove highlighted tokens off the GUI board
   *
   * @param attackedTokenType - the type of token that was attacked
   * @param isMillRemove - whether it is in a mill to be removed
   */
  public void highlightRemoveTokens(TokenType attackedTokenType, boolean isMillRemove) {
    if (this.hasMoveHinting) {
      for (String key : intersectionMap.keySet()) {
        Intersection current = intersectionMap.get(key);
        if (current.peekToken() != null) {
          if (current.peekToken().getTokenType() == attackedTokenType
              && (!current.getMillState() || isMillRemove)) {
            current.highlightSelectedTokenLegal();
          }
        }
      }
    }
  }

  /**
   * When the user chooses to, they can turn off hinting by toggling on or
   * off the button
   *
   * @param button - toggle hinting button
   */
  public void changeMoveHintSettings(JButton button) {
    System.out.println("settings have been changed");
    this.hasMoveHinting = !this.hasMoveHinting;
    if (this.hasMoveHinting) {
      try {
        toggleImage =
            ImageIO.read(
                getClass().getResource("/resources/META-INF/img/BoardImages/toggleOn.png"));
      } catch (IOException e) {
        e.printStackTrace();
      }
      button.setIcon(
          new ImageIcon(
              new ImageIcon(toggleImage).getImage().getScaledInstance(90, 40, Image.SCALE_SMOOTH)));
    } else {
      try {
        toggleImage =
            ImageIO.read(
                getClass().getResource("/resources/META-INF/img/BoardImages/toggleOff.png"));
      } catch (IOException e) {
        e.printStackTrace();
      }
      button.setIcon(
          new ImageIcon(
              new ImageIcon(toggleImage).getImage().getScaledInstance(90, 40, Image.SCALE_SMOOTH)));
    }
  }

  /**
   * Enable hinting
   */
  public void enableMoveHinting() {
    this.hasMoveHinting = true;
  }

  /**
   * disable hinting
   */
  public void disableMoveHinting() {
    this.hasMoveHinting = false;
  }



  /**
   * Removes all intersections off the map
   */
  public void removeAllIntersections() {
    for (String key : this.intersectionMap.keySet()) {
      Intersection current = this.intersectionMap.get(key);
      super.remove(current);
      super.repaint();
    }
  }

  /**
   * Adds a specific set of Intersections to the GameBoard.
   *
   * @param indexes The indexes of the Intersections to be added, where each is an index of
   *     COORDINATES.
   */
  public void addNewIntersections(int[] indexes) {
    for (int currentIndex : indexes) {
      String intersectionKey =
          String.valueOf(COORDINATES[currentIndex][0]) + COORDINATES[currentIndex][1];
      System.out.println("Adding intersection at:" + intersectionKey);

      Intersection button =
          this.newButton(X[currentIndex], Y[currentIndex], COORDINATES[currentIndex]);
      this.intersectionMap.put(intersectionKey, button);
      add(button);
    }

    super.repaint();
  }

  /**
   * When a new tutorial is toggled, the background image can be updated to show a new game state
   *
   * @param imagePath - The new image path
   */
  public void updateBackgroundImage(String imagePath) {

    Image image = null;
    if (imagePath != null) {
      try {
        image = ImageIO.read(Objects.requireNonNull(getClass().getResource(imagePath)));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    this.backgroundImage = image;
    super.repaint();
  }

  /**
   * Getter for the intersection map
   *
   * @return hashmap of all the intersections
   */
  public HashMap<String, Intersection> getIntersectionMap() {
    return this.intersectionMap;
  }

  /**
   * Get the intersection keys
   *
   * @param index - the index of the key needed
   * @return key of the coordinates
   */
  public String getIntersectionKey(int index) {
    return String.valueOf(COORDINATES[index][0]) + COORDINATES[index][1];
  }


  /**
   * Lock all the intersections of the tutorial
   *
   */
  public void setAllAsTutorialLocked() {
    for (String key : this.intersectionMap.keySet()) {
      Intersection current = this.intersectionMap.get(key);
      current.lockTutorialState();
    }
  }

  /**
   * set up the tutorial toggle next and prev buttons on the gameboard GUI
   */
  public void initialiseTutorialButtons() {
    if (this.currentGame.getGameState() == GameState.TUTORIAL) {
      TutorialManager currentManager = this.currentGame.getTutorialManager();
      JButton button = new JButton();

      button.setLocation(150, -40);
      button.setSize(200, 200);
      button.setOpaque(false);
      button.setContentAreaFilled(false);
      button.setBorderPainted(false);
      button.setFocusPainted(false);

      Image tutPrev = null;
      try {
        tutPrev =
            ImageIO.read(
                getClass().getResource("/resources/META-INF/img/BoardImages/tutorialPrev.png"));
      } catch (IOException e) {
        e.printStackTrace();
      }
      button.setIcon(
          new ImageIcon(
              new ImageIcon(tutPrev).getImage().getScaledInstance(160, 38, Image.SCALE_SMOOTH)));
      button.setBackground(Color.BLUE);
      add(button);

      JButton button2 = new JButton();

      button2.setLocation(400, -40);
      button2.setSize(200, 200);
      button2.setOpaque(false);
      button2.setContentAreaFilled(false);
      button2.setBorderPainted(false);
      button2.setFocusPainted(false);

      Image tutNext = null;
      try {
        tutNext =
            ImageIO.read(
                getClass().getResource("/resources/META-INF/img/BoardImages/tutorialNext.png"));
      } catch (IOException e) {
        e.printStackTrace();
      }
      button2.setIcon(
          new ImageIcon(
              new ImageIcon(tutNext).getImage().getScaledInstance(160, 40, Image.SCALE_SMOOTH)));
      button2.setBackground(Color.BLUE);
      add(button2);

      button.addActionListener(e -> this.executePreviousTutorial(button2));
      button2.addActionListener(e -> this.executeNextTutorial(button2, e));
    }
  }

  /**
   * Shift the tutorials to the previous tutorial state
   *
   * @param currentButton - the prev button
   */
  public void executePreviousTutorial(JButton currentButton) {
    TutorialManager currentManager = this.currentGame.getTutorialManager();
    boolean isPastEnd = currentManager.isPastEnd;

    if (isPastEnd) {
      Image tutNext = null;
      try {
        tutNext =
            ImageIO.read(
                getClass().getResource("/resources/META-INF/img/BoardImages/tutorialNext.png"));
      } catch (IOException e) {
        e.printStackTrace();
      }
      currentButton.setIcon(
          new ImageIcon(
              new ImageIcon(tutNext).getImage().getScaledInstance(160, 40, Image.SCALE_SMOOTH)));
      currentButton.setBackground(Color.BLUE);
      System.out.println("ADGADGADGADG");
    }

    currentManager.executePrevious();
  }

  /**
   * Shift the game state to the next tutorial
   *
   * @param currentButton - clicked button
   * @param e - action that needs to be executed
   */
  public void executeNextTutorial(JButton currentButton, ActionEvent e) {
    TutorialManager currentManager = this.currentGame.getTutorialManager();
    boolean isAtEnd = currentManager.isAtEnd();
    boolean isPastEnd = currentManager.isPastEnd;

    if (isPastEnd) {
      System.out.println("past end");

      JComponent comp = (JComponent) e.getSource();
      Window win = SwingUtilities.getWindowAncestor(comp);
      win.dispose();

      try {
        LaunchScreenGui game = new LaunchScreenGui();
        game.createGui();
      } catch (IOException e2) {
        e2.printStackTrace();
      }
    }

    if (isAtEnd) {
      // currentButton.setText("Return to Home screen");
      Image homeScreen = null;
      try {
        homeScreen =
            ImageIO.read(
                getClass().getResource("/resources/META-INF/img/BoardImages/mainScreen.png"));
      } catch (IOException e2) {
        e2.printStackTrace();
      }
      currentButton.setIcon(
          new ImageIcon(
              new ImageIcon(homeScreen).getImage().getScaledInstance(160, 40, Image.SCALE_SMOOTH)));
      currentButton.setBackground(Color.BLUE);
      System.out.println("at end");
    }

    currentManager.executeNext();
  }
}
