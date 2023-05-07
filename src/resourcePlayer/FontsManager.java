package resourcePlayer;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class FontsManager {
    private static Font font0;
    private static Font font1;

    public static Font getFont(float size, int style){
        if (style == 0)
            return font0.deriveFont(size);
        else if(style == 1)
            return font1.deriveFont(size);
        else
            return font0.deriveFont(size);
    }

    public static void PixelFonts() {
        try {
            URL fontUrl = new File("resource\\PixgamerRegular-OVD6A.ttf").toURI().toURL();
            font1 = Font.createFont(Font.TRUETYPE_FONT, fontUrl.openStream());

            URL fontUrl1 = new File("resource\\HachicroUndertaleBattleFontRegular-L3zlg.ttf").toURI().toURL();
            font0 = Font.createFont(Font.TRUETYPE_FONT, fontUrl1.openStream());
            
            // Use deriveFont() method on custom font
            // UIManager.put("Label.font", customFont.deriveFont(14f));
            // UIManager.put("Button.font", customFont.deriveFont(14f));
            // Add more UI components as needed
    
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

}
