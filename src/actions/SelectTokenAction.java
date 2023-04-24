package actions;

import gameengine.Intersection;
import gameplayers.Player;
import tokens.Token;

/** Selects a token for a Player to act upon. */
public class SelectTokenAction extends Action {
  @Override
  public boolean execute(Intersection selectedIntersection, Player player) {
    if (selectedIntersection.isEmpty()) {
      System.out.println("Intersection is empty!");
      return false;
    } else {
      Token token = selectedIntersection.getToken();
      if (!token.getTokenType().equals(player.getTokenType())) {
        selectedIntersection.setToken((token));
        System.out.println("Token does not belong to player!");
        return false;
      }
      player.setTokenInHand(token);
      return true;
    }
  }
}
