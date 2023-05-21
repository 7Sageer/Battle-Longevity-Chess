package view;

import controller.GameController;
import model.AI;
import model.AIModel;
import model.Chessboard;
import resourcePlayer.FontsManager;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AIFrame extends CommonFrame{

    public AIFrame() {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }

    @Override
    protected void addComponent(JPanel panel) {
        panel.setPreferredSize(new Dimension(400, 700));

        JLabel titleLabel = new JLabel("Choose the level of AI!");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(FontsManager.getFont(40,1));
        panel.add(titleLabel, BorderLayout.CENTER);

        ImageIcon imageIcon = new ImageIcon("resource\\chesspiece\\狮.png");
        JLabel imageLabel = new JLabel(imageIcon);
        panel.add(imageLabel, BorderLayout.NORTH);
        
        JPanel buttonpanel = new JPanel(new GridLayout(0, 1, 5, 5));


        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem("basic");
        comboBox.addItem("advanced");

        addComboBox(buttonpanel, comboBox, 30, new ChangeListener() {
            
            @Override
            public void stateChanged(ChangeEvent e) {
                AI.setModel(new AIModel(comboBox.getSelectedItem().toString()));
            }
        });

        JButton idiotbutton = new JButton("Idiot");
        addButton(buttonpanel, idiotbutton, 300,200,30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
                GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(), mainFrame, 100);
                mainFrame.setGameController(gameController);
                mainFrame.setVisible(true);
                SettingFrame.getGameFrame(mainFrame);
                dispose();
            }
        });


        JButton easybutton = new JButton("Easy");
        addButton(buttonpanel, easybutton, 300,200,30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
                GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(), mainFrame, 1);
                mainFrame.setGameController(gameController);
                mainFrame.setVisible(true);
                SettingFrame.getGameFrame(mainFrame);
                dispose();
            }
        });

        JButton normalbutton = new JButton("Normal");
        addButton(buttonpanel, normalbutton, 300,200,30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
                GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(), mainFrame, 3);
                mainFrame.setGameController(gameController);
                mainFrame.setVisible(true);
                SettingFrame.getGameFrame(mainFrame);
                dispose();
            }
        });

        JButton hardbutton = new JButton("Hard");
        addButton(buttonpanel, hardbutton, 300,200,30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
                GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(), mainFrame, 5);
                mainFrame.setGameController(gameController);
                mainFrame.setVisible(true);
                SettingFrame.getGameFrame(mainFrame);
                dispose();
            }
        });

        JButton backbutton = new JButton("Back");
        addButton(buttonpanel, backbutton, 300,200,30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TitleScreen();
                dispose();
            }
        });
        panel.add(buttonpanel, BorderLayout.SOUTH);
    }

}