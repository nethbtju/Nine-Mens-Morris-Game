import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends JFrame implements ActionListener {
    JButton button;
    JButton button2;
    MyFrame(){

        JFrame frame = new JFrame("Nine Man's Morris Game");
        frame.setSize(1000, 750);
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(new ImageIcon("rough_work/Project Inception Java/Images/test_board.jpeg").getImage().getScaledInstance(1000, 750, Image.SCALE_SMOOTH)));
        frame.add(label);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);


        button = new JButton();
        button.setBounds(150, 110, 60, 50);
        button.addActionListener(this);
        //button.setText("hi");
        button.setOpaque(true);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        //button.setBackground(Color.pink);
        button.setBorder(new RoundedBorder(10));
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setIcon(new ImageIcon(new ImageIcon("rough_work/Project Inception Java/Images/basketball.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));

        button2 = new JButton();
        button2.setBounds(150, 350, 60, 50);
        button2.addActionListener(this);
        //button2.setText("hi");
        button2.setOpaque(false);
        button2.setContentAreaFilled(false);
        button2.setBorderPainted(false);
        button2.setFocusPainted(false);
        //button2.setBackground(Color.pink);
        button2.setBorder(null);
        button2.setIcon(new ImageIcon(new ImageIcon("rough_work/Project Inception Java/Images/test_black_peice.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));






        frame.add(button);
        frame.add(button2);

        JLabel label2 = new JLabel();

        label2.setBounds(300, 350, 100, 100);
        label2.setOpaque(true);
        label2.setBackground(Color.pink);
        label2.setIcon(new ImageIcon(new ImageIcon("src/Images/test_black_peice.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));

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