package tutorials;

import actions.Action;
import actions.PlaceTokenAction;
import gameengine.Game;
import gameengine.GameBoardGui;
/** Tutorial that demonstrates place token action. */
public class PlaceTokenTutorial extends TutorialState{
    public PlaceTokenTutorial(Game currentGame, GameBoardGui currentGameBoard, String backgroundImagePath) {
        super(currentGame, currentGameBoard, backgroundImagePath);
    }

    /** Create the required Intersections to be used by the TutorialState. */
    @Override
    public void setIntersections() {
        GameBoardGui currentGameBoard = this.getCurrentGameBoard();
        currentGameBoard.removeAllIntersections();
        currentGameBoard.addAllIntersections();

    }

    @Override
    public void setTokens() {

    }

    /** Set the Intersections which a Token may move to in a given TutorialState. */
    @Override
    public void setLegalIntersections() {
        super.setAllAsOpen();


    }

    /** Enqueue the Actions required for a given Tutorial. */
    @Override
    public void setActionQueue() {
        Action[] actionList = {new PlaceTokenAction()};
        this.getCurrentGame().updateActionQueue(actionList);
    }

    /** Set the Player order for a given Tutorial. */
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
