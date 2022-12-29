package Game.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;

public class Main {
    private static String currentLevel="";
    public static JFrame frame;
    public static
    void main(String[] args) {
        Start start = new Start();
        HashMap<String, Integer> levels = new HashMap<>();
        levels.put("Easy", 3);
        levels.put("Medium", 5);
        levels.put("Hard", 7);
        frame = new JFrame();
        frame.setBounds(100, 100, 306, 329);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Lets Play");
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setBounds(0, 0, 300, 300);
        lblNewLabel.setForeground(Color.BLACK);
        frame.getContentPane().setLayout(null);
        try {
            lblNewLabel.setIcon(new ImageIcon(ImageIO.read(Main.class.getClassLoader().getResourceAsStream("menu3.jpg"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.getContentPane().add(lblNewLabel);
        JButton EasyButton = new JButton("Easy");
        EasyButton.setBounds(50, 85, 126, 27);
        EasyButton.setForeground(new Color(255, 255, 0));
        EasyButton.setBackground(new Color(51, 102, 153));

        JButton MeduimButton = new JButton("Medium");
        MeduimButton.setBounds(50, 125, 126, 27);
        MeduimButton.setForeground(new Color(255, 255, 0));
        MeduimButton.setBackground(new Color(51, 102, 153));

        JButton HardButton = new JButton("Hard");
        HardButton.setBounds(50, 165, 126, 27);
        HardButton.setForeground(new Color(255, 255, 0));
        HardButton.setBackground(new Color(51, 102, 153));

        frame.getContentPane().add(EasyButton,0);
        frame.getContentPane().add(MeduimButton, 0);
        frame.getContentPane().add(HardButton, 0);

        frame.setVisible(true);
        EasyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                start.setLevel(1);

            }
        });
        MeduimButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                start.setLevel(2);

            }
        });
        HardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                start.setLevel(3);

            }
        });
    }

}
