package GameEngine;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Image;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GameBoardGUI extends JPanel {
    private Image backgroundImage;

    private Game currentGame;

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

        Intersection button00 = this.newButton(208,178);
        Intersection button01 = this.newButton(208,343);
        Intersection button02 = this.newButton(208,508);
        Intersection button03 = this.newButton(375,178);
        Intersection button04 = this.newButton(542,178);

        button00.setIcon(new ImageIcon(new ImageIcon("").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        button00.addActionListener(button00);
        button01.setIcon(new ImageIcon(new ImageIcon("").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        button01.addActionListener(button01);
        button02.setIcon(new ImageIcon(new ImageIcon("").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        button02.addActionListener(button02);
        button03.setIcon(new ImageIcon(new ImageIcon("").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        button03.addActionListener(button03);
        button04.setIcon(new ImageIcon(new ImageIcon("").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        button04.addActionListener(button04);

        this.setLayout(null);
        add(button00,0,0);
        add(button01,0,1);
        add(button02,0,2);
        add(button03,0,3);
        add(button04,0,4);


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
     * @param e the event to be processed
     */

    public void updateBlackTokenCount(){

    }

    public void updateWhiteTokenCount(){

    }


}

