import gameengine.Game;
import gameengine.LaunchScreenGui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/** Main class from which the application is run. */
public class Main {
  public static void main(String[] args) throws IOException {

    LaunchScreenGui game = new LaunchScreenGui();
    game.createGui();
    //Game theGame = new Game();
  }
}
