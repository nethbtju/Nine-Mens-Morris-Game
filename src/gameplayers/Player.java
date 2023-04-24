package gameplayers;

/**
 * Represents a player in the Nine Man's Morris game.
 */
public class Player {
    String tokenType;
    String tokenImagePath;
    int tokenCount = 0;
    String currentCapability;

    /**
     * Constructor for Players.
     *
     * @param tokenType A string descriptor of the colour of token that the player places.
     * @param tokenImagePath A path to the image used for the player's tokens.
     */
    public Player(String tokenType, String tokenImagePath) {
        this.tokenType = tokenType;
        this.tokenImagePath = tokenImagePath;
        this.currentCapability = "PLACE_TOKEN";
    }

    /**
     * Get the capability of the Player that determines what Actions it can take.
     * @return The capability of the player as a string.
     */
    public String getCurrentCapability() {
        return this.currentCapability;
    }

    /** Increment the number of tokens the player has on the board. */
    public void incrementTokenCount() {
        this.tokenCount += 1;
        if (this.tokenCount == 9) {
            this.currentCapability = "MOVE_TOKEN";
        }
    }

    /** Decrement the number of tokens the player has on the board. */
    public void decrementTokenCount() {
        this.tokenCount = this.tokenCount - 1;
        if (this.tokenCount  == 3 && this.currentCapability.equals("MOVE_TOKEN")) {
            this.currentCapability = "JUMP_TOKEN";
        }
    }

    /** Get the image path used for the Player's tokens. */
    public String getTokenImagePath() {
        return this.tokenImagePath;
    }
}
