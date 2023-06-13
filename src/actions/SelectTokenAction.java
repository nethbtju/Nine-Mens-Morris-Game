package actions;

import gameengine.Intersection;
import gameplayers.Capable;
import gameplayers.GameState;
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
   * Checks if the intersection is valid to select.
   *
   * @param selectedIntersection The intersection selected by the user on the frontend.
   * @param player The player performing the action.
   * @return True if the Intersection is valid, false if not.
   */
  @Override
  public boolean isValid(Intersection selectedIntersection, Player player) {
    if (player.getCurrentGameState() == GameState.TUTORIAL
        && selectedIntersection.isTutorialLockedState()) {
      return false;
    }

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

  /**
   * Allows for the right action to be added to the intersection for the player clicking it by
   * seeing if it has legal moves.
   *
   * @param selectedIntersection - The intersection the player has selected
   * @param player - The player that has selected the intersection
   * @return Bool - Whether the intersection can set legal moves or not
   */
  private boolean setForRelevantAction(Intersection selectedIntersection, Player player) {
    boolean result;
    Capable currentCapability = player.getCurrentCapability();

    if (currentCapability == Capable.JUMPABLE) {
      result = selectedIntersection.setLegalJumpMoves();
    } else {
      result = selectedIntersection.setLegalMoves();
    }
    System.out.println(result);
    return result;
  }

  /**
   * Allows for the tokens to be highlighted to show the user certain indications.
   *
   * @param result Bool - if the intersection needs to be highlighted
   * @param selectedIntersection - The intersection that needs to be highlighted or not
   */
  private void highlightRelevantTokens(boolean result, Intersection selectedIntersection) {
    selectedIntersection.unhighlightTokens();
    if (result) {
      selectedIntersection.highlightSelectedTokenLegal();
    } else {
      selectedIntersection.highlightSelectedTokenIllegal();
    }
  }
}
