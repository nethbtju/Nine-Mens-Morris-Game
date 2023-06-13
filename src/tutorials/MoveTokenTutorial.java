package tutorials;

import actions.Action;
import actions.MoveTokenAction;
import actions.PlaceTokenAction;
import actions.SelectTokenAction;
import gameengine.Game;
import gameengine.GameBoardGui;
import gameengine.Intersection;

import java.util.HashMap;

public class MoveTokenTutorial extends TutorialState{

    public MoveTokenTutorial(Game currentGame, GameBoardGui currentGameBoard, String backgroundImagePath) {
        super(currentGame, currentGameBoard, backgroundImagePath);
    }

    @Override
    public void setIntersections() {
        GameBoardGui currentGameBoard = this.getCurrentGameBoard();
        currentGameBoard.removeAllIntersections();

        int[] indexes = {2,20,21,22};
        currentGameBoard.addNewIntersections(indexes);
        getCurrentGame().initialiseMillObservers();

    }

    @Override
    public void setTokens() {
        int[][] coords = {{2,1},{20,1}};
        super.setTokens(coords);

    }

    @Override
    public void setLegalIntersections() {
        int[] coords = {20,21};
        int[] highlightCoords = {20};
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

    @Override
    public void setCaption() {
        GameBoardGui currentGameBoard = this.getCurrentGameBoard();
        currentGameBoard.changeCaptionString("Select highlighted token and move to open intersection");
    }
}
