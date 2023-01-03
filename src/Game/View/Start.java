package Game.View;

import Game.Controller.Utils.Audio;
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
    String heeHee = "heehee.wav";
    public static GameEngine.GameController gameController;

    /**
     * This method is responsible for creating the game screen
     */
    public void callGame() {
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
            if (level == 5)
                audio.playMusic(heeHee);
            else
                audio.playMusic(audioName);
            first = false;
            gameController = GameEngine.start("Circus Of Plates", onePiece, menuBar);
        } else {
            audio.stop();
            if (level == 5)
                audio.playMusic(heeHee);
            else
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
            }
        });
        resumeMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.resume();
                audio.resume();
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
            onePiece = new OnePiece((int) (.98 * screenSize.getWidth()), (int) (.85 * screenSize.getHeight()), 10, 6, 5, 1, 3, this);
        else if (level == 2)
            onePiece = new OnePiece((int) (.98 * screenSize.getWidth()), (int) (.85 * screenSize.getHeight()), 15, 8, 5, 2, 4, this);
        else if (level == 3)
            onePiece = new OnePiece((int) (.98 * screenSize.getWidth()), (int) (.85 * screenSize.getHeight()), 20, 10, 2, 3, 5, this);
        else if (level == 4)
            onePiece = new OnePiece((int) (.98 * screenSize.getWidth()), (int) (.85 * screenSize.getHeight()), 25, 12, 2, 4, 6, this);
        else if (level == 5)
            onePiece = new OnePiece((int) (.98 * screenSize.getWidth()), (int) (.85 * screenSize.getHeight()), 50, 14, 1, 4, 7, this);
        else {
            audio.stop();
            Main.mainMenu.setVisible(true);
            gameController.pause();
            return;
        }
        callGame();
    }
}