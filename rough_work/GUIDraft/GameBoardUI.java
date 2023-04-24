import javax.swing.*;
import java.awt.*;
import java.awt.Image;

public class GameBoardUI extends JPanel {
    private JPanel rootPanel;
    private JLabel BoardImage;
    private JLayeredPane layeredPane;

    public GameBoardUI() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        String path = "/img/BoardImages/board600pxls.png";
        final ImageIcon boardImage = createImageIcon(path);
        BoardImage = new JLabel();
        BoardImage.setIcon(boardImage);
        BoardImage.setVisible(true);
        /*layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(550, 600));
        layeredPane.setBorder(BorderFactory.createTitledBorder("New Game.Game"));
        layeredPane.add(BoardImage,0);
        add(layeredPane);*/
    }

    public static void createGUI(){
        JFrame frame = new JFrame("Nine Men's Morris");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Create and set up the content pane.
        JComponent newContentPane = new GameBoardUI();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    protected static ImageIcon createImageIcon(String path) {
        ImageIcon imgURL = new ImageIcon(path);
        if (imgURL != null) {
            return imgURL;
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createGUI();
            }
        });
    }
}
