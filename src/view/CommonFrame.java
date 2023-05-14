package view;

import resourcePlayer.FontsManager;
import resourcePlayer.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 这是一个通用的窗口类，用于实现一些通用的功能，比如添加按钮，添加组件等
 */

public class CommonFrame extends JFrame {
    public CommonFrame(){
        super("Jungle Chess");

        JPanel panel = new JPanel(new GridLayout(0, 1,5,10));
        panel.setBackground(new Color(236, 242, 246));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel titleLabel = new JLabel("Settings");
        
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(FontsManager.getFont(20,1));

        this.addComponent(panel);
        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    protected void addComponent(JPanel panel){
    }

    protected void addButton(JPanel panel, JButton button, int width, int height, int fontSize, ActionListener listener) {
        button.setPreferredSize(new Dimension(width, height));
        button.setBackground(Color.LIGHT_GRAY);
        button.setFont(FontsManager.getFont(fontSize,1));
        button.addActionListener(listener);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 播放声音
                String soundPath = "resource\\sounds\\click-button.wav"; // 替换为您的声音文件路径
                Sound.playSound(soundPath);
            }
        });
        panel.add(button);
    }

    protected JLabel addLabel(JPanel panel, String text, int fontSize) {
        JLabel label = new JLabel(text);
        label.setFont(FontsManager.getFont(fontSize,1));
        label.setForeground(new Color(51, 97, 129));
        panel.add(label);
        return label;
    }

    protected JSlider addSlider(JPanel panel, int orientation, int min, int max, int value, javax.swing.event.ChangeListener listener) {
        JSlider slider = new JSlider(orientation, min, max, value);
        slider.addChangeListener(listener);
        panel.add(slider);
        return slider;
    }
    protected JComboBox<String> addComboBox(JPanel panel, String string, int fontSize,  javax.swing.event.ChangeListener listener){
        JComboBox<String> comboBox = new JComboBox<>(new String[]{string});
        comboBox.setFont(FontsManager.getFont(fontSize,1));
        comboBox.setForeground(new Color(51, 97, 129));
        panel.add(comboBox);
        return comboBox;
    }

    protected void addPicture(JPanel panel, String path, int width, int height) {
        ImageIcon imageIcon = new ImageIcon(path);
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setPreferredSize(new Dimension(width, height));
        panel.add(imageLabel);
        
    }

}
