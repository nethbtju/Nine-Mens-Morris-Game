package actions;

import gameengine.Intersection;
import gameplayers.GameState;
import gameplayers.Player;
import tokens.Token;

import java.util.Arrays;
import java.util.Objects;

/** Moves a token from one intersection to another. */
public class MoveTokenAction extends PlaceTokenAction implements Action {

  /**
   * Move the token from intersection to intersection
   *
   * @param tokenDestination - Intersection that the token is to be moved to
   * @param player The player performing the Action.
   */
  @Override
  public void execute(Intersection tokenDestination, Player player) {
    System.out.println("Ive been placed");
    System.out.println(player.getTokenType());
    System.out.println(player.getTokenCount());
    Token token = player.popTokenInHand();
    tokenDestination.setToken(token);
  }

  /**
   * Checks if the selected intersection is valid to be moved to
   *
   * @param selectedIntersection The intersection selected by the user on the frontend.
   * @param player The player performing the action.
   *
   * @return True if the Intersection is valid to move to.
   */
  @Override
  public boolean isValid(Intersection selectedIntersection, Player player) {
    System.out.println(Arrays.toString(player.getSelectedIntersection().getCoordinates()));
    System.out.println(Arrays.toString(selectedIntersection.getCoordinates()));
    System.out.println(
        Objects.equals(player.getSelectedIntersection().getKey(), selectedIntersection.getKey()));
    if (player.getCurrentGameState() == GameState.TUTORIAL) {
      return !(Objects.equals(
              player.getSelectedIntersection().getKey(), selectedIntersection.getKey()))
          && !selectedIntersection.isTutorialLockedState();
    } else {

      return selectedIntersection.isLegalMove();
    }
  }
}
