package tutorials;

import gameengine.Game;
import gameengine.GameBoardGui;

/**
 * A special variant of the RemoveTokenTutorial for removing a Token from a mill in the case that
 * all opposing Tokens are in mills.
 */
public class RemoveMillTokenTutorial extends RemoveTokenTutorial {

  /**
   * removing tokens in a mill tutorial initialisation
   *
   * @param currentGame the current game for the tutorial
   * @param currentGameBoard the current game board for the tutorial
   * @param backgroundImagePath background image path
   */
  public RemoveMillTokenTutorial(
      Game currentGame, GameBoardGui currentGameBoard, String backgroundImagePath) {
    super(currentGame, currentGameBoard, backgroundImagePath);
  }

  /** Create the required Intersections to be used by the TutorialState. */
  @Override
  public void setIntersections() {
    GameBoardGui currentGameBoard = this.getCurrentGameBoard();
    currentGameBoard.removeAllIntersections();

    int[] indexes = {0, 1, 2, 20, 21, 3, 14, 23, 12, 18};
    currentGameBoard.addNewIntersections(indexes);
    getCurrentGame().initialiseMillObservers();
  }

  /** Set the required Tokens onto the required Intersections for the given TutorialState. */
  @Override
  public void setTokens() {
    int[][] coordinates = {
      {0, 0}, {1, 0}, {2, 0}, {20, 0}, {21, 0}, {3, 1}, {14, 1}, {12, 1}, {18, 1}
    };
    super.setTokens(coordinates);
  }

  /** Set the Intersections which a Token may move to in a given TutorialState. */
  @Override
  public void setLegalIntersections() {
    int[] coordinates = {12, 23, 0, 1, 2, 20, 21};
    int[] highlightCoordinates = {12};
    super.setAsTutorialLegal(coordinates);
    super.highLightIntersection(highlightCoordinates);
  }

  /** Set the Player order for a given Tutorial. */
  @Override
  public void setPlayerQueue() {
    super.updatePlayerQueue(false, 5, 4);
  }
}
