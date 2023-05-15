package gameengine;

import java.util.ArrayList;
import tokens.Token;
import tokens.TokenType;

/** An Observer that watches for the formation of Mills. */
public class MillObserver {
  private final ArrayList<Token> tokens = new ArrayList<>();

  /**
   * Check whether the MillObserver is currently observing a Mill of a particular TokenType.
   *
   * @param tokenType The TokenType of the Mill being checked for.
   * @return True if a mill exists, false if not.
   */
  public boolean checkForMill(TokenType tokenType) {
    int tokensOfGivenType = 0;
    for (Token token : tokens) {
      if (token.getTokenType() == tokenType) {
        tokensOfGivenType += 1;
      }
    }
    return (tokensOfGivenType == 3);
  }

  /**
   * Update the MillObserver's Token list by adding a Token.
   *
   * @param token The Token being added.
   */
  public void updateTokenAddition(Token token) {
    this.tokens.add(token);
  }

  /**
   * Update the MillObserver's Token list by removing a Token.
   *
   * @param token The Token being removed.
   */
  public void updateTokenRemoval(Token token) {
    this.tokens.remove(token);
  }
}
