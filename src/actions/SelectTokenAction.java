package actions;

import gameengine.Intersection;
import gameplayers.Capable;
import gameplayers.Player;
import tokens.Token;
import tokens.TokenType;

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

    boolean result = this.setForRelevantAction(selectedIntersection, player);
    this.highlightRelevantTokens(result, selectedIntersection);
    return result;
  }

  private boolean setForRelevantAction(Intersection selectedIntersection, Player player){
    if(player.getTokenType() == TokenType.WHITE) {
      System.out.println(player.getTokenCount());
    }
    boolean result;
    Capable currentCapability = player.getCurrentCapability();
    if(currentCapability == Capable.JUMPABLE) {
      System.out.println("rabbitusin");
      result = selectedIntersection.setLegalJumpMoves();
    }
    else {
      result = selectedIntersection.setLegalMoves();
    }
    return result;
  }

  private void highlightRelevantTokens(boolean result, Intersection selectedIntersection){
    if (result) {
      selectedIntersection.highlightSelectedTokenLegal();
    } else {
      selectedIntersection.highlightSelectedTokenIllegal();
    }
  }


}
