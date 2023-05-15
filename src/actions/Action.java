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
   *
   */
  void execute(Intersection selectedIntersection, Player player);

  /**
   * Check whether the Action is valid given the intersection selected by the user.
   *
   * @param selectedIntersection The intersection selected by the user on the frontend.
   * @param player The player performing the action.
   *
   * @return True if the selection
   */
  boolean isValid(Intersection selectedIntersection, Player player);
}
