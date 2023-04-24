package gameengine;

import actions.Action;
import actions.MoveTokenAction;
import actions.PlaceTokenAction;
import actions.SelectTokenAction;
import gameplayers.Player;
import java.io.IOException;
import tokens.TokenBank;

/** An instance of a Nine Man's Morris game. Highest level class in the game architecture. */
public class Game {
  GameBoardGui gameBoard;
  Player currentPlayer;
  Player nonCurrentPlayer;

  /**
   * Constructor for Game.
   *
   * @throws IOException Excepts if GameBoard fails to initialise due to missing background image.
   */
  public Game() throws IOException {
    gameBoard = new GameBoardGui(this);
    gameBoard.createGui();
    this.currentPlayer = this.playerFactoryMethod("WHITE", "img/BoardImages/WhiteTokenPlain.png");
    this.nonCurrentPlayer = this.playerFactoryMethod("BLACK", "img/BoardImages/BlackTokenPlain.png");
  }

  /**
   * Play a turn for the current player.
   *
   * @param selectedIntersection The intersection selected by the user on the frontend.
   */
  public void playTurn(Intersection selectedIntersection) {
    Action currentAction = this.processPlayerAction(this.currentPlayer);
    boolean actionExecuted = currentAction.execute(selectedIntersection, this.currentPlayer);
    if (actionExecuted && this.checkForTurnEnd((this.currentPlayer))) {
      this.swapCurrentPlayer();
    }
  }

  /**
   * Determine the Action that the Player is currently capable of taking.
   *
   * @param currentPlayer The Player whose turn it is.
   * @return The Action that the Player can currently take based on their Capability.
   */
  private Action processPlayerAction(Player currentPlayer) {
    String currentCapability = currentPlayer.getCurrentCapability();
    if (currentCapability.equals("PLACE_TOKEN")) {
      return new PlaceTokenAction();
    } else if (currentCapability.equals("MOVE_TOKEN")) {
      if (!currentPlayer.hasTokenInHand()) {
        return new SelectTokenAction();
      } else {
        return new MoveTokenAction();
      }
    }
    return new SelectTokenAction();
  }

  /**
   * Checks if the Player is done with their turn or whether they have more Actions remaining.
   *
   * @param currentPlayer The Player whose turn it is.
   * @return true if the turn is over, false if not.
   */
  private boolean checkForTurnEnd(Player currentPlayer) {
    // Can be extended next sprint to continue turn if Player has formed a mill.
    return !currentPlayer.hasTokenInHand();
  }

  /** Swap the current player with the other player. */
  private void swapCurrentPlayer() {
    Player tempPlayer = this.currentPlayer;
    this.currentPlayer = this.nonCurrentPlayer;
    this.nonCurrentPlayer = tempPlayer;
  }

  /** Construct a new Player with associated Tokens. */
  private Player playerFactoryMethod(String tokenType, String tokenImagePath) {
    TokenBank tokenBank = new TokenBank(tokenType, tokenImagePath);
    return new Player(tokenType, tokenBank);
  }
}
