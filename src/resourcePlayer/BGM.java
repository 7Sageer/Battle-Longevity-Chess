package resourcePlayer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import java.io.File;

public class BGM {
    static Clip clip;
    public void playMusic(String musicLocation) {
        try {
            File musicPath = new File(musicLocation);

            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                BGM.clip = clip;
                clip.open(audioInput);
                FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                volumeControl.setValue((float) 0.1);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void setVolume(double d) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float dB = (float) (Math.log(d / 100) / Math.log(10.0) * 20.0);
        System.out.println(dB);
        gainControl.setValue(dB);
    }

    public static double getVolume() {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        double volume = Math.pow(10.0, gainControl.getValue() / 20.0);
        System.out.println(volume);
        return volume * 99;
    }
}
