package gameengine;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/** Represents a single intersection on the GameBoard. */
public class Intersection extends JButton implements ActionListener {
    String currentState = null;
    Game currentGame;

    public Intersection(Game currentGame) {
        this.currentGame = currentGame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.currentGame.playTurn(this);
    }

    public void updateState(String imagePath) {
        this.setIcon(new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
    }

    public String getCurrentState() {
        return "null";
    }
}
