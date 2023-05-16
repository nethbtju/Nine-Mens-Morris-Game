package actions;

import gameengine.Intersection;
import gameplayers.Player;
import tokens.Token;

/** Moves a token from one intersection to another. */
public class MoveTokenAction extends PlaceTokenAction implements Action {

  @Override
  public void execute(Intersection tokenDestination, Player player) {
    Token token = player.popTokenInHand();
    tokenDestination.setToken(token);
  }

  public boolean isValid(Intersection selectedIntersection, Player player){
    System.out.println(selectedIntersection.isLegalMove());
    return selectedIntersection.isLegalMove() && selectedIntersection.isEmpty();
  }
}
