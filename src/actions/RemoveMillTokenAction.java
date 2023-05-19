package actions;

import gameengine.Intersection;
import gameplayers.Player;

/** An Action used to remove any of an opponent's tokens, even if they are in a mill. */
public class RemoveMillTokenAction extends RemoveTokenAction implements Action {

  /**
   * Checks if the token being removed is from the opponent player's
   *
   * @param selectedIntersection The intersection selected by the user on the frontend.
   * @param player The player performing the action.
   *
   * @return
   */
  @Override
  public boolean isValid(Intersection selectedIntersection, Player player) {
    return (!selectedIntersection.isEmpty()
        && !(player.getTokenType() == selectedIntersection.peekToken().getTokenType()));
  }
}
