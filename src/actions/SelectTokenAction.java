package actions;

import gameengine.Intersection;
import gameplayers.Capable;
import gameplayers.Player;
import tokens.Token;
import tokens.TokenType;

/** Selects a token for a Player to act upon. */
public class SelectTokenAction implements Action {

  /**
   * Lets player pick up the token they are about to move
   *
   * @param selectedIntersection The intersection selected by the user on the frontend.
   * @param player The player performing the Action.
   */
  @Override
  public void execute(Intersection selectedIntersection, Player player) {
    Token token = selectedIntersection.selectToken();
    player.setTokenInHand(token, selectedIntersection);
  }

  /**
   * Checks if the intersection is valid by seeing if it is empty
   *
   * @param selectedIntersection The intersection selected by the user on the frontend.
   * @param player The player performing the action.
   *
   * @return
   */
  @Override
  public boolean isValid(Intersection selectedIntersection, Player player) {
    player.returnToken();
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


    boolean result = this.setForRelevantAction(selectedIntersection, player);
    this.highlightRelevantTokens(result, selectedIntersection);
    return result;
  }

  private boolean setForRelevantAction(Intersection selectedIntersection, Player player){
    boolean result;
    Capable currentCapability = player.getCurrentCapability();

    if(currentCapability == Capable.JUMPABLE) {
      result = selectedIntersection.setLegalJumpMoves();
    }
    else {
      result = selectedIntersection.setLegalMoves();
    }
    System.out.println(result);
    return result;
  }

  private void highlightRelevantTokens(boolean result, Intersection selectedIntersection){
    selectedIntersection.unhighlightTokens();
    if (result) {
      selectedIntersection.highlightSelectedTokenLegal();
    } else {
      selectedIntersection.highlightSelectedTokenIllegal();
    }
  }


}
