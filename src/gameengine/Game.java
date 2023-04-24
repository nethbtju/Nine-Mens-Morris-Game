package gameengine;

import actions.Action;
import actions.MoveTokenAction;
import actions.PlaceTokenAction;
import gameplayers.Player;
import java.io.IOException;

/** An instance of a Nine Man's Morris game. Highest level class in the game architecture. */
public class Game {
    GameBoardGui gameBoard;
    boolean currentPlayer = true;
    Player tempPlayer;

    /**
     * Constructor for Game.
     *
     * @throws IOException Excepts if GameBoard fails to initialise due to missing background image.
     */
    public Game() throws IOException {
        gameBoard = new GameBoardGui(this);
        gameBoard.createGui();
        tempPlayer = new Player("White", "");
    }

    /**
     * Play a turn for the current player.
     *
     * @param selectedIntersection The intersection selected by the user on the frontend.
     */
    public void playTurn(Intersection selectedIntersection) {
        Action currentAction = this.processPlayerAction(this.tempPlayer);
        currentAction.execute(selectedIntersection, this.currentPlayer);
        this.currentPlayer = !this.currentPlayer;
    }

    private Action processPlayerAction(Player currentPlayer) {
        String currentCapability = currentPlayer.getCurrentCapability();
        if (currentCapability.equals("PLACE_TOKEN")) {
            currentPlayer.incrementTokenCount();
            return new PlaceTokenAction(this, currentPlayer);
        } else if (currentCapability.equals("MOVE_TOKEN")) {
            System.out.println("Player can move!");
            return new MoveTokenAction();
        }
        return new MoveTokenAction();
    }

    public GameBoardGui getGameBoard() {
        return this.gameBoard;
    }

    public void initialisePlayers() {
        // create two players
    }
}
