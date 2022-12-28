package eg.edu.alexu.csd.oop.Circus;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Audio {
    Clip clip;
    public void playMusic(String audioLocation){
        try {
            AudioInputStream input= AudioSystem.getAudioInputStream(getClass().getClassLoader().getResourceAsStream(audioLocation));;
            clip=AudioSystem.getClip();
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
    public void stop(){ clip.stop();}
    public void resume(){
        clip.start();
    clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
