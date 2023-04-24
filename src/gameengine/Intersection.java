package gameengine;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import tokens.Token;
import tokens.TokenSource;

/** Represents a single intersection on the GameBoard. */
public class Intersection extends JButton implements ActionListener, TokenSource {
  private Token token;
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

  @Override
  public Token getToken() {
    if (this.token != null) {
      Token token = this.token;
      this.token = null;
      this.updateImagePath(null);
      return token;
    }
    return null;
  }

  @Override
  public boolean isEmpty() {
    return (this.token == null);
  }

  /**
   * Set the Token on this Intersection.
   *
   * @param token The Token to place on the Intersection. If null, the Intersection becomes empty.
   */
  public void setToken(Token token) {
    this.token = token;
    this.updateImagePath(token.getTokenImagePath());
  }

  private void updateImagePath(String imagePath) {
    this.setIcon(new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
  }
}
