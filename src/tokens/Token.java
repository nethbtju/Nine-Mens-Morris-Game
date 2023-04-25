package tokens;

/** A Token that a Player can place on an Intersection on the GameBoard. */
public class Token {
  private final TokenType tokenType;
  private final String tokenImagePath;

  /**
   * Constructor for Tokens.
   *  @param tokenType A string descriptor of the type of Token, which identifies the Player to whom
   *                  the token belongs.
   * @param tokenImagePath The filepath to the image used to represent the Token.
   */
  public Token(TokenType tokenType, String tokenImagePath) {
    this.tokenType = tokenType;
    this.tokenImagePath = tokenImagePath;
  }

  /**
   * Get the filepath to the image used to display the Token.
   *
   * @return The token's image filepath.
   */
  public String getTokenImagePath() {
    return this.tokenImagePath;
  }

  /**
   * Get the Token's tokenType.
   *
   * @return A string descriptor of the Token's type, which is used to identify the Player to whom
   *         the Token belongs.
   */
  public TokenType getTokenType() {
    return this.tokenType;
  }
}
