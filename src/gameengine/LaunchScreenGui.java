package gameengine;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import gameengine.Game;
import gameengine.LaunchScreenGui;
import gameplayers.GameState;

import java.io.IOException;

public class LaunchScreenGui extends JPanel implements ActionListener {
    private Image backgroundImage;

    /**
     * Launch the GUI game screen when the application is launched
     *
     * @throws IOException if the image path could not return the background image
     */
    public LaunchScreenGui() throws IOException {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        String path = "/resources/META-INF/img/BoardImages/LaunchScreen.png";
        backgroundImage = ImageIO.read(getClass().getResource(path));
        add(newButton(300, 425, GameState.NORMAL));
        add(newButton(300, 500, GameState.TUTORIAL));
        this.setLayout(null);
    }

    /**
     * Paint the GUI components on the background
     *
     * @param g - the graphics
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the background image.
        Image scaled = backgroundImage.getScaledInstance(600, 600, Image.SCALE_DEFAULT);
        g.drawImage(scaled, 100, 100, this);
    }

    /** Create the GUI by creating a new frame and adding the constraints. */
    public void createGui() {
        JFrame frame = new JFrame("Nine Men's Morris");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // Create and set up the content pane.
        frame.getContentPane().add(this);
        frame.setSize(800, 800);

        // Display the window.
        frame.setVisible(true);
    }

    /**
     * Setting up the main screen buttons
     *
     * @param x - x coordinate of the button
     * @param y - y coordinate of the button
     * @param newGameState - Which game state the button leads to
     * @return the new button
     */
    public JButton newButton(int x, int y, GameState newGameState) {
        JButton button = new JButton();
        button.setLocation(x, y);
        button.setSize(200, 60);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        Image image = null;
        String path = "/resources/META-INF/img/BoardImages/Empty.png";
        try {

            image = ImageIO.read(getClass().getResource(path));
            button.setIcon(
                    new ImageIcon(image));
        } catch (IOException e) {
            e.printStackTrace();
        }
        button.addActionListener(e -> this.initialiseGame(e, newGameState));
       return button;
    }

    // TODO: get rid of this?
    public JButton playTutorialButton() {
        JButton button = new JButton();
        button.setLocation(300, 500);
        button.setSize(200, 60);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        Image image = null;
        String path = "/resources/META-INF/img/BoardImages/BlackTokenIllegal.png";
        try {

            image = ImageIO.read(getClass().getResource(path));
            button.setIcon(
                    new ImageIcon(image));
        } catch (IOException e) {
            e.printStackTrace();
        }
        button.addActionListener(e -> this.initialiseGame(e, GameState.NORMAL));
        return button;
    }

    /**
     * Start the game by creating a new instance of the Game class
     *
     * @param gameState - the game state that is executed
     */
    public void startGame(GameState gameState){
        try {
            Game theGame = new Game(gameState);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("bob");
        //
    }

    /**
     * If the user clicks the start game button, the game is initialised as normal
     *
     * @param e - the action performed with the button
     * @param newGameState - The new game state to be executed
     */
    private void initialiseGame(ActionEvent e, GameState newGameState){
        JComponent comp = (JComponent) e.getSource();
        Window win = SwingUtilities.getWindowAncestor(comp);
        win.dispose();
        this.startGame(newGameState);}
}
