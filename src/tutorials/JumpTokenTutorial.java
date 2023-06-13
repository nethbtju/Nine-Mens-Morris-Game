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

  public JumpTokenTutorial(
      Game currentGame, GameBoardGui currentGameBoard, String backgroundImagePath) {
    super(currentGame, currentGameBoard, backgroundImagePath);
  }

  @Override
  public void setIntersections() {
    GameBoardGui currentGameBoard = this.getCurrentGameBoard();
    currentGameBoard.removeAllIntersections();
    currentGameBoard.addAllIntersections();
    getCurrentGame().initialiseMillObservers();
  }

  @Override
  public void setTokens() {
    int[][] coordinates = {{3, 0}, {22, 0}, {13, 1}, {10, 1}, {5, 1}, {4, 1}};
    super.setTokens(coordinates);
  }

  @Override
  public void setLegalIntersections() {
    int[] highlightCoordinates = {22};
    super.setAllAsLegal();
    super.highLightIntersection(highlightCoordinates);
  }

  @Override
  public void setActionQueue() {
    Action[] actionList = {new SelectTokenAction(), new MoveTokenAction()};
    this.getCurrentGame().updateActionQueue(actionList);
  }

  @Override
  public void setPlayerQueue() {
    super.updatePlayerQueue(true, 2, 4);
  }

  @Override
  public void setCaption() {
    GameBoardGui currentGameBoard = this.getCurrentGameBoard();
    currentGameBoard.changeCaptionString(
        "Select a Token with only two remaining, then jump it to anywhere.");
  }
}