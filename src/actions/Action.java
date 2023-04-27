package actions;

import gameengine.Intersection;
import gameplayers.Player;

/** An abstract class for actions, which allow the player to change the state of an intersection. */
public interface Action {

  /**
   * Execute the Action, changing the state of an intersection.
   *
   * @param selectedIntersection The intersection selected by the user on the frontend.
   * @param player The player performing the Action.
   * @return False if the execution failed (invalid move), true if successful.
   */
  boolean execute(Intersection selectedIntersection, Player player);
}
