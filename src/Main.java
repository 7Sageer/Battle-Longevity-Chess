
import view.TitleScreen;


import javax.swing.*;

import resourcePlayer.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BGM().playMusic("resource\\bgm.wav");
                FontsManager.PixelFonts();
                TitleScreen titleScreen = new TitleScreen();
                
                
            }


        });
    }
}



