package actions;

import gameengine.GameBoardGui;
import gameengine.Intersection;
import gameplayers.Player;
import tokens.Token;
import tokens.TokenType;

import javax.swing.*;

/** Places a token on a given intersection. */
public class PlaceTokenAction extends Action {

  @Override
  public boolean execute(Intersection selectedIntersection, Player player) {
    if (!selectedIntersection.isEmpty()) {
      System.out.println("Intersection is occupied!");
      return false;
    } else {
      Token token = player.getTokenBank().getToken();
      selectedIntersection.setToken(token);
      player.incrementTokenCount();
      return true;
    }
  }
}
