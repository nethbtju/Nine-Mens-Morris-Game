package gameengine;

import actions.Action;
import actions.MoveTokenAction;
import actions.PlaceTokenAction;
import gameplayers.Player;
import tokens.Token;
import tokens.TokenBank;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Game {

    GameBoardGui gameBoard;

    Queue<Player> playerQueue = new LinkedList<>();

    Player tempPlayer;

    /**
     * Constructor for the Game, initialises game backend
     * @throws IOException
     */
    public Game() throws IOException {
        gameBoard = new GameBoardGui(this);
        gameBoard.createGui();
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

    public GameBoardGui getGameBoard(){
        return this.gameBoard;
    }

    public void initialisePlayers(){
        Player player1 = new Player("White", new TokenBank("White", "img/BoardImages/WhiteTokenPlain.png"));
        Player player2 = new Player("Black", new TokenBank("White", "img/BoardImages/BlackTokenPlain.png"));
        this.playerQueue.add(player1);
        this.playerQueue.add(player2);
    }


}
