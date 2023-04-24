package actions;

import gameengine.Intersection;

/** An abstract class for actions, which allow the player to change the state of an intersection. */
public abstract class Action {

    /**
     * Execute the Action, changing the state of an intersection.
     *
     * @param selectedIntersection The intersection which is having its state changed.
     * @param player The player performing the Action.
     */
    public abstract void execute(Intersection selectedIntersection, boolean player);
}
