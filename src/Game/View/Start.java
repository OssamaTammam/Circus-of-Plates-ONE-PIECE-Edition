package Game.View;

import Game.Controller.Audio;
import Game.Controller.Logging;
import eg.edu.alexu.csd.oop.game.GameEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is responsible for starting the game
 */
public class Start {
    int level;
    Boolean first = true;
    OnePiece onePiece;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Audio audio = new Audio();
    String audioName = "Circus.wav";
    public static GameEngine.GameController gameController;

    /**
     * This method is responsible for creating the game screen
     */
    public void callGame() {
        Logging log = new Logging();
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem pauseMenuItem = new JMenuItem("Pause");
        JMenuItem resumeMenuItem = new JMenuItem("Resume");
        menu.add(newMenuItem);
        menu.addSeparator();
        menu.add(pauseMenuItem);
        menu.add(resumeMenuItem);
        menuBar.add(menu);

        if (first) {
            audio.playMusic(audioName);
            first = false;
            gameController = GameEngine.start("Circus Of Plates", onePiece, menuBar);
        } else {
            audio.stop();
            audio.playMusic(audioName);
            gameController.changeWorld(onePiece);
        }
        newMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setLevel(level);
                audio.stop();
                audio.playMusic(audioName);
                gameController.changeWorld(onePiece);
            }
        });
        pauseMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.pause();
                audio.stop();
                log.help().info("the game is paused");
            }
        });
        resumeMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.resume();
                audio.resume();
                log.help().info("the game is resumed");
            }
        });
    }

    /**
     * This method is responsible for setting the level of the game
     *
     * @param level the level of the game
     */
    public void setLevel(int level) {
        this.level = level;
        if (level == 1)
            onePiece = new OnePiece((int) (0.75 * screenSize.getWidth()), (int) (0.75 * screenSize.getHeight()), 10, 6, 5, 1, 3, this);
        else if (level == 2)
            onePiece = new OnePiece((int) (0.75 * screenSize.getWidth()), (int) (0.75 * screenSize.getHeight()), 15, 8, 5, 2, 4, this);
        else if (level == 3)
            onePiece = new OnePiece((int) (0.75 * screenSize.getWidth()), (int) (0.75 * screenSize.getHeight()), 20, 10, 2, 3, 5, this);
        else {
            audio.stop();
            Main.mainMenu.setVisible(true);
            gameController.pause();
            return;
        }
        callGame();
    }
}