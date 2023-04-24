package actions;

import gameengine.Game;
import gameengine.Intersection;
import gameplayers.Player;

/** Places a token on a given intersection. */
public class PlaceTokenAction extends Action {
    @Override
    public void execute(Intersection selectedIntersection, Player player) {
        selectedIntersection.updateState(player.getTokenImagePath());
    }
}
