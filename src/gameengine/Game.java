package gameengine;

import actions.*;
import gameplayers.Capable;
import gameplayers.GameState;
import gameplayers.Player;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import tokens.TokenBank;
import tokens.TokenType;
import tutorials.*;

/** An instance of a Nine Man's Morris game. Highest level class in the game architecture. */
public class Game {
  int x = 0;
  private final GameBoardGui gameBoard;
  private final Queue<Player> playerQueue = new LinkedList<>();
  private final Queue<Action> actionQueue = new LinkedList<>();
  private final ArrayList<MillObserver> millObservers = new ArrayList<>();

  private TutorialManager newManager = new TutorialManager();

  private GameState currentGamestate = GameState.TUTORIAL;

  /**
   * Constructor for the Game, initialises game backend.
   *
   * @throws IOException Excepts if the GameBoard fails to initialise due to a missing background
   *     image.
   */
  public Game(GameState currentGamestate) throws IOException {
    this.currentGamestate = currentGamestate;

    gameBoard = new GameBoardGui(this);
    gameBoard.createGui();

    this.initialisePlayers();
    this.initialiseMillObservers();
    this.updatePlayTurnDisplay();
    this.gameBoard.updatePlayerTurnDisplay(
        "/resources/META-INF/img/BoardImages/whiteWinScreen.png");
    this.initialiseTutorials();
  }

  /**
   * Play a turn for the current player.
   *
   * @param selectedIntersection The intersection selected by the user on the frontend.
   */
  public void playTurn(Intersection selectedIntersection) {
    Player currentPlayer = this.playerQueue.peek();

    boolean initialMillState = selectedIntersection.getMillState();

    if (this.actionQueue.isEmpty()) {
      this.pushPlayerActions(currentPlayer);
    } else {
      this.updateMoveSequence(currentPlayer, selectedIntersection);
    }

    Action action = this.actionQueue.peek();
    boolean actionValid = action.isValid(selectedIntersection, currentPlayer);

    if (actionValid) {
      this.gameBoard.unhighlightAllIntersections();
      this.gameBoard.highlightOpenIntersections();
      action.execute(selectedIntersection, currentPlayer);
      this.gameBoard.setMillStates();
      if (!initialMillState && selectedIntersection.getMillState()) {
        this.pushMillActions();
      }
      this.actionQueue.remove();
    }
    if (!currentPlayer.getTokenBank().isEmpty()) {
      try {
        this.gameBoard.updateCover(currentPlayer);
      } catch (IOException e) {
        System.out.println("Could not update cover");
      }
    }

    if (this.actionQueue.isEmpty()) {
      System.out.println(x);
      this.playerQueue.add(this.playerQueue.remove());

      if (this.currentGamestate == GameState.TUTORIAL) {
        this.gameBoard.setAllAsTutorialLocked();
        if (x % 2 == 0) {}

        if (x == 1) {
          // this.newManager.executeNext();

        }

        if (x == 3) {
          // this.newManager.executeNext();

        }

        if (x == 5) {
          // this.newManager.executeNext();
        }

        if (x == 7) {
          // this.newManager.executePrevious();
        }

        if (x == 9) {
          // this.newManager.executePrevious();
        }

        if (x == 11) {
          // this.newManager.executePrevious();
        }

        x = x + 1;
      }
    }

    this.updatePlayTurnDisplay();

    // this.updatePlayTurnDisplay();
  }

  /**
   * Push the appropriate Action to the current player's actionQueue depending on whether their
   * opponent has all of their Tokens in mills.
   */
  private void pushMillActions() {
    Player currentPlayer = this.playerQueue.remove();
    Player attackedPlayer = this.playerQueue.remove();

    if (this.gameBoard.getMillIntersectionCount(attackedPlayer.getTokenType())
        == attackedPlayer.getTokenCount()) {
      // highlight all tokens
      this.actionQueue.add(new RemoveMillTokenAction());
      this.gameBoard.highlightRemoveTokens(attackedPlayer.getTokenType(), true);
    } else {
      // only highlight tokens that can be removed
      this.actionQueue.add(new RemoveTokenAction());
      this.gameBoard.highlightRemoveTokens(attackedPlayer.getTokenType(), false);
      System.out.println(attackedPlayer.getTokenType());
    }
    this.playerQueue.clear();
    this.playerQueue.add(currentPlayer);
    this.playerQueue.add(attackedPlayer);
  }

  private void updateMoveSequence(Player currentPlayer, Intersection selectedIntersection) {

    if (selectedIntersection.peekToken() != null) {
      if (selectedIntersection.peekToken().getTokenType() == currentPlayer.getTokenType()
          && currentPlayer.hasTokenInHand()) {
        System.out.println(currentPlayer.hasTokenInHand());
        System.out.println("yo");
        this.actionQueue.remove();
        this.actionQueue.add(new SelectTokenAction());
        this.actionQueue.add(new MoveTokenAction());
        this.gameBoard.setIntersectionsAsClosed();
      }
    }
  }

