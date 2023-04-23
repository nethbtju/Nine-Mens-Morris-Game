import javax.swing.*;
import java.util.Objects;

public class Image extends JFrame {
    JFrame boardImage;
    JLabel displayField;
    ImageIcon image;

    public Image(){
        boardImage = new JFrame("Image Display Text");
        boardImage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try{
            displayField = new JLabel();
            displayField.setIcon(new ImageIcon("src/board600pxls.png"));

        } catch(Exception e) {
            System.out.println("Image Could Not Be Found");
        }
        boardImage.setSize(100,100);
        boardImage.setVisible(true);
    }
}
