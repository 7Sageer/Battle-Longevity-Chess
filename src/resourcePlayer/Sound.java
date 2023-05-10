package resourcePlayer;

import java.io.File;



import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
 


import javax.sound.sampled.FloatControl;

public class Sound {

    private static Clip clip;
    private static float volume = 100f;

    public static void playSound(String filename){
        try {
            File soundFile = new File(filename);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volume / 100) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setVolume(float volume) {
        Sound.volume = volume;
    }
}
