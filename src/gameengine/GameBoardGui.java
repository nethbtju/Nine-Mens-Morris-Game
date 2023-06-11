package gameengine;

import java.awt.*;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Queue;
import javax.imageio.ImageIO;
import javax.swing.*;

import actions.Action;
import actions.PlaceTokenAction;
import actions.SelectTokenAction;
import gameplayers.GameState;
import gameplayers.Player;
import jdk.swing.interop.SwingInterOpUtils;
import tokens.Token;
import tokens.TokenType;
import actions.Action;

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

  private final int[][] COORDINATES = {
    {0, 0, 3, 3,0}, {0, 3, 1, 3,1}, {0, 6, 3, 3,2}, {1, 1, 2, 2,3}, {1, 3, 1, 2,4}, {1, 5, 2, 2,5},
    {2, 2, 1, 1,6}, {2, 3, 1, 1,7}, {2, 4, 1, 1,8}, {3, 0, 3, 1,9}, {6, 0, 3, 3,10}, {4, 3, 1, 1,11},
    {5, 3, 1, 2,12}, {6, 3, 1, 3,13}, {3, 1, 2, 1,14}, {3, 2, 1, 1,15}, {4, 2, 1, 1,16}, {4, 4, 1, 1,17},
    {3, 4, 1, 1,18}, {3, 5, 2, 1,19}, {3, 6, 3, 1,20}, {6, 6, 3, 3,21}, {5, 5, 2, 2,22}, {5, 1, 2, 2,23}
  };
  // public void addIntialBlackCovers(int xBound, int yBound, int width, int height)
  // 5
  private final int[][] blackCovers = {{125,278,68,180}, {127,279,60,180}, {125,278,60,180}, {123,276,67,180}, {121,276,72,180}, {117,278,73,180}, {117,279,72,180}, {121,279,71,180}, {121,276,71,180}};
  private final int[][] whiteCovers = {{125,278,65,180}};


  HashMap<String, Intersection> intersectionMap = new HashMap<>();

  private String winnerDisplayerString = "";

  private Image blackTokenCover = ImageIO.read(getClass().getResource("/resources/META-INF/img/BoardImages/TokenCover0.png"));
  private Image whiteTokenCover = ImageIO.read(getClass().getResource("/resources/META-INF/img/BoardImages/TokenCover0.png"));

  JLabel blackCoverLabel = new JLabel();
  JLabel whiteCoverLabel = new JLabel();

  private boolean hasMoveHinting = false;

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

    int[] blackCover = blackCovers[0];
    this.addIntialBlackCovers(blackCover[0], blackCover[1], blackCover[2], blackCover[3]);
    this.addIntialWhiteCovers(125,278,65,180);

    this.setLayout(null);
  }

  public void addIntialBlackCovers(int xBound, int yBound, int width, int height){
    System.out.println("Adding Black Cover");

    blackCoverLabel.setBounds(xBound,yBound,width,height);
    blackCoverLabel.setSize(65, 180);

    blackCoverLabel.setOpaque(false);

    blackCoverLabel.setIcon(
            new ImageIcon(new ImageIcon(blackTokenCover).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH)));

    add(blackCoverLabel,1);
  }

  public void addIntialWhiteCovers(int xBound, int yBound, int width, int height){
    System.out.println("Adding white Cover");

    whiteCoverLabel.setBounds(615, 277, 65,180);
    whiteCoverLabel.setSize(65, 180);

    whiteCoverLabel.setOpaque(false);

    whiteCoverLabel.setIcon(
            new ImageIcon(
                    new ImageIcon(whiteTokenCover).getImage().getScaledInstance(63, 180, Image.SCALE_SMOOTH)));
    add(whiteCoverLabel,0);
  }

  public void updateCover(Player player) throws IOException {
    System.out.println("Updating cover");
    TokenType currentPlayer = player.getTokenType();
    int tokensLeft = player.getTokenBank().getTokensLeft();
    int coverName = 9 - tokensLeft;
    if (currentPlayer == TokenType.BLACK) {
      System.out.println(coverName);
      String newimage = "/resources/META-INF/img/BoardImages/TokenCover" + coverName + ".png";
      try {
        blackTokenCover = ImageIO.read(getClass().getResource(newimage));
      } catch(IOException e) {
        System.out.println("Could not black token fetch Image");
      }
      int[] cover = blackCovers[coverName];
      addIntialBlackCovers(cover[0],cover[1],cover[2],cover[3]);
    } else {
      String newimage = "/resources/META-INF/img/BoardImages/TokenCover" + coverName + ".png";
      try {
        whiteTokenCover = ImageIO.read(getClass().getResource(newimage));
      } catch(IOException e) {
        System.out.println("Could not white token fetch image");
      }
      this.addIntialWhiteCovers(615, 277, 65,180);
    }
  }

  public void addAllIntersections(){
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

  private void initialiseToggleHintButton(){

    JButton button = new JButton();
    button.addActionListener(e -> this.changeMoveHintSettings(button));

    button.setLocation(500, 40);
    button.setSize(200, 200);
    button.setOpaque(false);
    button.setContentAreaFilled(false);
    button.setBorderPainted(false);
    button.setFocusPainted(false);

    if (this.currentGame.getGameState() == GameState.NORMAL) {
      System.out.println("enable");
      this.disableMoveHinting();
      button.setText("Enable Hinting");
    }
    else if (this.currentGame.getGameState() == GameState.TUTORIAL){
      System.out.println("disable");
      this.enableMoveHinting();
      button.setText("Disable Hinting");
    }

    button.setBackground(Color.BLUE);
    add(button);
  }

  private void initialiseTutorialMode(){

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
    JLabel winLabel = new JLabel();
    winLabel.setLocation(150, 260);
    winLabel.setSize(500, 200);
    winLabel.setOpaque(false);
    winLabel.setIcon(
        new ImageIcon(
            new ImageIcon(winnerImage).getImage().getScaledInstance(500, 200, Image.SCALE_SMOOTH)));
    return winLabel;
  }

  /**
   * Updates the display with which player is currently playing
   *
   */
  public void updatePlayerTurnDisplay(String newDisplay) {
    this.winningPlayerDisplay("white");
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
    if(this.hasMoveHinting) {
      for (String key : intersectionMap.keySet()) {
        Intersection current = intersectionMap.get(key);
        if ((current.isLegalMove() || !current.isTutorialLockedState() ) && current.isEmpty()) {
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

  public void highlightRemoveTokens(TokenType attackedTokenType, boolean isMillRemove) {
    if (this.hasMoveHinting) {
      for (String key : intersectionMap.keySet()) {
        Intersection current = intersectionMap.get(key);
        if (current.peekToken() != null) {
          if (current.peekToken().getTokenType() == attackedTokenType && (!current.getMillState() || isMillRemove)) {
            current.highlightSelectedTokenLegal();
          }
        }
      }
    }
  }

  public void changeMoveHintSettings(JButton button){
    System.out.println("settings have been changed");
    this.hasMoveHinting = !this.hasMoveHinting;
    if(this.hasMoveHinting){
      button.setText("Disable Hinting");
    }
    else{
      button.setText("Enable Hinting");
    }
  }

  public void enableMoveHinting(){
    this.hasMoveHinting = true;
  }

  public void disableMoveHinting(){
    this.hasMoveHinting = false;
  }

  public void test(Player currentplayer){
    this.killGame();

    Intersection current = this.intersectionMap.get("00");
    current.setToken(new Token(TokenType.BLACK,
            "/resources/META-INF/img/BoardImages/BlackTokenPlain.png",
            "/resources/META-INF/img/BoardImages/BlackTokenSelected.png",
            "/resources/META-INF/img/BoardImages/BlackTokenIllegal.png",
            "/resources/META-INF/img/BoardImages/BlackTokenMill.png"));

    Intersection current2 = this.intersectionMap.get("03");
    current2.setToken(new Token(TokenType.BLACK,
            "/resources/META-INF/img/BoardImages/BlackTokenPlain.png",
            "/resources/META-INF/img/BoardImages/BlackTokenSelected.png",
            "/resources/META-INF/img/BoardImages/BlackTokenIllegal.png",
            "/resources/META-INF/img/BoardImages/BlackTokenMill.png"));

    Intersection current3 = this.intersectionMap.get("06");
    current3.setToken(new Token(TokenType.BLACK,
            "/resources/META-INF/img/BoardImages/BlackTokenPlain.png",
            "/resources/META-INF/img/BoardImages/BlackTokenSelected.png",
            "/resources/META-INF/img/BoardImages/BlackTokenIllegal.png",
            "/resources/META-INF/img/BoardImages/BlackTokenMill.png"));

    //this.currentGame.updateActionQueue();

  }

  //remove all tokens, set tokens specifically, set moveable intersections, set next player, set actions

  public void populateGameBoard() {

    this.updateBackgroundImage("/resources/META-INF/img/BoardImages/board600pxls.png");


    //empty gameboard, reinitialise players, pop players, set tokens at specific points, provide relevant action,
    // set desired place intersection, set player positions from previously popped players
    this.removeAllIntersections();
    int[] coords = {2,20,21};
    this.addNewIntersections(coords);
    this.currentGame.initialiseMillObservers();

    Action[] actions = {new SelectTokenAction(), new PlaceTokenAction()};
    this.currentGame.updateActionQueue(actions);

    //Queue<Player> players = this.currentGame.getPlayerQueue();
    //Player player1 = players.remove();
    //Player player2 = players.remove();

    //this.currentGame.updatePlayerQueue(player2, player1);

  }
  public void removeAllIntersections(){
    for (String key : this.intersectionMap.keySet()) {
      Intersection current = this.intersectionMap.get(key);
      super.remove(current);
      super.repaint();
    }
  }

  public void addNewIntersections(int[] indexes){
    for(int i = 0; i < indexes.length; i++) {
      int currentIndex = indexes[i];
      String intersectionKey = String.valueOf(COORDINATES[currentIndex][0]) + COORDINATES[currentIndex][1];
      System.out.println(intersectionKey);

      Intersection button = this.newButton(X[currentIndex], Y[currentIndex], COORDINATES[2]);
      this.intersectionMap.put(intersectionKey, button);
      add(button);
    }

    super.repaint();
  }

  public void updateBackgroundImage(String imagePath){

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

  public HashMap<String, Intersection> getIntersectionMap(){
    return this.intersectionMap;
  }

  public String getIntersectionKey(int index){
    return String.valueOf(COORDINATES[index][0]) + COORDINATES[index][1];
  }

  public void setAllAsTutorialLocked(){
    for (String key : this.intersectionMap.keySet()) {
      Intersection current = this.intersectionMap.get(key);
      current.lockTutorialState();
    }
  }

}
