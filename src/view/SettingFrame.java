package view;

import javax.swing.*;
import javax.swing.event.ChangeEvent;

import resourcePlayer.BGM;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import resourcePlayer.*;

public class SettingFrame extends JFrame {

    private static ChessGameFrame game;

    public SettingFrame(){
        super("Jungle Chess");

        JPanel panel = new JPanel(new GridLayout(0, 1,5,10));
        panel.setBackground(new Color(236, 242, 246));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel titleLabel = new JLabel("Settings");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(FontsManager.getFont(20,1));

        JButton backButton = new JButton("back");
        addButton(panel, backButton, 100, 40, 40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JButton themeButton = new JButton("change theme");
        addButton(panel, themeButton, 200, 40, 30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChessboardComponent.changeTheme();
                game.changeTheme();
            }
        });

        JLabel volumeLabel = addLabel(panel, "volume:", 40, new Color(51, 97, 129));
        JSlider volumeSlider = addSlider(panel, JSlider.HORIZONTAL, 0, 100, 50, new javax.swing.event.ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = ((JSlider) e.getSource()).getValue();
                BGM.setVolume(value/100.0);
            }
        });

        JLabel soundLabel = addLabel(panel, "sounds:", 40, new Color(51, 97, 129));
        JSlider soundSlider = addSlider(panel, JSlider.HORIZONTAL, 0, 100, 50, new javax.swing.event.ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = ((JSlider) e.getSource()).getValue();
                Sound.setVolume(value);
            }
        });

        addButton(panel, themeButton, 200, 40, 30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChessboardComponent.changeTheme();
                game.changeTheme();
            }
        });

        addButton(panel, backButton, 100, 40, 40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void getGameFrame(ChessGameFrame game){
        SettingFrame.game = game;
    }

    private void addButton(JPanel panel, JButton button, int width, int height, int fontSize, ActionListener listener) {
        button.setPreferredSize(new Dimension(width, height));
        button.setBackground(Color.LIGHT_GRAY);
        button.setFont(FontsManager.getFont(fontSize,1));
        button.addActionListener(listener);
        panel.add(button);
    }

    private JLabel addLabel(JPanel panel, String text, int fontSize, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(FontsManager.getFont(fontSize,1));
        label.setForeground(color);
        panel.add(label);
        return label;
    }

    private JSlider addSlider(JPanel panel, int orientation, int min, int max, int value, javax.swing.event.ChangeListener listener) {
        JSlider slider = new JSlider(orientation, min, max, value);
        slider.addChangeListener(listener);
        panel.add(slider);
        return slider;
    }
}