package Game.Controller.Utils;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

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
            InputStream audioSource = getClass().getClassLoader().getResourceAsStream(audioName);
            AudioInputStream input = new BufferedInputStream(audioSource) != null ? AudioSystem.getAudioInputStream(new BufferedInputStream(audioSource)) : null;
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
