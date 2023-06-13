package tutorials;

import actions.Action;
import actions.MoveTokenAction;
import actions.PlaceTokenAction;
import actions.SelectTokenAction;
import gameengine.Game;
import gameengine.GameBoardGui;

/** The TutorialState for placing a new Token onto the board. */
public class PlaceTokenTutorial extends TutorialState {

  public PlaceTokenTutorial(
      Game currentGame, GameBoardGui currentGameBoard, String backgroundImagePath) {
    super(currentGame, currentGameBoard, backgroundImagePath);
  }

  @Override
  public void setIntersections() {
    GameBoardGui currentGameBoard = this.getCurrentGameBoard();
    currentGameBoard.removeAllIntersections();
    currentGameBoard.addAllIntersections();
  }

  @Override
  public void setTokens() {}

  @Override
  public void setLegalIntersections() {
    super.setAllAsLegal();
  }

  @Override
  public void setActionQueue() {
    Action[] actionList = {new PlaceTokenAction()};
    this.getCurrentGame().updateActionQueue(actionList);
  }

  @Override
  public void setPlayerQueue() {
    super.updatePlayerQueue(true);
  }
}
