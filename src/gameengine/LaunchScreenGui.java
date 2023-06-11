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

    public LaunchScreenGui() throws IOException {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        String path = "/resources/META-INF/img/BoardImages/LaunchScreen.png";
        backgroundImage = ImageIO.read(getClass().getResource(path));
        add(newButton(300, 425, GameState.NORMAL));
        add(newButton(300, 500, GameState.TUTORIAL));
        this.setLayout(null);
    }

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

    private void initialiseGame(ActionEvent e, GameState newGameState){
        JComponent comp = (JComponent) e.getSource();
        Window win = SwingUtilities.getWindowAncestor(comp);
        win.dispose();
        this.startGame(newGameState);}
}
