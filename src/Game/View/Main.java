package Game.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;

public class Main {
    public static JFrame mainMenu;

    public static void main(String[] args) {
        Start start = new Start();
        mainMenu = new JFrame();
        mainMenu.setBounds(100, 100, 306, 329);
        mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenu.setResizable(false);
        mainMenu.setTitle("Lets Play");
        JLabel mainMenuBackground = new JLabel("");
        mainMenuBackground.setBounds(0, 0, 300, 300);
        mainMenuBackground.setForeground(Color.BLACK);
        mainMenu.getContentPane().setLayout(null);
        try {
            mainMenuBackground.setIcon(new ImageIcon(ImageIO.read(Main.class.getClassLoader().getResourceAsStream("menu3.jpg"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainMenu.getContentPane().add(mainMenuBackground);
        JButton easyButton = new JButton("Easy");
        easyButton.setBounds(50, 65, 126, 27);
        easyButton.setForeground(new Color(255, 255, 0));
        easyButton.setBackground(new Color(51, 102, 153));

        JButton mediumButton = new JButton("Medium");
        mediumButton.setBounds(50, 105, 126, 27);
        mediumButton.setForeground(new Color(255, 255, 0));
        mediumButton.setBackground(new Color(51, 102, 153));

        JButton hardButton = new JButton("Hard");
        hardButton.setBounds(50, 145, 126, 27);
        hardButton.setForeground(new Color(255, 255, 0));
        hardButton.setBackground(new Color(51, 102, 153));

        JButton veryHardButton = new JButton("Very Hard");
        veryHardButton.setBounds(50, 185, 126, 27);
        veryHardButton.setForeground(new Color(255, 255, 0));
        veryHardButton.setBackground(new Color(51, 102, 153));

        JButton heeHeeButton = new JButton("HEE HEE");
        heeHeeButton.setBounds(50, 225, 126, 27);
        heeHeeButton.setForeground(new Color(255, 255, 0));
        heeHeeButton.setBackground(new Color(51, 102, 153));

        mainMenu.getContentPane().add(easyButton, 0);
        mainMenu.getContentPane().add(mediumButton, 0);
        mainMenu.getContentPane().add(hardButton, 0);
        mainMenu.getContentPane().add(veryHardButton, 0);
        mainMenu.getContentPane().add(heeHeeButton, 0);

        mainMenu.setVisible(true);
        easyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainMenu.setVisible(false);
                start.setLevel(1);

            }
        });
        mediumButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainMenu.setVisible(false);
                start.setLevel(2);

            }
        });
        hardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainMenu.setVisible(false);
                start.setLevel(3);

            }
        });
        veryHardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainMenu.setVisible(false);
                start.setLevel(4);

            }
        });
        heeHeeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainMenu.setVisible(false);
                start.setLevel(5);

            }
        });
    }
}
