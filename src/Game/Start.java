package Game;

import eg.edu.alexu.csd.oop.game.GameEngine;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Start { ;
    int level;
    Boolean first = true;
    MyWorld myWorld ;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Audio ad=new Audio();
    String adPath="Circus.wav";
    public static GameEngine.GameController gameController;

    public void call(){
        logging log=new logging();
        JMenuBar menuBar = new JMenuBar();;
        JMenu menu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem pauseMenuItem = new JMenuItem("Pause");
        JMenuItem resumeMenuItem = new JMenuItem("Resume");
        menu.add(newMenuItem);
        menu.addSeparator();
        menu.add(pauseMenuItem);
        menu.add(resumeMenuItem);
        menuBar.add(menu);



        if(first) {
            ad.playMusic(adPath);
            first = false;
            gameController = GameEngine.start("Circus Of Plates", myWorld, menuBar);
        }
        else {
            ad.stop();
            ad.playMusic(adPath);
            gameController.changeWorld(myWorld);
        }
        newMenuItem.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                setLevel(level);
                ad.stop();
                ad.playMusic(adPath);
                gameController.changeWorld(myWorld);
            }
        });
        pauseMenuItem.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                gameController.pause();
                ad.stop();
                log.help().info("the game is paused");

            }
        });
        resumeMenuItem.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                gameController.resume();
                ad.resume();
                log.help().info("the game is resumed");

            }
        });
    }
    public void setLevel(int level){
        this.level = level;
        if(level == 1)
            myWorld= new MyWorld((int) (0.75*screenSize.getWidth()), (int) (0.75*screenSize.getHeight()), 8, 4, 2, 5, 1, 1, 3, this);
        else if(level == 2)
            myWorld = new MyWorld((int) (0.75*screenSize.getWidth()), (int) (0.75*screenSize.getHeight()), 15, 7, 3, 5, 3, 2, 5, this);
        else if(level == 3)
            myWorld = new MyWorld((int) (0.75*screenSize.getWidth()), (int) (0.75*screenSize.getHeight()), 20, 10, 3, 4, 4, 3, 7, this);
        else {
            ad.stop();
            Main.frame.setVisible(true);
            return;
        }
        call();
    }
}