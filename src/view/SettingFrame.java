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

        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(new Color(236, 242, 246));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel titleLabel = new JLabel("Settings");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(FontsManager.getFont(20,1));

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 40));
        backButton.setBackground(Color.LIGHT_GRAY);
        backButton.setFont(FontsManager.getFont(40,1));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JButton themeButton = new JButton("Change Theme");
        themeButton.setPreferredSize(new Dimension(200, 40));
        themeButton.setBackground(Color.LIGHT_GRAY);
        themeButton.setFont(FontsManager.getFont(30,1));

        themeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChessboardComponent.changeTheme();
                game.changeTheme();
            }
        });


        // Add a label to the volume slider
        JLabel volumeLabel = new JLabel("Volume:");
        volumeLabel.setFont(FontsManager.getFont(40,1));
        volumeLabel.setForeground(new Color(51, 97, 129));

        

        

        JSlider volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        volumeSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = ((JSlider) e.getSource()).getValue();
                BGM.setVolume(value/100.0);
            }
        });

        JLabel soundLabel = new JLabel("Sounds:");
        soundLabel.setFont(FontsManager.getFont(40,1));
        soundLabel.setForeground(new Color(51, 97, 129));

        

        

        JSlider soundSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        soundSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = ((JSlider) e.getSource()).getValue();
                Sound.setVolume(value);
            }
        });


        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 20, 20));
        buttonPanel.setBackground(new Color(236, 242, 246));
        
        buttonPanel.add(volumeLabel);
        buttonPanel.add(volumeSlider);
        buttonPanel.add(soundLabel);
        buttonPanel.add(soundSlider);
        buttonPanel.add(themeButton);
        buttonPanel.add(backButton);

        
        panel.add(buttonPanel, BorderLayout.PAGE_END);

        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void getGameFrame(ChessGameFrame game){
        SettingFrame.game = game;
    }
}
