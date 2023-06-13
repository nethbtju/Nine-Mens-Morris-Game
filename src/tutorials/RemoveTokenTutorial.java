package tutorials;

import actions.Action;
import actions.MoveTokenAction;
import actions.SelectTokenAction;
import gameengine.Game;
import gameengine.GameBoardGui;

/** The Tutorial to remove a Token from a mill in the case that Tokens exist outside a mill. */
public class RemoveTokenTutorial extends TutorialState {

  public RemoveTokenTutorial(
      Game currentGame, GameBoardGui currentGameBoard, String backgroundImagePath) {
    super(currentGame, currentGameBoard, backgroundImagePath);
  }

  @Override
  public void setIntersections() {
    GameBoardGui currentGameBoard = this.getCurrentGameBoard();
    currentGameBoard.removeAllIntersections();

    int[] indexes = {2, 20, 21, 19, 22};
    currentGameBoard.addNewIntersections(indexes);
    getCurrentGame().initialiseMillObservers();
  }

  @Override
  public void setTokens() {
    int[][] coordinates = {{2, 1}, {19, 1}, {21, 1}, {22, 0}};
    super.setTokens(coordinates);
  }

  @Override
  public void setLegalIntersections() {
    int[] coordinates = {19, 20, 22};
    int[] highlightCoordinates = {19};
    super.setAsTutorialLegal(coordinates);
    super.highLightIntersection(highlightCoordinates);
  }

  @Override
  public void setActionQueue() {
    Action[] actionList = {new SelectTokenAction(), new MoveTokenAction()};
    this.getCurrentGame().updateActionQueue(actionList);
  }

  @Override
  public void setPlayerQueue() {
    super.updatePlayerQueue(false);
  }
}
