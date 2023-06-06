import gameengine.Game;
import gameengine.LaunchScreenGui;

import java.io.IOException;

/** Main class from which the application is run. */
public class Main {
  public static void main(String[] args) throws IOException {
    LaunchScreenGui game = new LaunchScreenGui();
    game.createGui();
    //Game theGame = new Game();
  }
}
