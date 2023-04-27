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
  Game currentGame;

  /**
   * Constructor for an Intersection.
   *
   * @param currentGame The overarching Game in which the Intersection exists.
   */
  public Intersection(Game currentGame) {
    this.currentGame = currentGame;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.currentGame.playTurn(this);
  }

  /**
   * Get a Token from the Intersection.
   * 
   * @return Token if there is one, null if not.
   */
  public Token popToken() {
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

  public boolean isEmpty() {
    return this.tokenStack.isEmpty();
  }

  private void updateImagePath(String imagePath) {
    this.setIcon(
        new ImageIcon(
            new ImageIcon(imagePath).getImage().getScaledInstance(TOKEN_WIDTH, TOKEN_HEIGHT, Image.SCALE_SMOOTH)));
  }
}
