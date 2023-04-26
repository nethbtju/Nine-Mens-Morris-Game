package gameengine;

import actions.Action;
import actions.MoveTokenAction;
import actions.PlaceTokenAction;
import gameplayers.Player;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import tokens.TokenBank;
import tokens.TokenType;

/** An instance of a Nine Man's Morris game. Highest level class in the game architecture. */
public class Game {

  GameBoardGui gameBoard;
  Queue<Player> playerQueue = new LinkedList<>();

  /**
   * Constructor for the Game, initialises game backend.
   *
   * @throws IOException Excepts if the GameBoard fails to initialise due to a missing background
   *     image.
   */
  public Game() throws IOException {
    gameBoard = new GameBoardGui(this);
    gameBoard.createGUI();
    this.initialisePlayers();
    this.updatePlayTurnDisplay();
  }

  /**
   * Play a turn for the current player.
   *
   * @param selectedIntersection The intersection selected by the user on the frontend.
   */
  public void playTurn(Intersection selectedIntersection) {
    Player currentPlayer = this.playerQueue.peek();

    Action currentAction = this.processPlayerAction(currentPlayer);
    boolean turnComplete = currentAction.execute(selectedIntersection, currentPlayer);
    if (turnComplete) {
      this.playerQueue.add(this.playerQueue.remove());
    }

    this.updatePlayTurnDisplay();
  }

  private Action processPlayerAction(Player currentPlayer) {
    String currentCapability = currentPlayer.getCurrentCapability();
    if (currentCapability == "PLACE_TOKEN") {
      currentPlayer.incrementTokenCount();
      return new PlaceTokenAction();
    } else if (currentCapability == "MOVE_TOKEN") {
      System.out.println("Player can move!");
      return new MoveTokenAction();
    }
    return new MoveTokenAction();
  }

  public GameBoardGui getGameBoard() {
    return this.gameBoard;
  }

  /** Enqueue two players to the game. */
  public void initialisePlayers() {
    Player player1 =
        new Player("White", new TokenBank(TokenType.WHITE, "img/BoardImages/WhiteTokenPlain.png"));
    Player player2 =
        new Player("Black", new TokenBank(TokenType.BLACK, "img/BoardImages/BlackTokenPlain.png"));
    this.playerQueue.add(player1);
    this.playerQueue.add(player2);
  }

  /** Prints the Player whose turn it is. */
  public void updatePlayTurnDisplay() {
    Player currentPlayer = playerQueue.peek();
    String playerType = currentPlayer.getTokenType();

    if (playerType == "White") {
      System.out.println("Player 1");
      this.gameBoard.updatePlayerTurnDisplay("Player 1 Turn!");
    } else {
      System.out.println("Player 2");
      this.gameBoard.updatePlayerTurnDisplay("Player 2 Turn!");
    }
  }
}
