package tokens;

/**
 * An interface for objects from which Tokens can be obtained.
 */
public interface TokenSource {

  /**
   * Get a Token from the TokenSource.
   *
   * @return A Token.
   */
  Token getToken();

  /**
   * Check if the TokenSource is empty (and cannot provide Tokens).
   *
   * @return true if empty, false if not.
   */
  boolean isEmpty();
}
