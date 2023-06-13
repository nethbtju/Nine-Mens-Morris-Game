package tutorials;

import gameengine.Game;
import gameengine.GameBoardGui;
import gameengine.Intersection;
import gameplayers.Player;
import java.util.HashMap;
import java.util.Queue;
import tokens.Token;
import tokens.TokenType;

/** A playable tutorial to teach players game mechanics. */
public abstract class TutorialState {

  private final Game currentGame;
  private final GameBoardGui currentGameBoard;
  private final String backgroundImage;

  /**
   * Constructor for a TutorialState.
   *
   * @param currentGame The Game in which the Tutorial exists.
   * @param currentGameBoard The GameBoard which is being operated on in the Tutorial.
   * @param backgroundImagePath The image path of a background image, which is used to display a
   *     preset GameBoard state without the need for actual objects.
   */
  public TutorialState(
      Game currentGame, GameBoardGui currentGameBoard, String backgroundImagePath) {
    this.currentGame = currentGame;
    this.currentGameBoard = currentGameBoard;
    this.backgroundImage = backgroundImagePath;
  }

  /** Perform all tasks required to set up the game state for a concrete TutorialState. */
  public void execute() {
    this.currentGameBoard.updateBackgroundImage(this.backgroundImage);
    this.setIntersections();
    this.setTokens();
    this.setLegalIntersections();
    this.setActionQueue();
    this.setPlayerQueue();
    this.setCaption();
  }

  /** Create the required Intersections to be used by the TutorialState. */
  public abstract void setIntersections();

  /** Set the required Tokens onto the required Intersections for the given TutorialState. */
  public abstract void setTokens();

  /**
   * Sets Tokens on a given set of Intersections as defined by their coordinates.
   *
   * @param coordinates The coordinates of the Intersections on which to place Tokens.
   */
  public void setTokens(int[][] coordinates) {
    HashMap<String, Intersection> intersectionHashMap = this.currentGameBoard.getIntersectionMap();

    for (int[] coordinate : coordinates) {
      int playerType = coordinate[1];
      System.out.println(coordinate[0]);
      String intersectionKey = this.currentGameBoard.getIntersectionKey(coordinate[0]);
      System.out.println(intersectionKey);
      Intersection current = intersectionHashMap.get(intersectionKey);

      if (playerType == 0) {
        Token whiteToken =
            new Token(
                TokenType.WHITE,
                "/resources/META-INF/img/BoardImages/WhiteTokenPlain.png",
                "/resources/META-INF/img/BoardImages/WhiteTokenSelected.png",
                "/resources/META-INF/img/BoardImages/WhiteTokenIllegal.png",
                "/resources/META-INF/img/BoardImages/WhiteTokenMill.png");
        current.setToken(whiteToken);
      } else {
        Token blackToken =
            new Token(
                TokenType.BLACK,
                "/resources/META-INF/img/BoardImages/BlackTokenPlain.png",
                "/resources/META-INF/img/BoardImages/BlackTokenSelected.png",
                "/resources/META-INF/img/BoardImages/BlackTokenIllegal.png",
                "/resources/META-INF/img/BoardImages/BlackTokenMill.png");
        current.setToken(blackToken);
      }
    }
  }

  /** Set the Intersections which a Token may move to in a given TutorialState. */
  public abstract void setLegalIntersections();

  /** Enqueue the Actions required for a given Tutorial. */
  public abstract void setActionQueue();

  /** Set the Player order for a given Tutorial. */
  public abstract void setPlayerQueue();

  /** Set a caption for the Tutorial to inform the Player of what they are being taught. */
  public abstract void setCaption();

  /**
   * Getter method for the Game the TutorialState plays.
   *
   * @return The Game being played.
   */
  public Game getCurrentGame() {
    return this.currentGame;
  }

  /**
   * Getter method for the GameBoard the TutorialState plays on.
   *
   * @return The GameBoard being played on.
   */
  public GameBoardGui getCurrentGameBoard() {
    return this.currentGameBoard;
  }

  /**
   * Set an Intersection as legally accessible in this Tutorial. This is necessary as Tutorials do
   * not instantiate every Intersection in a GameBoard found in a normal game, but only the ones
   * that will be used for this game.
   *
   * @param indexes The indexes in the GameBoard COORDINATES list of the Intersections to be set as
   *     accessible. Refer to the 9mm_coordinates.png in rough_work for a visual guide.
   */
  public void setAsTutorialLegal(int[] indexes) {
    HashMap<String, Intersection> intersectionHashMap = this.currentGameBoard.getIntersectionMap();
    for (int index : indexes) {
      String intersectionKey = this.currentGameBoard.getIntersectionKey(index);
      Intersection current = intersectionHashMap.get(intersectionKey);
      current.unlockTutorialState();
    }
  }

  /** Set all existing Intersections as legally accessible in this Tutorial. */
  public void setAllAsLegal() {
    HashMap<String, Intersection> intersectionHashMap = this.currentGameBoard.getIntersectionMap();
    for (String key : intersectionHashMap.keySet()) {
      Intersection current = intersectionHashMap.get(key);
      current.unlockTutorialState();
    }
  }

  /**
   * Set all existing Intersections as both legally accessible and open for immediate Action in this
   * Tutorial.
   */
  public void setAllAsOpen() {
    HashMap<String, Intersection> intersectionHashMap = this.currentGameBoard.getIntersectionMap();
    for (String key : intersectionHashMap.keySet()) {
      Intersection current = intersectionHashMap.get(key);
      current.unlockTutorialState();
      current.highlightAsOpen();
    }
  }

  /** Highlight a set of accessible Intersections for a Tutorial. */
  public void highLightIntersection(int[] indexes) {
    HashMap<String, Intersection> intersectionHashMap = this.currentGameBoard.getIntersectionMap();
    for (int i = 0; i < indexes.length; i++) {
      String intersectionKey = this.currentGameBoard.getIntersectionKey(indexes[i]);
      Intersection current = intersectionHashMap.get(intersectionKey);
      if (!current.isEmpty()) {
        current.highlightSelectedTokenLegal();
      } else {
        current.highlightAsOpen();
      }
    }
  }

  /**
   * Sets the PlayerQueue up for the tutorial to determine who goes first.
   *
   * @param isWhiteFirst Whether Player1 with white Tokens will play first.
   * @param whiteTokenCount The number of Tokens player 1 (white) has on the board, both in terms of
   *     real Tokens and any that are illustrated in the background image.
   * @param blackTokenCount The number of Tokens player 2 (black) has on the board, both in terms of
   *     real Tokens and any that are illustrated in the background image.
   */
  public void updatePlayerQueue(boolean isWhiteFirst, int whiteTokenCount, int blackTokenCount) {
    Queue<Player> players = this.currentGame.getPlayerQueue();
    Player player1 = players.remove();
    Player player2 = players.remove();

    if (player2.getTokenType() == TokenType.WHITE) {
      Player temp = player1;
      player1 = player2;
      player2 = temp;
    }

    player1.setTutorialState();
    player1.setTokenCount(whiteTokenCount);
    player2.setTutorialState();
    player2.setTokenCount(blackTokenCount);

    if (isWhiteFirst) {
      this.currentGame.updatePlayerQueue(player1, player2);
    } else {
      this.currentGame.updatePlayerQueue(player2, player1);
    }
  }
}
