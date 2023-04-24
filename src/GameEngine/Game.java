package GameEngine;

import Actions.Action;
import Actions.PlaceTokenAction;
import GamePlayers.Player;

import java.io.IOException;

public class Game {
    GameBoardGUI gameBoard;
    boolean currentPlayer = true;
    public Game() throws IOException {
        gameBoard = new GameBoardGUI(this);
        gameBoard.createGUI();
    }

    public void playTurn(Intersection selectedIntersection){
        System.out.println("hello");
        Action currentAction = new PlaceTokenAction(this, new Player());
        currentAction.execute(selectedIntersection, this.currentPlayer);
        this.currentPlayer = !this.currentPlayer;
    }

    private void processPlayerAction(){

    }

    public GameBoardGUI getGameBoard(){
        return this.gameBoard;
    }


}
