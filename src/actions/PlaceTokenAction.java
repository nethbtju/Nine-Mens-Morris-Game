package actions;

import gameengine.Game;
import gameengine.Intersection;
import gameplayers.Player;

/** Places a token on a given intersection. */
public class PlaceTokenAction extends Action {
    @Override
    public boolean execute(Intersection selectedIntersection, Player player) {
        if (selectedIntersection.isOccupied()) {
            System.out.println("Intersection is occupied!");
            return false;
        }
        else {
            selectedIntersection.updateState(player.getTokenImagePath());
            player.incrementTokenCount();
            selectedIntersection.setOccupied();
            return true;
        }
    }
}
