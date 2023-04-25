package gameengine;

import actions.*;
import gameplayers.Player;
import tokens.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Game {

    GameBoardGui gameBoard;

    Queue<Player> playerQueue = new LinkedList<>();


    /**
     * Constructor for the Game, initialises game backend
     * @throws IOException
     */
    public Game() throws IOException {
        gameBoard = new GameBoardGui(this);
        gameBoard.createGUI();
        this.initialisePlayers();
        this.updatePlayTurnDisplay();
    }

    public void playTurn(Intersection selectedIntersection){
        Player currentPlayer = this.playerQueue.peek();

        Action currentAction = this.processPlayerAction(currentPlayer);
        boolean turncomplete = currentAction.execute(selectedIntersection, currentPlayer);
        if(turncomplete) {
            this.playerQueue.add(this.playerQueue.remove());
        }

        this.updatePlayTurnDisplay();

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
        Player player1 = new Player("White", new TokenBank(TokenType.WHITE, "img/BoardImages/WhiteTokenPlain.png"));
        Player player2 = new Player("Black", new TokenBank(TokenType.BLACK, "img/BoardImages/BlackTokenPlain.png"));
        this.playerQueue.add(player1);
        this.playerQueue.add(player2);
    }


    public void updatePlayTurnDisplay(){
        Player currentPlayer = playerQueue.peek();
        String playerType = currentPlayer.getTokenType();

        if(playerType == "White"){
            System.out.println("Player 1");
            this.gameBoard.updatePlayerTurnDisplay("Player 1 Turn!");
        }
        else{
            System.out.println("Player 2");
            this.gameBoard.updatePlayerTurnDisplay("Player 2 Turn!");
        }
    }
}
