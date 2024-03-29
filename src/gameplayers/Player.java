package gameplayers;

import gameengine.Intersection;
import tokens.Token;
import tokens.TokenBank;
import tokens.TokenType;

/** Represents a player in the Nine Man's Morris game. */
public class Player {
  private final TokenType tokenType;
  private final TokenBank tokenBank;
  private int tokenCount = 0;
  private Token tokenInHand;

  private Intersection selectedIntersection;

  private GameState currentGameState;

  /**
   * Constructor for Players.
   *
   * @param tokenType A string identifier of the Player's tokens, used to identify which Tokens
   *     belong to the Player.
   * @param tokenBank A TokenSource from which the Player can draw Tokens while using the
   */
  public Player(TokenType tokenType, TokenBank tokenBank, GameState currentGamestate) {
    this.tokenType = tokenType;
    this.tokenBank = tokenBank;
    this.currentGameState = currentGamestate;
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
  public void setTokenInHand(Token token, Intersection selectedIntersection) {

    this.selectedIntersection = selectedIntersection;
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

  /**
   * Set the Player's TokenCount to a specific number. Used for game modes where the Player does has
   * Tokens on the board that were not placed using placeTokenActions, like in TutorialStates.
   */
  public void setTokenCount(int tokenCount) {
    this.tokenCount = tokenCount;
  }

  /** Decrement the number of tokens the player has on the board. */
  public void decrementTokenCount() {
    this.tokenCount -= 1;
  }

  /**
   * Getter method for the number of Tokens the Player has on the board.
   *
   * @return The Player's tokenCount.
   */
  public int getTokenCount() {
    return this.tokenCount;
  }

  public void returnToken() {
    System.out.println(1);

    if (this.hasTokenInHand()) {
      this.selectedIntersection.setToken(this.tokenInHand);
      this.tokenInHand = null;
      this.selectedIntersection = null;
    }
  }

  public GameState getCurrentGameState() {
    return this.currentGameState;
  }

  public void setTutorialState() {
    this.currentGameState = GameState.TUTORIAL;
  }

  public Intersection getSelectedIntersection() {
    return this.selectedIntersection;
  }
}
