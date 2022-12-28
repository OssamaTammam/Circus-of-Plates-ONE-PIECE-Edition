package eg.edu.alexu.csd.oop.Circus;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;

public class main {
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
            lblNewLabel.setIcon(new ImageIcon(ImageIO.read(main.class.getClassLoader().getResourceAsStream("menu3.jpg"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.getContentPane().add(lblNewLabel);
        JComboBox comboBox = new JComboBox();
        comboBox.setBounds(10, 85, 126, 27);
        comboBox.setForeground(new Color(255, 255, 0));
        comboBox.setBackground(new Color(51, 102, 153));
        comboBox.setMaximumRowCount(4);
        comboBox.setModel(new DefaultComboBoxModel(new String[] {"Choose Level ","Easy", "Medium", "Hard"}));
        comboBox.setToolTipText("Levels");
        comboBox.setSelectedIndex(0);
        frame.getContentPane().add(comboBox);
        frame.setVisible(true);
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentLevel=(String)comboBox.getSelectedItem();
                if((!currentLevel.equals(""))&&(!currentLevel.equals("Choose Level "))){
                    frame.setVisible(false);
                    start.setLevel(levels.get(currentLevel));
                }
            }
        });
    }
}
