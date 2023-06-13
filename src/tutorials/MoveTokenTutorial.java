package tutorials;

import actions.Action;
import actions.MoveTokenAction;
import actions.SelectTokenAction;
import gameengine.Game;
import gameengine.GameBoardGui;
/** Tutorial that demonstrates basic move token action. */
public class MoveTokenTutorial extends TutorialState {

  /**
   * moving tutorial initialisation
   *
   * @param currentGame the current game for the tutorial
   * @param currentGameBoard the current game board for the tutorial
   * @param backgroundImagePath background image path
   */
  public MoveTokenTutorial(
      Game currentGame, GameBoardGui currentGameBoard, String backgroundImagePath) {
    super(currentGame, currentGameBoard, backgroundImagePath);
  }

  /** Create the required Intersections to be used by the TutorialState. */
  @Override
  public void setIntersections() {
    GameBoardGui currentGameBoard = this.getCurrentGameBoard();
    currentGameBoard.removeAllIntersections();

    int[] indexes = {2, 20, 21, 22};
    currentGameBoard.addNewIntersections(indexes);
    getCurrentGame().initialiseMillObservers();
  }

  /** Set the required Tokens onto the required Intersections for the given TutorialState. */
  @Override
  public void setTokens() {
    int[][] coordinates = {{2, 1}, {20, 1}};
    super.setTokens(coordinates);
  }

  /** Set the Intersections which a Token may move to in a given TutorialState. */
  @Override
  public void setLegalIntersections() {
    int[] coordinates = {20, 21};
    int[] highlightCoords = {20};
    super.setAsTutorialLegal(coordinates);
    super.highLightIntersection(highlightCoords);
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
    super.updatePlayerQueue(false, 7, 8);
  }

  /** Set a caption for the Tutorial to inform the Player of what they are being taught. */
  @Override
  public void setCaption() {
    GameBoardGui currentGameBoard = this.getCurrentGameBoard();
    currentGameBoard.changeCaptionString("Select highlighted token and move to open intersection");
  }
}
