package actions;

import gameengine.Intersection;

/** Moves a token from one intersection to another. */
public class MoveTokenAction extends Action {
    @Override
    public void execute(Intersection selectedIntersection, boolean player) {
        System.out.println("Move Action!");
    }
}
