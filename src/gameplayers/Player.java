package gameplayers;

import tokens.Token;
import tokens.TokenBank;
import tokens.TokenType;

/** Represents a player in the Nine Man's Morris game. */
public class Player {
  private final TokenType tokenType;
  private final TokenBank tokenBank;
  private int tokenCount = 9;
  private Token tokenInHand;

  /**
   * Constructor for Players.
   *
   * @param tokenType A string identifier of the Player's tokens, used to identify which Tokens
   *     belong to the Player.
   * @param tokenBank A TokenSource from which the Player can draw Tokens while using the
   */
  public Player(TokenType tokenType, TokenBank tokenBank) {
    this.tokenType = tokenType;
    this.tokenBank = tokenBank;
  }

  /**
   * Get the Player's tokenType.
   *
   * @return The Player's tokenType, a string descriptor which represents which Tokens are theirs.
   */
  public TokenType getTokenType() {
    return this.tokenType;
  }

  /**
   * Get the Player's TokenBank.
   *
   * @return The Player's TokenBank.
   */
  public TokenBank getTokenBank() {
    return this.tokenBank;
  }

  /**
   * Pop the Token the player has currently selected from their hand.
   *
   * @return The Token the player has selected.
   */
  public Token popTokenInHand() {
    if (this.tokenInHand != null) {
      Token token = this.tokenInHand;
      this.tokenInHand = null;
      return token;
    }
    return null;
  }

  public Token peekTokenInHand() {
    if (this.tokenInHand != null) {
      Token token = this.tokenInHand;
      return token;
    }
    return null;
  }

  /**
   * Check if the Player has selected a Token. Do not remove the Token from the Player.
   *
   * @return true if the Player has a Token, false if not.
   */
  public boolean hasTokenInHand() {
    return (this.tokenInHand != null);
  }

  /**
   * Set the Token the player currently has selected.
   *
   * @param token The Token that the player is selecting.
   */
  public void setTokenInHand(Token token) {
    this.tokenInHand = token;
  }

  /**
   * Get the capability of the Player that determines what Actions it can take.
   *
   * @return The capability of the player as a string.
   */
  public Capable getCurrentCapability() {
    if (!this.tokenBank.isEmpty()) {
      return Capable.PLACEABLE;
    } else if (this.tokenCount == 3 && this.tokenBank.isEmpty()) {
      return Capable.JUMPABLE;
    } else {
      return Capable.MOVEABLE;
    }
  }

  /** Increment the number of tokens the player has on the board. */
  public void incrementTokenCount() {
    this.tokenCount += 1;
  }

  /** Decrement the number of tokens the player has on the board. */
  public void decrementTokenCount() {
    this.tokenCount -= 1;
  }

  public int getTokenCount() {
    return this.tokenCount;
  }
}
