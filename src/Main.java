import model.UserAdministrator;
import resourcePlayer.BGM;
import resourcePlayer.FontsManager;
import view.TitleScreen;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BGM().playMusic("resource\\bgm.wav");
                FontsManager.PixelFonts();
                UserAdministrator.loadData();
                new TitleScreen();
            }


        });
    }
}



