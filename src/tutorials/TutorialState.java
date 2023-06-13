package tutorials;

import gameengine.Game;
import gameengine.GameBoardGui;
import gameengine.Intersection;
import gameplayers.Player;
import tokens.Token;
import tokens.TokenType;

import java.util.HashMap;
import java.util.Queue;

public abstract class TutorialState {

  private Game currentGame;
  private GameBoardGui currentGameBoard;

  private String backgroundImage;

  private Token whiteToken;

  private Token blackToken;

  public TutorialState(
      Game currentGame, GameBoardGui currentGameBoard, String backgroundImagePath) {
    this.currentGame = currentGame;
    this.currentGameBoard = currentGameBoard;
    this.backgroundImage = backgroundImagePath;
  }

  public void execute() {
    this.currentGameBoard.updateBackgroundImage(this.backgroundImage);
    this.setIntersections();
    this.setTokens();
    this.setLegalIntersections();
    this.setActionQueue();
    this.setPlayerQueue();
    this.setCaption();
  }

  public abstract void setIntersections();

  public abstract void setTokens();

  public abstract void setLegalIntersections();

  public abstract void setActionQueue();

  public abstract void setPlayerQueue();

  public abstract void setCaption();

  public void setTokens(int[][] coords) {
    HashMap<String, Intersection> intersectionHashMap = this.currentGameBoard.getIntersectionMap();

    for (int i = 0; i < coords.length; i++) {
      int playerType = coords[i][1];
      System.out.println(coords[i][0]);
      String intersectionKey = this.currentGameBoard.getIntersectionKey(coords[i][0]);
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

  public Game getCurrentGame() {
    return this.currentGame;
  }

  public GameBoardGui getCurrentGameBoard() {
    return this.currentGameBoard;
  }

  public void setAsTutorialLegal(int[] indexes) {
    HashMap<String, Intersection> intersectionHashMap = this.currentGameBoard.getIntersectionMap();
    for (int i = 0; i < indexes.length; i++) {
      String intersectionKey = this.currentGameBoard.getIntersectionKey(indexes[i]);
      Intersection current = intersectionHashMap.get(intersectionKey);
      current.unlockTutorialState();
    }
  }

  public void setAllAsLegal() {
    HashMap<String, Intersection> intersectionHashMap = this.currentGameBoard.getIntersectionMap();
    for (String key : intersectionHashMap.keySet()) {
      Intersection current = intersectionHashMap.get(key);
      current.unlockTutorialState();
      current.highlightAsOpen();
    }
  }

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
