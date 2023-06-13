package tutorials;

import actions.Action;
import actions.MoveTokenAction;
import actions.RemoveTokenAction;
import actions.SelectTokenAction;
import gameengine.Game;
import gameengine.GameBoardGui;

/** A Tutorial for winning the game by reducing the opponent's tokenCount to 2. */
public class TwoTokenWinTutorial extends TutorialState {

  /**
   * winning with 2 tokens on the opponent side tutorial initialisation
   *
   * @param currentGame the current game for the tutorial
   * @param currentGameBoard the current game board for the tutorial
   * @param backgroundImagePath background image path
   */
  public TwoTokenWinTutorial(
      Game currentGame, GameBoardGui currentGameBoard, String backgroundImagePath) {
    super(currentGame, currentGameBoard, backgroundImagePath);
  }

  /** Create the required Intersections to be used by the TutorialState. */
  @Override
  public void setIntersections() {
    GameBoardGui currentGameBoard = this.getCurrentGameBoard();
    currentGameBoard.removeAllIntersections();

    int[] indexes = {0, 2, 22, 11, 16, 17, 18, 7};
    currentGameBoard.addNewIntersections(indexes);
    getCurrentGame().initialiseMillObservers();
  }

  /** Set the required Tokens onto the required Intersections for the given TutorialState. */
  @Override
  public void setTokens() {
    int[][] coordinates = {{0, 1}, {2, 1}, {22, 1}, {11, 0}, {16, 0}, {18, 0}, {7, 0}};
    super.setTokens(coordinates);
  }

  /** Set the Intersections which a Token may move to in a given TutorialState. */
  @Override
  public void setLegalIntersections() {
    int[] coordinates = {0, 2, 22, 18, 17};
    int[] highlightCoordinates = {18};
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
    super.updatePlayerQueue(true, 4, 3);
  }


  /** Set a caption for the Tutorial to inform the Player of what they are being taught. */
  @Override
  public void setCaption() {
    GameBoardGui currentGameBoard = this.getCurrentGameBoard();
    currentGameBoard.changeCaptionString(
        "Form a mill to remove all but 2 of the opponent's Tokens and win the game.");
  }
}
