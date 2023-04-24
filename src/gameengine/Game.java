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

  private Action processPlayerAction(Player currentPlayer) {
    String currentCapability = currentPlayer.getCurrentCapability();
    return switch (currentCapability) {
      case "PLACE_TOKEN" -> new PlaceTokenAction();
      case "SELECT_TOKEN" -> new SelectTokenAction();
      default -> new MoveTokenAction();
    };
  }

  private boolean checkForTurnEnd(Player currentPlayer) {
    // Can be extended next sprint to continue turn if Player has formed a mill.
    return currentPlayer.peekTokenInHand() == null;
  }

  private void swapCurrentPlayer() {
    Player tempPlayer = this.currentPlayer;
    this.currentPlayer = this.nonCurrentPlayer;
    this.nonCurrentPlayer = tempPlayer;
  }

  private Player playerFactoryMethod(String tokenType, String tokenImagePath) {
    TokenBank tokenBank = new TokenBank(tokenType, tokenImagePath);
    return new Player(tokenType, tokenBank);
  }
}
