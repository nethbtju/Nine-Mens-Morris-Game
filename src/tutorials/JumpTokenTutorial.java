package tutorials;

import actions.Action;
import actions.MoveTokenAction;
import actions.SelectTokenAction;
import gameengine.Game;
import gameengine.GameBoardGui;

/**
 * A tutorial for moving a Token to any space if a Player has only 3 tokens left on the board and
 * has already placed all of their Tokens.
 */
public class JumpTokenTutorial extends TutorialState {

  /**
   * Jumping tutorial initialisation
   *
   * @param currentGame the current game for the tutorial
   * @param currentGameBoard the current game board for the tutorial
   * @param backgroundImagePath background image path
   */
  public JumpTokenTutorial(
      Game currentGame, GameBoardGui currentGameBoard, String backgroundImagePath) {
    super(currentGame, currentGameBoard, backgroundImagePath);
  }

  /** Create the required Intersections to be used by the TutorialState. */
  @Override
  public void setIntersections() {
    GameBoardGui currentGameBoard = this.getCurrentGameBoard();
    currentGameBoard.removeAllIntersections();
    currentGameBoard.addAllIntersections();
    getCurrentGame().initialiseMillObservers();
  }

  /** Set the required Tokens onto the required Intersections for the given TutorialState. */
  @Override
  public void setTokens() {
    int[][] coordinates = {{3, 0}, {22, 0}, {0, 0}, {13, 1}, {10, 1}, {5, 1}, {4, 1}};
    super.setTokens(coordinates);
  }

  /** Set the Intersections which a Token may move to in a given TutorialState. */
  @Override
  public void setLegalIntersections() {
    int[] highlightCoordinates = {22};
    super.setAllAsLegal();
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
    super.updatePlayerQueue(true, 3, 4);
  }

  /** Set a caption for the Tutorial to inform the Player of what they are being taught. */
  @Override
  public void setCaption() {
    GameBoardGui currentGameBoard = this.getCurrentGameBoard();
    currentGameBoard.changeCaptionString(
        "Select a Token with only two remaining, then jump it to anywhere.");
  }
}
