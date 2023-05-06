package view;


import model.PlayerColor;
import javax.imageio.ImageIO;

import javax.swing.*;
import java.awt.*;
import java.io.File;


/**
 * This is the equivalent of the ChessPiece class,
 * but this class only cares how to draw Chess on ChessboardComponent
 */
//我把ElephantChessComponet小改了一下，添加了name作为公共类

public class ChessComponent extends JComponent {
    private PlayerColor owner;
    private String name;
    private boolean selected;
    //private Image image;

    public ChessComponent(PlayerColor owner, int size, String name) {
        this.owner = owner;
        this.name = name;
        this.selected = false;
        setSize(size/2, size/2);
        //setLocation(0,0);
        setVisible(true);
        // try {
        //     image = ImageIO.read(new File("resource\\Elephant-blue.png"));
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        //g2.drawImage( image,null, getFocusCycleRootAncestor());
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = new Font("黑体", Font.PLAIN, getWidth() / 2);
        
        g2.setFont(font);
        FontMetrics metrics = g2.getFontMetrics(font);
        int stringWidth = metrics.stringWidth(name);
        int stringHeight = metrics.getHeight();
        int x = (getWidth() - stringWidth) / 2;
        int y = (getHeight() - stringHeight) / 2 + metrics.getAscent();
        g2.setColor(owner.getColor());
        g2.drawString(name, x, y);
        
           if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
    @Override
    public void paintImmediately(int x, int y, int w, int h) {
        paintComponent(getGraphics());
        super.paintImmediately(x, y, w, h);
    }
}
