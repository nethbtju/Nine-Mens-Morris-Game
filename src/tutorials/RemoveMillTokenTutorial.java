package tutorials;

import gameengine.Game;
import gameengine.GameBoardGui;

/**
 * A special variant of the RemoveTokenTutorial for removing a Token from a mill in the case that
 * all opposing Tokens are in mills.
 */
public class RemoveMillTokenTutorial extends RemoveTokenTutorial {

  public RemoveMillTokenTutorial(
      Game currentGame, GameBoardGui currentGameBoard, String backgroundImagePath) {
    super(currentGame, currentGameBoard, backgroundImagePath);
  }

  @Override
  public void setIntersections() {
    GameBoardGui currentGameBoard = this.getCurrentGameBoard();
    currentGameBoard.removeAllIntersections();

    int[] indexes = {0, 1, 2, 20, 21, 3, 14, 23, 12, 18};
    currentGameBoard.addNewIntersections(indexes);
    getCurrentGame().initialiseMillObservers();
  }

  @Override
  public void setTokens() {
    int[][] coordinates = {
      {0, 0}, {1, 0}, {2, 0}, {20, 0}, {21, 0}, {3, 1}, {14, 1}, {12, 1}, {18, 1}
    };
    super.setTokens(coordinates);
  }

  @Override
  public void setLegalIntersections() {
    int[] coordinates = {12, 23, 0, 1, 2, 20, 21};
    int[] highlightCoordinates = {12};
    super.setAsTutorialLegal(coordinates);
    super.highLightIntersection(highlightCoordinates);
  }
}