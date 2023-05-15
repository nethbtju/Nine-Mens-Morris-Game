package gameengine;

import actions.Action;
import actions.MoveTokenAction;
import actions.PlaceTokenAction;
import actions.SelectTokenAction;
import gameplayers.Capable;
import gameplayers.Player;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import tokens.TokenBank;
import tokens.TokenType;

/** An instance of a Nine Man's Morris game. Highest level class in the game architecture. */
public class Game {

  private final GameBoardGui gameBoard;
  private final Queue<Player> playerQueue = new LinkedList<>();
  private final Queue<Action> actionQueue = new LinkedList<>();
  private final ArrayList<MillObserver> millObservers = new ArrayList<>();

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
    this.initialiseMillObservers();
    this.updatePlayTurnDisplay();
  }

  /**
   * Play a turn for the current player.
   *
   * @param selectedIntersection The intersection selected by the user on the frontend.
   */
  public void playTurn(Intersection selectedIntersection) {
    Player currentPlayer = this.playerQueue.peek();
    int originalMillCount = this.getNumberOfMills(currentPlayer);

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

    if (this.getNumberOfMills(currentPlayer) > originalMillCount) {
      System.out.println("A new mill was formed!");
    }

    if (this.actionQueue.isEmpty()) {
      this.playerQueue.add(this.playerQueue.remove());
    }

    this.updatePlayTurnDisplay();
  }

  /**
   * Get a count of the mills currently controlled by the Player.
   *
   * @param currentPlayer The Player currently playing their turn.
   * @return The number of Mills currently owned by the Player.
   */
  private int getNumberOfMills(Player currentPlayer) {
    int numberOfMills = 0;
    for (MillObserver millObserver : this.millObservers) {
      if (millObserver.checkForMill(currentPlayer.getTokenType())) {
        numberOfMills++;
      }
    }
    return numberOfMills;
  }

  /** Initialise the MillObservers, which detect Mill formation. */
  private void initialiseMillObservers() {
    // Hardcoded while searching for a better representation

    String[] OUTER_LEFT_COLUMN = {"00", "03", "06"};
    String[] OUTER_RIGHT_COLUMN = {"60", "63", "66"};
    String[] OUTER_BOTTOM_ROW = {"00", "30", "60"};
    String[] OUTER_TOP_ROW = {"06", "36", "66"};

    String[] MIDDLE_LEFT_COLUMN = {"11", "13", "15"};
    String[] MIDDLE_RIGHT_COLUMN = {"51", "53", "55"};
    String[] MIDDLE_BOTTOM_ROW = {"11", "31", "51"};
    String[] MIDDLE_TOP_ROW = {"15", "35", "55"};

    String[] INNER_LEFT_COLUMN = {"22", "23", "24"};
    String[] INNER_RIGHT_COLUMN = {"42", "43", "44"};
    String[] INNER_BOTTOM_ROW = {"22", "32", "42"};
    String[] INNER_TOP_ROW = {"24", "34", "44"};

    String[] LEFT_CROSS = {"03", "13", "23"};
    String[] RIGHT_CROSS = {"63", "53", "43"};
    String[] BOTTOM_CROSS = {"30", "31", "32"};
    String[] TOP_CROSS = {"34", "35", "36"};

    String[][] MILL_KEYS = {
      OUTER_LEFT_COLUMN,
      OUTER_RIGHT_COLUMN,
      OUTER_BOTTOM_ROW,
      OUTER_TOP_ROW,
      MIDDLE_LEFT_COLUMN,
      MIDDLE_RIGHT_COLUMN,
      MIDDLE_BOTTOM_ROW,
      MIDDLE_TOP_ROW,
      INNER_LEFT_COLUMN,
      INNER_RIGHT_COLUMN,
      INNER_BOTTOM_ROW,
      INNER_TOP_ROW,
      LEFT_CROSS,
      RIGHT_CROSS,
      BOTTOM_CROSS,
      TOP_CROSS
    };

    for (String[] millKey : MILL_KEYS) {
      MillObserver millObserver = new MillObserver();
      this.gameBoard.attachMillObserverByKey(millObserver, millKey);
      this.millObservers.add(millObserver);
    }
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
