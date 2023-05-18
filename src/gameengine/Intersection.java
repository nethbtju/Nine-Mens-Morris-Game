package gameengine;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import tokens.Token;
import tokens.TokenStack;

/** Represents a single intersection on the GameBoard. */
public class Intersection extends JButton implements ActionListener {
  private final int TOKEN_HEIGHT = 50;
  private final int TOKEN_WIDTH = 50;
  private final TokenStack tokenStack = new TokenStack(1);
  private final ArrayList<MillObserver> millObservers = new ArrayList<>();
  private final int[] coordinates;
  private final Game currentGame;
  private boolean legalMoveState = false;
  private boolean millState = false;

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

  /** Update the mill state of the Intersection, which tracks whether it has a mill. */
  public void setMillState() {
    boolean millFound = false;
    for (MillObserver millObserver : this.millObservers) {
      if (millObserver.hasMill()) {
        millFound = true;
      }
    }
    this.millState = millFound;
  }

  /**
   * Get the mill state of the Intersection.
   *
   * @return True if there is a Mill on the Intersection, false if not.
   */
  public boolean getMillState() {
    return this.millState;
  }

  /** Set the image of the Intersection to display a legally selected token. */
  public void highlightSelectedTokenLegal() {
    Token token = this.peekToken();
    this.updateImagePath(token.getSelectedTokenImagePath());
  }

  /** Set the image of the Intersection to display an illegally selected token. */
  public void highlightSelectedTokenIllegal() {
    Token token = this.peekToken();
    this.updateImagePath(token.getSelectedTokenIllegalImagePath());
  }

  /** Set the image of the Intersection to display a Token in a mill. */
  public void highlightMill() {
    Token token = this.peekToken();
    this.updateImagePath(token.getMillTokenImagePath());
  }

  /** Set the image of the Intersection to display an empty and accessible intersection. */
  public void highlightAsOpen() {
    this.updateImagePath("img/BoardImages/dotSelected.png");
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
   * Highlight a selected Intersection and remove its Token.
   *
   * @return The Token on the Intersection if it exists, null if there is no Token.
   */
  public Token selectToken() {
    Token token = this.tokenStack.popToken();
    this.updateImagePath(token.getSelectedTokenImagePath());
    for (MillObserver millObserver : this.millObservers) {
      millObserver.updateTokenRemoval(token);
    }
    return token;
  }

  /** Remove a Token from the Intersection. */
  public void removeToken() {
    Token token = this.tokenStack.popToken();
    for (MillObserver millObserver : this.millObservers) {
      millObserver.updateTokenRemoval(token);
    }
    this.updateImagePath(null);
  }

  /**
   * Set the Token on this Intersection.
   *
   * @param token The Token to place on the Intersection. If null, the Intersection becomes empty.
   */
  public void setToken(Token token) {
    this.tokenStack.pushToken(token);
    for (MillObserver millObserver : this.millObservers) {
      millObserver.updateTokenAddition(token);
    }
    this.updateImagePath(token.getTokenImagePath());

    this.unhighlightTokens();
  }

  public void unhighlightTokens() {
    this.currentGame.getGameBoard().unhighlightAllIntersections();
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

  /**
   * Attach a MillObserver to the Intersection that watches to see if a Mill is formed on it.
   *
   * @param millObserver The MillObserver being attached.
   */
  public void attachObserver(MillObserver millObserver) {
    this.millObservers.add(millObserver);
  }

  /**
   * Checks whether the intersection is legal to act upon with an Action.
   *
   * @return True if legal, false if not.
   */
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

  public boolean setLegalMoves() {
    return this.currentGame.getGameBoard().setLegalIntersections(this);
  }

  public boolean setLegalJumpMoves() {
    return this.currentGame.getGameBoard().setLegalJumpIntersections();
  }

  public void removeAttackedPlayerToken(){
    this.currentGame.decrementOpposingPlayerTokenCount();
  }

}
