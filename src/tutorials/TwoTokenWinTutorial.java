package tutorials;

import actions.Action;
import actions.MoveTokenAction;
import actions.RemoveTokenAction;
import actions.SelectTokenAction;
import gameengine.Game;
import gameengine.GameBoardGui;

public class TwoTokenWinTutorial extends TutorialState{

    public TwoTokenWinTutorial (
        Game currentGame, GameBoardGui currentGameBoard, String backgroundImagePath) {
            super(currentGame, currentGameBoard, backgroundImagePath);
    }

    /**
     * Create the required Intersections to be used by the TutorialState.
     */
    @Override
    public void setIntersections() {
        GameBoardGui currentGameBoard = this.getCurrentGameBoard();
        currentGameBoard.removeAllIntersections();

        int[] indexes = {22, 11, 16};
        currentGameBoard.addNewIntersections(indexes);
        getCurrentGame().initialiseMillObservers();
    }

    /**
     * Set the required Tokens onto the required Intersections for the given TutorialState.
     */
    @Override
    public void setTokens() {
        int[][] coordinates = {{22, 1}, {11, 0}};
        super.setTokens(coordinates);
    }

    /**
     * Set the Intersections which a Token may move to in a given TutorialState.
     */
    @Override
    public void setLegalIntersections() {
        int[] coordinates = {16, 22, 11};
        int[] highlightCoordinates = {16};
        super.setAsTutorialLegal(coordinates);
        super.highLightIntersection(highlightCoordinates);
    }

    /**
     * Enqueue the Actions required for a given Tutorial.
     */
    @Override
    public void setActionQueue() {
        Action[] actionList = {new SelectTokenAction(), new MoveTokenAction(), new RemoveTokenAction()};
        this.getCurrentGame().updateActionQueue(actionList);
    }

    /**
     * Set the Player order for a given Tutorial.
     */
    @Override
    public void setPlayerQueue() {
        super.updatePlayerQueue(true, 2,2 );
    }

    @Override
    public void setCaption() {
        GameBoardGui currentGameBoard = this.getCurrentGameBoard();
        currentGameBoard.changeCaptionString("Form a mill to remove a token to win the game");
    }
}
