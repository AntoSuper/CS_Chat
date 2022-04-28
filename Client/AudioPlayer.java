/**
 * Write a description of class AudioPlayer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioPlayer {
    
    AudioInputStream audio;
    Clip clip;

    public AudioPlayer() {
    }
    
    public void play (String x) {
        try {
            audio = AudioSystem.getAudioInputStream(getClass().getResource(x));
            clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        }
        catch (Exception e) {
            System.out.println("Problema nell'apertura audio!");
            System.out.println();
        }
    }
}
