package gameplayers;

public class Player {
    String tokenType;
    String tokenImagePath;
    int tokenCount = 0;
    String currentCapability;
    public Player(String tokenType, String tokenImagePath){
        this.tokenType = tokenType;
        this.tokenImagePath = tokenImagePath;
        this.currentCapability = "PLACE_TOKEN";
    }

    public String getCurrentCapability(){
        //get player capability
        return this.currentCapability;
    }

    public void incrementTokenCount(){
        this.tokenCount = this.tokenCount + 1;
        //if token count is nine, give place move capability
        if (this.tokenCount == 9){
            this.currentCapability = "MOVE_TOKEN";
        }
    }

    public void decrementTokenCount(){
        this.tokenCount = this.tokenCount - 1;
        //if token count == 3 and current capability is move, give player jump
        if(this.tokenCount  == 3 && this.currentCapability == "MOVE_TOKE"){
            this.currentCapability = "JUMP_TOKEN";
        }
    }

    public String getTokenPath(){
        return this.tokenImagePath;
    }
}