  /** Initialise the MillObservers, which detect Mill formation. */
  public void initialiseMillObservers() {
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
      this.actionQueue.add(new PlaceTokenAction());
    } else if (currentCapability == Capable.MOVEABLE || currentCapability == Capable.JUMPABLE) {
      this.gameBoard.setIntersectionsAsClosed();
      this.actionQueue.add(new SelectTokenAction());
      this.actionQueue.add(new MoveTokenAction());
    }
  }

  /** Enqueue two players to the game. */
  public void initialisePlayers() {
    Player player1 =
        new Player(
            TokenType.WHITE,
            new TokenBank(
                TokenType.WHITE,
                "/resources/META-INF/img/BoardImages/WhiteTokenPlain.png",
                "/resources/META-INF/img/BoardImages/WhiteTokenSelected.png",
                "/resources/META-INF/img/BoardImages/WhiteTokenIllegal.png",
                "/resources/META-INF/img/BoardImages/WhiteTokenMill.png"),
            GameState.NORMAL);
    Player player2 =
        new Player(
            TokenType.BLACK,
            new TokenBank(
                TokenType.BLACK,
                "/resources/META-INF/img/BoardImages/BlackTokenPlain.png",
                "/resources/META-INF/img/BoardImages/BlackTokenSelected.png",
                "/resources/META-INF/img/BoardImages/BlackTokenIllegal.png",
                "/resources/META-INF/img/BoardImages/BlackTokenMill.png"),
            GameState.NORMAL);
    this.playerQueue.clear();
    this.playerQueue.add(player1);
    this.playerQueue.add(player2);
  }

  /** Prints the Player whose turn it is. */
  public void updatePlayTurnDisplay() {
    Player currentPlayer = playerQueue.peek();
    TokenType playerType = currentPlayer.getTokenType();

    if (playerType == TokenType.WHITE) {
      this.gameBoard.updatePlayerTurnDisplay("Player 1 Turn!");
    } else {
      this.gameBoard.updatePlayerTurnDisplay("Player 2 Turn!");
    }
  }

  /**
   * Checks whether a Player has lost the game.
   *
   * @param player The Player whose loss is being decided.
   * @return True if the Player has lost, false if not.
   */
  public boolean checkPlayerLose(Player player) {
    int currentPlayerTokens = player.getTokenCount();
    return (currentPlayerTokens < 3 && player.getTokenBank().isEmpty())
        || !this.gameBoard.hasAnyLegalMoves(player.getTokenType());
  }

  /**
   * Gets the game board
   *
   * @return instance of the current gameBoard GUI
   */
  public GameBoardGui getGameBoard() {
    return gameBoard;
  }

  /**
   * Checks if the current play has lost before executing the kill method
   *
   * @param attackedPlayer - The current player who have been popped off the queue
   */
  public void checkIfGameOver(Player attackedPlayer) {
    if (this.checkPlayerLose(attackedPlayer)) {
      printWinScreen(attackedPlayer);
      this.gameBoard.killGame();
    }
  }

  /** Prints the label on the GUI that shows which player has won */
  public void printWinScreen(Player attackedPlayer) {
    String image;
    if (attackedPlayer.getTokenType() == TokenType.BLACK) {
      image = "/resources/META-INF/img/BoardImages/whiteWinScreen.png";
    } else {
      image = "/resources/META-INF/img/BoardImages/blackWinScreen.png";
    }
    this.gameBoard.showWinnerDisplay(image);
  }

  /** Gets both players and removes the attacked players tokens, then checks if the game is over */
  public void decrementOpposingPlayerTokenCount() {
    Player currentPlayer = this.playerQueue.remove();
    Player attackedPlayer = this.playerQueue.remove();

    attackedPlayer.decrementTokenCount();

    this.playerQueue.add(currentPlayer);
    this.playerQueue.add(attackedPlayer);

    // check when game is lost
    this.checkIfGameOver(attackedPlayer);
  }

  public void wait(int ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException ex) {
      Thread.currentThread().interrupt();
    }
  }

  public void updateActionQueue(Action[] actions) {
    System.out.println("yessir");
    this.actionQueue.clear();
    for (int i = 0; i < actions.length; i++) {
      this.actionQueue.add(actions[i]);
    }
  }

  public Queue<Player> getPlayerQueue() {
    return this.playerQueue;
  }

  public void updatePlayerQueue(Player player1, Player player2) {
    this.playerQueue.clear();
    this.playerQueue.add(player1);
    this.playerQueue.add(player2);
  }

  private void tutorialGameMode() {}

  public GameState getGameState() {
    return this.currentGamestate;
  }

  private void initialiseTutorials() {
    // this.newManager.add(new PlaceTokenTutorial(this, this.gameBoard,
    // "/resources/META-INF/img/BoardImages/board600pxls.png"));
    this.newManager.add(
        new PlaceTokenTutorial(
            this, this.gameBoard, "/resources/META-INF/img/BoardImages/board600pxls.png"));
    this.newManager.add(
        new MoveTokenTutorial(
            this, this.gameBoard, "/resources/META-INF/img/BoardImages/GameBoardSaturated.png"));
    this.newManager.add(
        new BasicMillToRemoveTokenTutorial(
            this, this.gameBoard, "/resources/META-INF/img/BoardImages/GameBoardSaturated.png"));

    if (this.currentGamestate == GameState.TUTORIAL) {
      this.newManager.executeNext();
    }
  }

  public TutorialManager getTutorialManager() {
    return this.newManager;
  }
}
