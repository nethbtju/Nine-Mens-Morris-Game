package actions;

import gameengine.Intersection;
import gameplayers.Player;
import tokens.Token;

/** Places a token on a given intersection. */
public class PlaceTokenAction implements Action {

  @Override
  public void execute(Intersection selectedIntersection, Player player) {
    Token token = player.getTokenBank().popToken();
    selectedIntersection.setToken(token);
    player.incrementTokenCount();
  }

  @Override
  public boolean isValid(Intersection selectedIntersection, Player player) {
    if (!selectedIntersection.isEmpty()) {
      System.out.println("Intersection is occupied!");
      return false;
    }
    return true;
  }
}
