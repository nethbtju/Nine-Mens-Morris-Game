package actions;

import gameengine.Intersection;
import gameplayers.Player;

/** Moves a token from one intersection to another. */
public class MoveTokenAction extends Action {
    @Override
    public boolean execute(Intersection selectedIntersection, Player player) {
        System.out.println("Move Action!");
        return true;
    }
}
