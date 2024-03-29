package tokens;

/** A Token that a Player can place on an Intersection on the GameBoard. */
public class Token {
  private final TokenType tokenType;
  private final String tokenImagePath;
  private final String selectedTokenImagePath;
  private final String selectedTokenIllegalImagePath;
  private final String millTokenImagePath;

  /**
   * Constructor for Tokens.
   *
   * @param tokenType A string descriptor of the type of Token, which identifies the Player to whom
   *     the token belongs.
   * @param tokenImagePath The filepath to the image used to represent the Token.
   */
  public Token(
      TokenType tokenType,
      String tokenImagePath,
      String selectedTokenImagePath,
      String selectedTokenIllegalImagePath,
      String millTokenImagePath) {
    this.tokenType = tokenType;
    this.tokenImagePath = tokenImagePath;
    this.selectedTokenImagePath = selectedTokenImagePath;
    this.selectedTokenIllegalImagePath = selectedTokenIllegalImagePath;
    this.millTokenImagePath = millTokenImagePath;
  }

  /**
   * Get the filepath to the image used to display the Token when it is selected.
   *
   * @return The token's selected image filepath.
   */
  public String getSelectedTokenImagePath() {
    return this.selectedTokenImagePath;
  }

  /**
   * Get the Token's tokenType.
   *
   * @return A string descriptor of the Token's type, which is used to identify the Player to whom
   *     the Token belongs.
   */
  public TokenType getTokenType() {
    return this.tokenType;
  }

  /**
   * Get the filepath to the image used to display the Token's 'illegal action' image variant.
   *
   * @return The token's illegal action image path.
   */
  public String getSelectedTokenIllegalImagePath() {
    return this.selectedTokenIllegalImagePath;
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
   * Get the filepath to the image used to display the Token's 'mill' image variant.
   *
   * @return The token's mill image path.
   */
  public String getMillTokenImagePath() {
    return this.millTokenImagePath;
  }
}
