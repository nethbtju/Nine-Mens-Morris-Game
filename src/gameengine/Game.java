package gameengine;

import actions.*;
import gameplayers.Player;
import tokens.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Game {

    GameBoardGUI gameBoard;

    Queue<Player> playerQueue = new LinkedList<>();


    /**
     * Constructor for the Game, initialises game backend
     * @throws IOException
     */
    public Game() throws IOException {
        gameBoard = new GameBoardGUI(this);
        gameBoard.createGUI();
        this.initialisePlayers();
    }

    public void playTurn(Intersection selectedIntersection){
        Player currentPlayer = this.playerQueue.peek();
        //System.out.println("hello");
        Action currentAction = this.processPlayerAction(currentPlayer);
        currentAction.execute(selectedIntersection, currentPlayer);
        this.playerQueue.add(this.playerQueue.remove());

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

    public GameBoardGUI getGameBoard(){
        return this.gameBoard;
    }

    public void initialisePlayers(){
        Player player1 = new Player("White", new TokenBank(TokenType.WHITE, "img/BoardImages/WhiteTokenPlain.png"));
        Player player2 = new Player("Black", new TokenBank(TokenType.BLACK, "img/BoardImages/BlackTokenPlain.png"));
        this.playerQueue.add(player1);
        this.playerQueue.add(player2);
    }
}
