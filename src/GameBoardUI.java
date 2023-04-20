import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameBoardUI {
    private JPanel rootPanel;
    private JLabel helloLabel;
    private JButton helloButton;

    public GameBoardUI() {
        helloButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                helloLabel.setText("Hello Bundo");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("GameBoardUI");
        frame.setContentPane(new GameBoardUI().rootPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static class Board extends JPanel {
        private BufferedImage image;

        public Board() {
            try {
                image = ImageIO.read(new File("image name and path"));
            } catch (IOException ex) {
                // handle exception...
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters
        }
    }
}
