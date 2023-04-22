import javax.swing.*;
import java.util.Objects;

public class Image extends JFrame {
    JFrame boardImage;
    JLabel displayField;
    ImageIcon image;

    public void Image(){
        boardImage = new JFrame("Image Display Text");
        boardImage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try{
            image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Users/nethbotheju/Desktop/Engineering/2023/S1/FIT3077/Project/img/BoardImages/board600pxls.png")));
            displayField = new JLabel(image);
            boardImage.add(displayField);

        } catch(Exception e) {
            System.out.println("Image Could Not Be Found");
        }
        boardImage.setSize(100,100);
        boardImage.setVisible(true);
    }
}
