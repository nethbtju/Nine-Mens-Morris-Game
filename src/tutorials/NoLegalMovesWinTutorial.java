package tutorials;

import actions.Action;
import actions.MoveTokenAction;
import actions.SelectTokenAction;
import gameengine.Game;
import gameengine.GameBoardGui;

/** A Tutorial for winning the game by blocking the opponent from making any legal moves. */
public class NoLegalMovesWinTutorial extends TutorialState {

  /**
   * no legal moves tutorial initialisation
   *
   * @param currentGame the current game for the tutorial
   * @param currentGameBoard the current game board for the tutorial
   * @param backgroundImagePath background image path
   */
  public NoLegalMovesWinTutorial(
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
    int[][] coordinates = {{0, 0}, {9, 0}, {10, 0}, {1, 0}, {2, 1}, {4, 1}, {3, 1}, {13, 1}};
    super.setTokens(coordinates);
  }

  /** Set the Intersections which a Token may move to in a given TutorialState. */
  @Override
  public void setLegalIntersections() {
    int[] coordinates = {3, 14};
    int[] highlightCoordinates = {3};
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
    super.updatePlayerQueue(false, 4, 4);
  }

  /** Set a caption for the Tutorial to inform the Player of what they are being taught. */
  @Override
  public void setCaption() {
    GameBoardGui currentGameBoard = this.getCurrentGameBoard();
    currentGameBoard.changeCaptionString(
        "Block your opponent's last legal move option and win the game."
    );
  }
}
