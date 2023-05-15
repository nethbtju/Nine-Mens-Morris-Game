package actions;

import gameengine.Intersection;
import gameplayers.Player;
import tokens.Token;

/** Moves a token from one intersection to another. */
public class MoveTokenAction implements Action {
  @Override
  public boolean execute(Intersection tokenDestination, Player player) {
    if (!tokenDestination.isEmpty() || tokenDestination.isLegalMove()) {
      System.out.println("Intersection is occupied or illegal move!");
      return false;
    } else {
      Token token = player.popTokenInHand();
      tokenDestination.setToken(token);
      return true;
    }
  }
}
