package gameengine;

import actions.Action;
import actions.MoveTokenAction;
import actions.PlaceTokenAction;
import actions.SelectTokenAction;
import gameplayers.Capable;
import gameplayers.Player;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import tokens.TokenBank;
import tokens.TokenType;

/** An instance of a Nine Man's Morris game. Highest level class in the game architecture. */
public class Game {

  private final GameBoardGui gameBoard;
  private final Queue<Player> playerQueue = new LinkedList<>();
  private final Queue<Action> actionQueue = new LinkedList<>();

  /**
   * Constructor for the Game, initialises game backend.
   *
   * @throws IOException Excepts if the GameBoard fails to initialise due to a missing background
   *     image.
   */
  public Game() throws IOException {
    gameBoard = new GameBoardGui(this);
    gameBoard.createGui();
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

    if (this.actionQueue.isEmpty()) {
      this.pushPlayerActions(currentPlayer);
    }

    Action action = this.actionQueue.peek();
    boolean actionValid = action.isValid(selectedIntersection, currentPlayer);
    if (actionValid) {
      this.gameBoard.unhighlightAllIntersections();
      action.execute(selectedIntersection, currentPlayer);
      this.actionQueue.remove();
    }

    if (this.actionQueue.isEmpty()) {
      this.playerQueue.add(this.playerQueue.remove());
    }

    this.updatePlayTurnDisplay();
  }

  /**
   * Translates the Player's Capability into the Actions that they can take on a given turn.
   *
   * @param currentPlayer The Player currently taking their turn.
   */
  private void pushPlayerActions(Player currentPlayer) {
    Capable currentCapability = currentPlayer.getCurrentCapability();

    if (currentCapability == Capable.PLACEABLE) {
      currentPlayer.incrementTokenCount();
      this.actionQueue.add(new PlaceTokenAction());
    } else if (currentCapability == Capable.MOVEABLE) {
      this.gameBoard.setIntersectionsAsClosed();
      this.actionQueue.add(new SelectTokenAction());
      this.actionQueue.add(new MoveTokenAction());
    }
  }

  /** Enqueue two players to the game. */
  private void initialisePlayers() {
    Player player1 =
        new Player(
            TokenType.WHITE,
            new TokenBank(
                TokenType.WHITE,
                "img/BoardImages/WhiteTokenPlain.png",
                "img/BoardImages/WhiteTokenSelected.png"));
    Player player2 =
        new Player(
            TokenType.BLACK,
            new TokenBank(
                TokenType.BLACK,
                "img/BoardImages/BlackTokenPlain.png",
                "img/BoardImages/BlackTokenSelected.png"));
    this.playerQueue.add(player1);
    this.playerQueue.add(player2);
  }

  /** Prints the Player whose turn it is. */
  public void updatePlayTurnDisplay() {
    Player currentPlayer = playerQueue.peek();
    TokenType playerType = currentPlayer.getTokenType();

    if (playerType == TokenType.WHITE) {
      // System.out.println("Player 1");
      this.gameBoard.updatePlayerTurnDisplay("Player 1 Turn!");
    } else {
      // System.out.println("Player 2");
      this.gameBoard.updatePlayerTurnDisplay("Player 2 Turn!");
    }
  }
}
