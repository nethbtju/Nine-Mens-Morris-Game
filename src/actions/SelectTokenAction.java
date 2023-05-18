package actions;

import gameengine.Intersection;
import gameplayers.Player;
import tokens.Token;

/** Selects a token for a Player to act upon. */
public class SelectTokenAction implements Action {
  @Override
  public void execute(Intersection selectedIntersection, Player player) {
    Token token = selectedIntersection.selectToken();
    player.setTokenInHand(token);
  }

  @Override
  public boolean isValid(Intersection selectedIntersection, Player player) {

    if (selectedIntersection.isEmpty()) {
      System.out.println("Intersection is empty!");
      return false;
    } else {
      Token token = selectedIntersection.peekToken();
      if (!token.getTokenType().equals(player.getTokenType())) {
        System.out.println("Token does not belong to player!");
        return false;
      }
    }
    selectedIntersection.unhighlightTokens();
    boolean result = selectedIntersection.setLegalMoves();

    if (result) {
      selectedIntersection.highlightSelectedTokenLegal();
    } else {
      selectedIntersection.highlightSelectedTokenIllegal();
    }
    return result;
  }
}
