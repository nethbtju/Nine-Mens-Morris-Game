package tokens;


/** A Player's initial source of Tokens while they are using the PLACE_TOKEN Capability. */
public class TokenBank extends TokenStack {

  /**
   * Constructor for a TokenBank.
   *
   * @param tokenType A string descriptor of the Token's type, which is shared with the Player to
   *     whom the Token belongs. Used to identify who a Token belongs to.
   * @param tokenImagePath The image path used to represent Tokens in this TokenBank. Note that this
   *     is a stopgap measure until dependency injection is introduced, at which point tokens can be
   *     initialised and passed to the TokenBank rather than
   */
  public TokenBank(TokenType tokenType, String tokenImagePath) {
    super(9);
    for (int i = 0; i < this.maximumTokens; i++) {
      Token token = new Token(tokenType, tokenImagePath);
      this.tokens.push(token);
    }
  }
}
