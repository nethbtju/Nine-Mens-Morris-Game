package gameengine;

import actions.Action;
import actions.MoveTokenAction;
import actions.PlaceTokenAction;
import gameplayers.Player;
import java.io.IOException;

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
    this.currentPlayer = new Player("White", "img/BoardImages/WhiteTokenPlain.png");
    this.nonCurrentPlayer = new Player("Black", "img/BoardImages/BlackTokenPlain.png");
  }

  /**
   * Play a turn for the current player.
   *
   * @param selectedIntersection The intersection selected by the user on the frontend.
   */
  public void playTurn(Intersection selectedIntersection) {
    Action currentAction = this.processPlayerAction(this.currentPlayer);
    boolean actionExecuted = currentAction.execute(selectedIntersection, this.currentPlayer);
    if (actionExecuted) { this.swapCurrentPlayer(); }
  }

  private Action processPlayerAction(Player currentPlayer) {
    String currentCapability = currentPlayer.getCurrentCapability();
    if (currentCapability.equals("PLACE_TOKEN")) {
      return new PlaceTokenAction();
    } else if (currentCapability.equals("MOVE_TOKEN")) {
      System.out.println("Player can move!");
      return new MoveTokenAction();
    }
    return new MoveTokenAction();
  }

  private void swapCurrentPlayer() {
    Player tempPlayer = this.currentPlayer;
    this.currentPlayer = this.nonCurrentPlayer;
    this.nonCurrentPlayer = tempPlayer;
  }
}
