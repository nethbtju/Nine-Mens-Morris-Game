package tutorials;

import actions.Action;
import actions.PlaceTokenAction;
import gameengine.Game;
import gameengine.GameBoardGui;

public class PlaceTokenTutorial extends TutorialState{
    public PlaceTokenTutorial(Game currentGame, GameBoardGui currentGameBoard, String backgroundImagePath) {
        super(currentGame, currentGameBoard, backgroundImagePath);
    }

    @Override
    public void setIntersections() {
        GameBoardGui currentGameBoard = this.getCurrentGameBoard();
        currentGameBoard.removeAllIntersections();
        currentGameBoard.addAllIntersections();

    }

    @Override
    public void setTokens() {

    }

    @Override
    public void setLegalIntersections() {
        super.setAllAsOpen();


    }

    @Override
    public void setActionQueue() {
        Action[] actionList = {new PlaceTokenAction()};
        this.getCurrentGame().updateActionQueue(actionList);
    }

    @Override
    public void setPlayerQueue() {
        super.updatePlayerQueue(true, 0, 0);
    }

    @Override
    public void setCaption() {
        GameBoardGui currentGameBoard = this.getCurrentGameBoard();
        currentGameBoard.changeCaptionString("Place token on any open intersection");
    }
}
