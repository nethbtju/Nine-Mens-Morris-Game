package gameengine;

import actions.Action;
import actions.MoveTokenAction;
import actions.PlaceTokenAction;
import gameplayers.Player;
import tokens.TokenBank;

import java.io.IOException;

public class Game {
    GameBoardGui gameBoard;
    boolean currentPlayer = true;

    Player tempPlayer;
    public Game() throws IOException {
        gameBoard = new GameBoardGui(this);
        gameBoard.createGui();
        tempPlayer = new Player("White", new TokenBank("White", "img/BoardImages/BlackTokenPlain.png"));
    }

    public void playTurn(Intersection selectedIntersection){
        //System.out.println("hello");
        Action currentAction = this.processPlayerAction(this.tempPlayer);
        currentAction.execute(selectedIntersection, tempPlayer);
        this.currentPlayer = !this.currentPlayer;


    }

    private Action processPlayerAction(Player currentPlayer){
        String currentCapability = currentPlayer.getCurrentCapability();
        if (currentCapability == "PLACE_TOKEN"){
            currentPlayer.incrementTokenCount();
            return new PlaceTokenAction();
        }
        else if(currentCapability == "MOVE_TOKEN"){
            System.out.println("Player can move!");
            return new MoveTokenAction();
        }
        return new MoveTokenAction();

    }

    public GameBoardGui getGameBoard(){
        return this.gameBoard;
    }

    public void initialisePlayers(){
        //create two players

    }


}
