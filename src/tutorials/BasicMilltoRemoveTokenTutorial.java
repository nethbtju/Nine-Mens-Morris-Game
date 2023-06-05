package tutorials;

import actions.Action;
import actions.MoveTokenAction;
import actions.SelectTokenAction;
import gameengine.Game;
import gameengine.GameBoardGui;

public class BasicMilltoRemoveTokenTutorial extends TutorialState{
    public BasicMilltoRemoveTokenTutorial(Game currentGame, GameBoardGui currentGameBoard, String backgroundImagePath) {
        super(currentGame, currentGameBoard, backgroundImagePath);
    }

    @Override
    public void setIntersections() {
        GameBoardGui currentGameBoard = this.getCurrentGameBoard();
        currentGameBoard.removeAllIntersections();

        int[] indexes = {2,20,21,19,22};
        currentGameBoard.addNewIntersections(indexes);
        getCurrentGame().initialiseMillObservers();
    }

    @Override
    public void setTokens() {
        int[][] coords = {{2,1},{19,1},{21,1},{22,0}};
        super.setTokens(coords);
    }

    @Override
    public void setLegalIntersections() {
        int[] coords = {19,20,22};
        int[] highlightCoords = {19};
        super.setAsTutorialLegal(coords);
        super.highLightIntersection(highlightCoords);
    }

    @Override
    public void setActionQueue() {
        Action[] actionList = {new SelectTokenAction(), new MoveTokenAction()};
        this.getCurrentGame().updateActionQueue(actionList);
    }

    @Override
    public void setPlayerQueue() {
        super.updatePlayerQueue(false);
    }
}
