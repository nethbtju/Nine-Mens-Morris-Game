package gameengine;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Image;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GameBoardGUI extends JPanel {
    private final Image backgroundImage;
    private final int[] X = {208,208,208,265,265,265,320,320,320,375,542,430,487,542,375,375,430,430,375,375,375,542,487,487};
    private final int[] Y = {178,343,508,234,343,452,288,343,398,178,178,343,343,343,234,288,288,398,398,452,508,508,452,234};
    private final Game currentGame;

    /**
     * Constructor for the Gameboard, puts everything together.
     * @throws IOException
     */
    public GameBoardGUI(Game newGame) throws IOException {
        this.currentGame = newGame;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        String path = "img/BoardImages/board600pxls.png";
        backgroundImage = ImageIO.read(new File(path));
        ImageIcon boardImage = this.getScaledImage(path, 600, 600);
        JLabel boardImageLabel = new JLabel(boardImage);

        for (int i = 0; i < X.length; i++) {
            Intersection button = this.newButton(X[i],Y[i]);
            add(button);
        }
        this.setLayout(null);
    }

    public Intersection newButton(int x, int y){
        Intersection button = new Intersection(this.currentGame);
        //button.setBounds(0, 0, 10, 10);
        //button.setLayout(null);
        button.setLocation(x, y);
        button.setSize(50,50);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setIcon(new ImageIcon(new ImageIcon("").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        button.addActionListener(button);
        //button.setMargin(new Insets(top, left, bottom, right));
        return button;
    }

    /**
     * Built in class that draws the background of the gameboard
     * @param g - Graphic image that is to be background
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the background image.
        Image scaled = backgroundImage.getScaledInstance(600,600,Image.SCALE_DEFAULT);
        g.drawImage(scaled, 100, 100, this);
    }

    /**
     * Created the GUI by creating a new frame and adding the constraints
     * @throws IOException
     */
    public void createGUI() throws IOException {
        JFrame frame = new JFrame("Nine Men's Morris");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        //Create and set up the content pane.
        frame.getContentPane().add(new GameBoardGUI(this.currentGame));
        frame.setSize(800,800);

        // JComponent newContentPane = new Game.GameBoardGUI();
        // newContentPane.setOpaque(true); //content panes must be opaque
        //frame.add(newContentPane);

        //Display the window.
        //frame.pack();
        frame.setVisible(true);

        // created Button
    }

    /**
     * Allows to scale an image to whichever size is needed
     * @param path
     * @param w
     * @param h
     * @return
     */
    private ImageIcon getScaledImage(String path, int w, int h){
        ImageIcon imageIcon = new ImageIcon(path); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(w, h,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon = new ImageIcon(newimg);  // transform it back
        return imageIcon;
    }




    /**
     * Invoked when an action occurs.
     *
     */

    public void updateBlackTokenCount(){

    }

    public void updateWhiteTokenCount(){

    }


}
