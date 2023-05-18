package actions;

import gameengine.Intersection;
import gameplayers.Player;

/** An Action that removes Tokens from the GameBoard, awarded for forming mills. */
public class RemoveTokenAction implements Action {

  @Override
  public void execute(Intersection selectedIntersection, Player player) {
    selectedIntersection.removeToken();
    selectedIntersection.removeAttackedPlayerToken();
  }
  
  @Override
  public boolean isValid(Intersection selectedIntersection, Player player) {
    return !selectedIntersection.getMillState()
        && !selectedIntersection.isEmpty()
        && selectedIntersection.peekToken().getTokenType() != player.getTokenType();
  }
}
