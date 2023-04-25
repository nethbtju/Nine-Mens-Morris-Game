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

    /**
     * The main methods that executes each player's turn
     * @param selectedIntersection The intersection selected by the player
     */
    public void playTurn(Intersection selectedIntersection){
        Player currentPlayer = this.playerQueue.peek();
        Action currentAction = this.processPlayerAction(currentPlayer);
        currentAction.execute(selectedIntersection, currentPlayer);
        this.playerQueue.add(this.playerQueue.remove());

    }

    /**
     * Method that provides the current player with an appropriate action based on the current state of the game
     * @param currentPlayer The current player who initiated a move
     * @return
     */
    private Action processPlayerAction(Player currentPlayer){
        //Get player capabilities which reflects the current state of the game
        String currentCapability = currentPlayer.getCurrentCapability();

        //Provide either place token, move token or jump token action based on capability
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

    /**
     * Getter used to retrieve gameboardGUI
     * @return current gameboardGUI
     */
    public GameBoardGui getGameBoard(){
        return this.gameBoard;
    }

    /**
     * Method used to initialise the two players and their respective token type
     */
    public void initialisePlayers(){
        Player player1 = new Player("White", new TokenBank("White", "img/BoardImages/WhiteTokenPlain.png"));
        Player player2 = new Player("Black", new TokenBank("Black", "img/BoardImages/BlackTokenPlain.png"));
        this.playerQueue.add(player1);
        this.playerQueue.add(player2);
    }


}
