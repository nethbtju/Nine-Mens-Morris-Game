import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class MyFrame extends JFrame implements ActionListener {
    JButton button;
    JButton button2;
    MyFrame() {
        JFrame frame = new JFrame("Nine Man's Morris Game.Game");
        frame.setSize(1000, 750);
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon("/Users/nethbotheju/Desktop/Engineering/2023/S1/FIT3077/Project/img/BoardImages/board600pxls.png"));
        frame.add(label);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button){
            System.out.println("(0,0)");
            button.setOpaque(!button.isOpaque());
        }
        if(e.getSource() == button2){
            System.out.println("(0,1)");
            button2.setOpaque(!button2.isOpaque());
        }

    }
}