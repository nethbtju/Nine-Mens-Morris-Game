package actions;

import gameengine.Intersection;
import gameplayers.Player;
import tokens.Token;

/** Places a token on a given intersection. */
public class PlaceTokenAction implements Action {

  /**
   * Gets the player token bank and pops off a token to place at an intersection
   *
   * @param selectedIntersection The intersection selected by the user on the frontend.
   * @param player The player performing the Action.
   */
  @Override
  public void execute(Intersection selectedIntersection, Player player) {

    Token token = player.getTokenBank().popToken();
    selectedIntersection.setToken(token);
    player.incrementTokenCount();
  }

  /**
   * checks if the intersection already has a token on it
   *
   * @param selectedIntersection The intersection selected by the user on the frontend.
   * @param player The player performing the action.
   *
   * @return
   */
  @Override
  public boolean isValid(Intersection selectedIntersection, Player player) {
    if (!selectedIntersection.isEmpty()) {
      System.out.println("Intersection is occupied!");
      return false;
    }
    return true;
  }
}
