package gameengine;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/** Represents a single intersection on the GameBoard. */
public class Intersection extends JButton implements ActionListener {
    private boolean occupied = false;
    Game currentGame;

    /**
     * Constructor for an Intersection.
     *
     * @param currentGame The overarching Game in which the Intersection exists.
     */
    public Intersection(Game currentGame) {
        this.currentGame = currentGame;
    }

    /**
     * Listens for button activations on the Intersection.
     *
     * @param e the event to be processed (A button activation).
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.currentGame.playTurn(this);
    }

    /**
     * Update the icon of the Intersection.
     *
     * @param imagePath The filepath of the icon to be used.
     */
    public void updateState(String imagePath) {
        this.setIcon(new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
    }

    /** Set the Intersection as occupied, preventing the placement of another token. */
    public void setOccupied() {
        this.occupied = true;
    }

    /** Check if the Intersection is occupied. */
    public boolean isOccupied() {
        return this.occupied;
    }
}
