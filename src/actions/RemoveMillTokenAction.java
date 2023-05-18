package actions;

import gameengine.Intersection;
import gameplayers.Player;

/** An Action used to remove any of an opponent's tokens, even if they are in a mill. */
public class RemoveMillTokenAction extends RemoveTokenAction implements Action {

  @Override
  public boolean isValid(Intersection selectedIntersection, Player player) {
    return (!selectedIntersection.isEmpty()
        && !(player.getTokenType() == selectedIntersection.peekToken().getTokenType()));
  }
}
