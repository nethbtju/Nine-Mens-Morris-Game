package actions;

import gameengine.Game;
import gameengine.Intersection;
import gameplayers.Player;

public class PlaceTokenAction extends Action {
    String blackToken = "img/BoardImages/BlackTokenPlain.png";
    String whiteToken = "img/BoardImages/WhiteTokenPlain.png";

    Player currentPlayer;
    Game currentGame;

    public PlaceTokenAction(Game currentGame, Player currentPlayer){
        this.currentGame = currentGame;
        this.currentPlayer = currentPlayer;

    }
    @Override
    public void execute(Intersection selectedIntersection, boolean player) {
        this.updateIntersection(selectedIntersection, player);

    }

    private void updateIntersection(Intersection selectedIntersection, boolean player){
        if (player) {
            selectedIntersection.updateState(this.whiteToken);
        }
        else{
            selectedIntersection.updateState(this.blackToken);
        }

    }

    private void decrementPlayerToken(){

    }

    private void updateGUI(){

    }


}
