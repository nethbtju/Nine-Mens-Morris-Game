package actions;

import gameengine.Intersection;
import gameplayers.Player;
import tokens.Token;

/** Moves a token from one intersection to another. */
public class MoveTokenAction extends PlaceTokenAction implements Action {

  @Override
  public void execute(Intersection tokenDestination, Player player) {
    System.out.println(player.getTokenType());
    System.out.println(player.getTokenCount());
    Token token = player.popTokenInHand();
    tokenDestination.setToken(token);
  }

  @Override
  public boolean isValid(Intersection selectedIntersection, Player player) {
    return selectedIntersection.isLegalMove();
  }
}
