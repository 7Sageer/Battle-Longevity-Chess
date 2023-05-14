package view;

import controller.GameController;
import model.Chessboard;
import resourcePlayer.FontsManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class AIFrame extends CommonFrame{

    private float buttonfontsize = 30f;

    public AIFrame() {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }

    @Override
    protected void addComponent(JPanel panel) {
        panel.setPreferredSize(new Dimension(400, 600));

        JLabel titleLabel = new JLabel("Choose the level of AI!");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(FontsManager.getFont(40,1));
        panel.add(titleLabel, BorderLayout.CENTER);

        ImageIcon imageIcon = new ImageIcon("resource\\chesspiece\\狮.png");
        JLabel imageLabel = new JLabel(imageIcon);
        panel.add(imageLabel, BorderLayout.NORTH);
        
        JPanel buttonpanel = new JPanel(new GridLayout(0, 1, 5, 5));

        JButton easybutton = new JButton("Easy");
        addButton(buttonpanel, easybutton, 300,200,30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
                GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(), mainFrame, 1);
                mainFrame.setGameController(gameController);
                mainFrame.setVisible(true);
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
                dispose();
            }
        });

        JButton backbutton = new JButton("Back");
        addButton(buttonpanel, backbutton, 300,200,30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TitleScreen titleScreen = new TitleScreen();
                dispose();
            }
        });
        panel.add(buttonpanel, BorderLayout.SOUTH);
    }

}