
import view.TitleScreen;


import javax.swing.*;

import sound.BGM;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BGM().playMusic("resource\\bgm.wav");
                TitleScreen titleScreen = new TitleScreen();
                
            }
        });
    }
}



