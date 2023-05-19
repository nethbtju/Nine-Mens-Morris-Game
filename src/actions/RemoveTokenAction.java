package actions;

import gameengine.Intersection;
import gameplayers.Player;

/** An Action that removes Tokens from the GameBoard, awarded for forming mills. */
public class RemoveTokenAction implements Action {

  /**
   * Removes the token of the opponents
   *
   * @param selectedIntersection The intersection selected by the user on the frontend.
   * @param player The player performing the Action.
   */
  @Override
  public void execute(Intersection selectedIntersection, Player player) {
    selectedIntersection.removeToken();
    selectedIntersection.removeAttackedPlayerToken();
    selectedIntersection.unhighlightTokens();
  }

  /**
   * Checks if the intersection selected has a token of the right type before allowing removal
   *
   * @param selectedIntersection The intersection selected by the user on the frontend.
   * @param player The player performing the action.
   *
   * @return
   */
  @Override
  public boolean isValid(Intersection selectedIntersection, Player player) {
    return !selectedIntersection.getMillState()
        && !selectedIntersection.isEmpty()
        && selectedIntersection.peekToken().getTokenType() != player.getTokenType();
  }
}
