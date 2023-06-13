package tutorials;

import actions.Action;
import actions.MoveTokenAction;
import actions.SelectTokenAction;
import gameengine.Game;
import gameengine.GameBoardGui;

/** The Tutorial for moving a Token from one Intersection to another. */
public class MoveTokenTutorial extends TutorialState {

  public MoveTokenTutorial(
      Game currentGame, GameBoardGui currentGameBoard, String backgroundImagePath) {
    super(currentGame, currentGameBoard, backgroundImagePath);
  }

  @Override
  public void setIntersections() {
    GameBoardGui currentGameBoard = this.getCurrentGameBoard();
    currentGameBoard.removeAllIntersections();

    int[] indexes = {2, 20, 21, 22};
    currentGameBoard.addNewIntersections(indexes);
    getCurrentGame().initialiseMillObservers();
  }

  @Override
  public void setTokens() {
    int[][] coordinates = {{2, 1}, {20, 1}};
    super.setTokens(coordinates);
  }

  @Override
  public void setLegalIntersections() {
    int[] coordinates = {20, 21};
    int[] highlightCoordinates = {20};
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
