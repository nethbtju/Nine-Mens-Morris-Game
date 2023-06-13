package tutorials;

import actions.Action;
import actions.MoveTokenAction;
import actions.SelectTokenAction;
import gameengine.Game;
import gameengine.GameBoardGui;

/** The Tutorial to remove a Token from a mill in the case that Tokens exist outside a mill. */
public class RemoveTokenTutorial extends TutorialState {

  /**
   * removing tokens tutorial initialisation
   *
   * @param currentGame the current game for the tutorial
   * @param currentGameBoard the current game board for the tutorial
   * @param backgroundImagePath background image path
   */
  public RemoveTokenTutorial(
      Game currentGame, GameBoardGui currentGameBoard, String backgroundImagePath) {
    super(currentGame, currentGameBoard, backgroundImagePath);
  }

  /** Create the required Intersections to be used by the TutorialState. */
  @Override
  public void setIntersections() {
    GameBoardGui currentGameBoard = this.getCurrentGameBoard();
    currentGameBoard.removeAllIntersections();

    int[] indexes = {2, 20, 21, 19, 22};
    currentGameBoard.addNewIntersections(indexes);
    getCurrentGame().initialiseMillObservers();
  }

  /** Set the required Tokens onto the required Intersections for the given TutorialState. */
  @Override
  public void setTokens() {
    int[][] coordinates = {{2, 1}, {19, 1}, {21, 1}, {22, 0}};
    super.setTokens(coordinates);
  }

  /** Set the Intersections which a Token may move to in a given TutorialState. */
  @Override
  public void setLegalIntersections() {
    int[] coordinates = {19, 20, 22};
    int[] highlightCoordinates = {19};
    super.setAsTutorialLegal(coordinates);
    super.highLightIntersection(highlightCoordinates);
  }

  /** Enqueue the Actions required for a given Tutorial. */
  @Override
  public void setActionQueue() {
    Action[] actionList = {new SelectTokenAction(), new MoveTokenAction()};
    this.getCurrentGame().updateActionQueue(actionList);
  }

  /** Set the Player order for a given Tutorial. */
  @Override
  public void setPlayerQueue() {
    super.updatePlayerQueue(false, 8, 9);
  }

  /** Set a caption for the Tutorial to inform the Player of what they are being taught. */
  @Override
  public void setCaption() {
    GameBoardGui currentGameBoard = this.getCurrentGameBoard();
    currentGameBoard.changeCaptionString("Move selected token to form a mill, then remove enemy token");
  }
}
