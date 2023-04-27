package tokens;

import java.util.Stack;

/** An interface for objects from which Tokens can be obtained. */
public class TokenStack {
  protected int maximumTokens;
  protected Stack<Token> tokens = new Stack<>();

  public TokenStack(int maximumTokens) {
    this.maximumTokens = maximumTokens;
  }

  /**
   * Push a token to the TokenStack.
   *
   * @param token The Token being pushed.
   */
  public void pushToken(Token token) {
    if (this.tokens.size() <= this.maximumTokens) {
      this.tokens.push(token);
    }
  }

  /**
   * Pop a Token from the TokenStack.
   *
   * @return A Token if one exists, null if the TokenStack is empty.
   */
  public Token popToken() {
    if (!this.tokens.isEmpty()) {
      return this.tokens.pop();
    }
    return null;
  }

  /** Check if the TokenStack is full (and cannot contain any more Tokens). */
  public boolean isFull() {
    return (this.tokens.size() >= this.maximumTokens);
  }

  /**
   * Check if the TokenStack is empty (and cannot provide Tokens).
   *
   * @return true if empty, false if not.
   */
  public boolean isEmpty() {
    return this.tokens.isEmpty();
  }
}
