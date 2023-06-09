package view;


import java.awt.*;

/**
 * 继承自CellComponent，修改了paintComponent方法，使得绘制的是一个空心圆
 * 现在已经弃用了，直接改变CellComponent
 */

public class SelectComponent extends CellComponent {
    private Color background;
    public SelectComponent(Color background, Point location, int size) {
        super(background, location, size);
        this.background = background;
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(background);
        Graphics2D g2d = (Graphics2D) g;
        //g2d.setColor(new Color(255, 255, 255, 255));
        // 设置线条宽度
        g2d.setStroke(new BasicStroke(3f));
        g2d.drawRect(0, 0, getWidth(), getHeight());
    }
}