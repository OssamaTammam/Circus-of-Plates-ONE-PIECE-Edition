package Game.Controller;

import javax.sound.sampled.*;
import java.io.IOException;

/**
 * This class is responsible for playing the audio of the game
 */
public class Audio {
    Clip clip;

    /**
     * Play the audio of the game
     *
     * @param audioName Path of the audio file
     */
    public void playMusic(String audioName) {

        try {
            AudioInputStream input = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResourceAsStream(audioName));
            clip = AudioSystem.getClip();
            clip.open(input);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (UnsupportedAudioFileException ex) {
            ex.printStackTrace();
        }
    }

    public void stop() {
        clip.stop();
    }

    public void resume() {
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
