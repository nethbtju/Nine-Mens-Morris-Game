import javax.swing.*;
import java.awt.*;
import java.awt.Image;

public class GameBoardUI extends JPanel {
    private JPanel rootPanel;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JRadioButton radioButton7;
    private JRadioButton radioButton17;
    private JRadioButton radioButton18;
    private JRadioButton radioButton19;
    private JRadioButton radioButton20;
    private JRadioButton radioButton21;
    private JRadioButton radioButton22;
    private JRadioButton radioButton23;
    private JRadioButton radioButton24;
    private JRadioButton radioButton3;
    private JRadioButton radioButton4;
    private JRadioButton radioButton5;
    private JRadioButton radioButton8;
    private JRadioButton radioButton9;
    private JRadioButton radioButton6;
    private JRadioButton radioButton10;
    private JRadioButton radioButton11;
    private JRadioButton radioButton12;
    private JRadioButton radioButton13;
    private JRadioButton radioButton14;
    private JRadioButton radioButton15;
    private JRadioButton radioButton16;
    private JLabel BoardImage;
    private JLayeredPane layeredPane;

    public GameBoardUI() {
        radioButton1 = new JRadioButton(new ImageIcon("img/BoardImages/WhiteTokenPlain.png"));
        add(radioButton1);
        /*setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        String path = "/img/BoardImages/board600pxls.png";
        final ImageIcon boardImage = createImageIcon(path);
        BoardImage = new JLabel();
        BoardImage.setIcon(boardImage);
        BoardImage.setVisible(true);
        add(BoardImage);*/
        /*layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(550, 600));
        layeredPane.setBorder(BorderFactory.createTitledBorder("New Game"));
        layeredPane.add(BoardImage,0);
        add(layeredPane);*/
    }

    public static void createGUI(){
        JFrame frame = new JFrame("Nine Men's Morris");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Create and set up the content pane.
        JComponent newContentPane = new GameBoardUI().rootPanel;
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
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createGUI();
            }
        });
    }
}
