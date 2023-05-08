package view;

import javax.swing.*;
import java.awt.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * This is the equivalent of the Cell class,
 * but this class only cares how to draw Cells on ChessboardComponent
 */

// public class CellComponent extends JPanel {
//     private Color background;
//     public int moveable = 0;

//     public CellComponent(Color background, Point location, int size) {
//         setLayout(new GridLayout(1,1));
//         setLocation(location);
//         setSize(size, size);
//         this.background = background;
//     }

//     @Override
//     protected void paintComponent(Graphics g) {
//         super.paintComponents(g);
//         g.setColor(background);
//         g.fillRect(1, 1, this.getWidth()-1, this.getHeight()-1);
//         if(moveable != 0){
//             Graphics2D g2d = (Graphics2D) g.create();
//             g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f)); //Set opacity here, 0.5f for 50%
//             if(moveable == 2)
//                 g2d.setColor(Color.RED);
//             else if(moveable == 1)
//                 g2d.setColor(Color.BLUE);
//             g2d.fillRect(1, 1, this.getWidth()-1, this.getHeight()-1);
//             g2d.dispose();
//         }
//     }
// }
public class CellComponent extends JPanel {
    private BufferedImage image; // declare BufferedImage instance variable
    public int moveable = 0;
    private Color background;

    public CellComponent(Color color, Point location, int size) {
        setLayout(new GridLayout(1,1));
        setLocation(location);
        setSize(size, size);
        this.background = color;
        if(background == Color.LIGHT_GRAY){
            try {
                    File imageFile = new File("resource\\material\\lawn1.jpg");
                    image = ImageIO.read(imageFile); // set BufferedImage instance variable
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(background == Color.CYAN){
            try {
                    File imageFile = new File("resource\\material\\river2.jpg");
                    image = ImageIO.read(imageFile); // set BufferedImage instance variable
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(background == Color.ORANGE){
            try {
                    File imageFile = new File("resource\\material\\trap1.jpg");
                    image = ImageIO.read(imageFile); // set BufferedImage instance variable
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(background == Color.BLUE||background == Color.RED){
            try {
                    File imageFile = new File("resource\\material\\den.jpg");
                    image = ImageIO.read(imageFile); // set BufferedImage instance variable
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(image != null){
            g.drawImage(image, 1, 1, getWidth() - 1, getHeight() - 1, this); // draw the image
        }
        else{
            g.setColor(background);
            g.fillRect(1, 1, this.getWidth()-1, this.getHeight()-1);
        }
        if(moveable != 0){
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f)); //Set opacity here, 0.5f for 50%
            if(moveable == 2)
                g2d.setColor(Color.RED);
            else if(moveable == 1)
                g2d.setColor(Color.BLUE);
            g2d.fillRect(1, 1, this.getWidth()-1, this.getHeight()-1);
            g2d.dispose();
        }
    }
}