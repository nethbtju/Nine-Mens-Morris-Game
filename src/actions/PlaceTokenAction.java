package actions;

import gameengine.Game;
import gameengine.Intersection;
import gameplayers.Player;

/** Places a token on a given intersection. */
public class PlaceTokenAction extends Action {
    String blackToken = "img/BoardImages/BlackTokenPlain.png";
    String whiteToken = "img/BoardImages/WhiteTokenPlain.png";

    Player currentPlayer;
    Game currentGame;

    /**
     * Constructor for the PlaceTokenAction class.
     *
     * @param currentGame The overarching game in which the action is occurring.
     * @param currentPlayer The player who is performing the action during their turn.
     */
    public PlaceTokenAction(Game currentGame, Player currentPlayer) {
        this.currentGame = currentGame;
        this.currentPlayer = currentPlayer;
    }

    @Override
    public void execute(Intersection selectedIntersection, boolean player) {
        this.updateIntersection(selectedIntersection, player);

    }

    private void updateIntersection(Intersection selectedIntersection, boolean player) {
        if (player) {
            selectedIntersection.updateState(this.whiteToken);
        } else {
            selectedIntersection.updateState(this.blackToken);
        }

    }

    private void decrementPlayerToken(){

    }

    private void updateGui() {

    }
}
