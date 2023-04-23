import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Image;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class GameBoardGUI extends JPanel implements ActionListener {
    private Image backgroundImage;
    int[] x = {70,103,103};
    int[] y = {105,105,105};

    /**
     * Constructor for the Gameboard, puts everything together.
     * @throws IOException
     */
    public GameBoardGUI() throws IOException {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        String path = "img/BoardImages/board600pxls.png";
        backgroundImage = ImageIO.read(new File(path));
        ImageIcon boardImage = this.getScaledImage(path, 600, 600);
        JLabel boardImageLabel = new JLabel(boardImage);

        JButton button00 = this.newButton(70,105,0,0);
        JButton button01 = this.newButton(103,105,0,0);
        JButton button02 = this.newButton(103,105,0,0);
        button00.setIcon(new ImageIcon(new ImageIcon("img/BoardImages/dotSelected.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        button00.addActionListener(this);
        button01.setIcon(new ImageIcon(new ImageIcon("img/BoardImages/dot.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        button01.addActionListener(this);
        button02.setIcon(new ImageIcon(new ImageIcon("img/BoardImages/dotSelected.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        button02.addActionListener(this);
        add(button00,0,0);
        add(button01,0,1);
        add(button02,0,2);

    }

    /**
     * newButton method creates a new button using certain constraints
     * on the margins around the button allowing it to be placed anywhere on the frame
     * @param top - integer to determine the distance from the top
     * @param left - integer to determine the distance from the left
     * @param bottom - integer to determine the distance from the bottom
     * @param right - integer to determine the distance from the right
     * @return button - a button with the following contraints added to it to fit the token needs
     */
    public JButton newButton(int top, int left,int bottom, int right){
        JButton button = new JButton();
        button.setBounds(10, 10, 10, 10);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setMargin(new Insets(top, left, bottom, right));
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
        g.drawImage(scaled, 0, 0, this);
    }

    /**
     * Created the GUI by creating a new frame and adding the constraints
     * @throws IOException
     */
    public static void createGUI() throws IOException {
        JFrame frame = new JFrame("Nine Men's Morris");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setResizable(false);

        //Create and set up the content pane.
        frame.getContentPane().add(new GameBoardGUI());
        frame.setSize(600,600);

        // JComponent newContentPane = new GameBoardGUI();
        // newContentPane.setOpaque(true); //content panes must be opaque
        //frame.add(newContentPane);

        //Display the window.
        frame.pack();
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


    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    createGUI();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JButton){
            System.out.println("(0,0)");
            ((JButton) e.getSource()).setIcon(new ImageIcon(new ImageIcon("img/BoardImages/WhiteTokenPlain.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        }
    }
}

