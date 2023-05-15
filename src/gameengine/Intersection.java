package gameengine;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import tokens.Token;
import tokens.TokenStack;

/** Represents a single intersection on the GameBoard. */
public class Intersection extends JButton implements ActionListener {
  private final int TOKEN_HEIGHT = 50;
  private final int TOKEN_WIDTH = 50;
  private final TokenStack tokenStack = new TokenStack(1);
  private final int[] coordinates;
  private final Game currentGame;
  private boolean legalMoveState = true;

  /**
   * Constructor for an Intersection.
   *
   * @param currentGame The overarching Game in which the Intersection exists.
   */
  public Intersection(Game currentGame, int[] coordinates) {
    this.currentGame = currentGame;
    this.coordinates = coordinates;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.currentGame.playTurn(this);
  }

  /**
   * Highlight a selected Intersection and remove its Token.
   *
   * @return The Token on the Intersection if it exists, null if there is no Token.
   */
  public Token selectToken() {
    Token token = this.tokenStack.popToken();
    if (token != null) {
      this.updateImagePath(token.getSelectedTokenImagePath());
    } else {
      this.updateImagePath(null);
    }
    return token;
  }

  /** Reset the image of an Intersection from any highlighting back to its normal appearance. */
  public void resetIntersectionImage() {
    Token token = this.tokenStack.peekToken();
    if (token != null) {
      this.updateImagePath(token.getTokenImagePath());
    } else {
      this.updateImagePath(null);
    }
  }

  /**
   * Get the Token on the Intersection without removing it.
   *
   * @return The Token on the Intersection if it exists, null if there is no Token.
   */
  public Token peekToken() {
    return this.tokenStack.peekToken();
  }

  /**
   * Get a Token from the Intersection.
   *
   * @return Token if there is one, null if not.
   */
  public Token removeToken() {
    Token token = this.tokenStack.popToken();
    this.updateImagePath(null);
    return token;
  }

  /**
   * Set the Token on this Intersection.
   *
   * @param token The Token to place on the Intersection. If null, the Intersection becomes empty.
   */
  public void setToken(Token token) {
    this.tokenStack.pushToken(token);
    this.updateImagePath(token.getTokenImagePath());
  }

  /**
   * Checks whether the Intersection does not have a Token.
   *
   * @return True if there is no Token, false if there is a Token.
   */
  public boolean isEmpty() {
    return this.tokenStack.isEmpty();
  }

  private void updateImagePath(String imagePath) {
    this.setIcon(
        new ImageIcon(
            new ImageIcon(imagePath)
                .getImage()
                .getScaledInstance(TOKEN_WIDTH, TOKEN_HEIGHT, Image.SCALE_SMOOTH)));
  }

  public boolean isLegalMove() {
    return this.legalMoveState;
  }

  public void setLegalMoveState() {
    this.legalMoveState = true;
  }

  public void setIllegalMoveState() {
    this.legalMoveState = false;
  }

  public int[] getCoordinates() {
    return this.coordinates;
  }

  public String getKey() {
    String intersectionKey = String.valueOf(this.coordinates[0]) + this.coordinates[1];
    return intersectionKey;
  }
}
