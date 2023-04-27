package view;


import javax.swing.*;
import java.awt.*;

/**
 * 继承自CellComponent，修改了paintComponent方法，使得绘制的是一个空心圆
 */

public class SelectComponent extends CellComponent {
    private Color background;
    public SelectComponent(Color background, Point location, int size) {
        super(background, location, size);
        this.background = background;
    }

    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.setColor(background);
        // 绘制一个空心正方形
        g.drawRect(0, 0, getWidth(), getHeight());
    }
}
