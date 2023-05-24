package view;

import controller.GameController;
import resourcePlayer.BGM;
import resourcePlayer.Sound;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingFrame extends CommonFrame {

    private static ChessGameFrame game;

    public SettingFrame(){
        super();
    }

    public void addComponent(JPanel panel){
        addLabel(panel, "volume:", 40);
        addSlider(panel, JSlider.HORIZONTAL, 0, 100, (int)BGM.getVolume(), new javax.swing.event.ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = ((JSlider) e.getSource()).getValue();
                BGM.setVolume(value);
            }
        });

        addLabel(panel, "sounds:", 40);
        addSlider(panel, JSlider.HORIZONTAL, 0, 100, (int)Sound.getVolume(), new javax.swing.event.ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = ((JSlider) e.getSource()).getValue();
                Sound.setVolume(value);
            }
        });

        JButton isTimerButton;
        if (GameController.isTimer)
            isTimerButton = new JButton("close timer");
        else
            isTimerButton = new JButton("open timer");
        addButton(panel, isTimerButton, 100, 40, 30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameController.isTimer = !GameController.isTimer;
                if(!GameController.isTimer){
                    isTimerButton.setText("open timer");
                    game.setTimeLabel("");
                }else{
                    isTimerButton.setText("close timer");
                    game.setTimeLabel(String.format("Time Left: %ds", 30));
                }

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

        JButton UserButton;
        UserButton = new JButton("User");
        addButton(panel, UserButton, 100, 40, 30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserFrame();
            }

        });


        JButton backButton = new JButton("back");

        addButton(panel, backButton, 100, 40, 40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }



    public static void getGameFrame(ChessGameFrame game){
        SettingFrame.game = game;
    }
}