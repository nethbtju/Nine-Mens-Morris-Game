package GameEngine;

import Actions.Action;
import Actions.MoveTokenAction;
import Actions.PlaceTokenAction;
import GamePlayers.Player;

import java.io.IOException;

public class Game {
    GameBoardGUI gameBoard;
    boolean currentPlayer = true;

    Player tempPlayer;
    public Game() throws IOException {
        gameBoard = new GameBoardGUI(this);
        gameBoard.createGUI();
        tempPlayer = new Player("White", "");
    }

    public void playTurn(Intersection selectedIntersection){
        //System.out.println("hello");
        Action currentAction = this.processPlayerAction(this.tempPlayer);
        currentAction.execute(selectedIntersection, this.currentPlayer);
        this.currentPlayer = !this.currentPlayer;


    }

    private Action processPlayerAction(Player currentPlayer){
        String currentCapability = currentPlayer.getCurrentCapability();
        if (currentCapability == "PLACE_TOKEN"){
            currentPlayer.incrementTokenCount();
            return new PlaceTokenAction(this, currentPlayer);
        }
        else if(currentCapability == "MOVE_TOKEN"){
            System.out.println("Player can move!");
            return new MoveTokenAction();
        }
        return new MoveTokenAction();

    }

    public GameBoardGUI getGameBoard(){
        return this.gameBoard;
    }

    public void initialisePlayers(){
        //create two players

    }


}
