package tutorials;

import actions.Action;
import actions.MoveTokenAction;
import actions.SelectTokenAction;
import gameengine.Game;
import gameengine.GameBoardGui;

/** A Tutorial for winning the game by blocking the opponent from making any legal moves. */
public class NoLegalMovesWinTutorial extends TutorialState {

  public NoLegalMovesWinTutorial(
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
    int[][] coordinates = {{0, 0}, {9, 0}, {10, 0}, {1, 0}, {2, 1}, {4, 1}, {3, 1}, {13, 1}};
    super.setTokens(coordinates);
  }

  @Override
  public void setLegalIntersections() {
    int[] coordinates = {3, 14};
    int[] highlightCoordinates = {3};
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
    super.updatePlayerQueue(false, 4, 4);
  }

  @Override
  public void setCaption() {
    GameBoardGui currentGameBoard = this.getCurrentGameBoard();
    currentGameBoard.changeCaptionString(
        "Block your opponent's last legal move option and win the game."
    );
  }
}