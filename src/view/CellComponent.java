package view;

import javax.swing.*;

import controller.GameController;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * This is the equivalent of the Cell class,
 * but this class only cares how to draw Cells on ChessboardComponent
 */

public class CellComponent extends JPanel {
    private BufferedImage image; // declare BufferedImage instance variable
    public int moveable = 0;
    private Color background;
    private boolean highlight = false;
    public ChessboardComponent chessboardComponent;
    private int theme = 0;

    public CellComponent(Color color, Point location, int size) {
        setLayout(new GridLayout(1,1));
        setLocation(location);
        setSize(size, size);
        this.background = color;

        changeTheme(0);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                highlight = true;
                repaint();
            }
            public void mouseExited(MouseEvent e) {
                highlight = false;
                repaint();
            }
            public void mousePressed(MouseEvent e) {
                chessboardComponent.MousePress(location);
            }
        });

    }

    public void rigisterChessboardComponent(ChessboardComponent chessboardComponent){
        this.chessboardComponent = chessboardComponent;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(image != null){
            if (highlight){
                g.drawImage(image, -5, -5, getWidth()+5, getHeight()+5, this);
            }else{
                g.drawImage(image, 1, 1, getWidth() - 1, getHeight() - 1, this); 
            }
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

    public void changeTheme(int type){
        if(type == 0){
            if(background == Color.LIGHT_GRAY){
                try {
                        File imageFile = new File("resource\\material\\lawn2.jpg");
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
        }else if(type == 1){
            if(background == Color.LIGHT_GRAY){
                try {
                        File imageFile = new File("resource\\material\\autumnLawn.jpg");
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
                        File imageFile = new File("resource\\material\\autumnTrap.jpg");
                        image = ImageIO.read(imageFile); // set BufferedImage instance variable
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if(background == Color.BLUE||background == Color.RED){
                try {
                        File imageFile = new File("resource\\material\\autumnDen.jpg");
                        image = ImageIO.read(imageFile); // set BufferedImage instance variable
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        repaint();
    }




}