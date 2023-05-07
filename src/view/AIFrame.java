package view;

import javax.swing.*;

import controller.GameController;
import model.Chessboard;
import resourcePlayer.FontsManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AIFrame extends JFrame {

    private float buttonfontsize = 30f; 
    JButton backButton = new JButton();
    JButton easyButton = new JButton();
    JButton normalButton = new JButton();
    JButton hardButton = new JButton();

    public AIFrame() {
        super("Jungle Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 设置背景颜色和内边距
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(new Color(236, 242, 246));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Choose the level of AI!");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(FontsManager.getFont(20,0));

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 40));
        backButton.setBackground(Color.LIGHT_GRAY);
        backButton.setFont(FontsManager.getFont(buttonfontsize,1));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TitleScreen titleScreen = new TitleScreen();
                dispose();
            }
        });

        easyButton = new JButton("Easy");
        easyButton.setPreferredSize(new Dimension(100, 40));
        easyButton.setBackground(Color.LIGHT_GRAY);
        easyButton.setFont(FontsManager.getFont(buttonfontsize,1));
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
                GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(), mainFrame, 1);
                mainFrame.setGameController(gameController);
                mainFrame.setVisible(true);
                dispose();
            }
        });

        normalButton = new JButton("Normal");
        normalButton.setPreferredSize(new Dimension(100, 40));
        normalButton.setBackground(Color.LIGHT_GRAY);
        normalButton.setFont(FontsManager.getFont(buttonfontsize,1));
        normalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
                GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(), mainFrame, 3);
                mainFrame.setGameController(gameController);
                mainFrame.setVisible(true);
                dispose();
            }
        });

        hardButton = new JButton("Hard");
        hardButton.setPreferredSize(new Dimension(100, 40));
        hardButton.setBackground(Color.LIGHT_GRAY);
        hardButton.setFont(FontsManager.getFont(buttonfontsize,1));
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
                GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(), mainFrame, 5);
                mainFrame.setGameController(gameController);
                mainFrame.setVisible(true);
                dispose();
            }
        });

        // 对页面标签进行美化处理
        panel.add(titleLabel, BorderLayout.CENTER);
        ImageIcon imageIcon = new ImageIcon("resource\\Elephant-blue.png");
        JLabel imageLabel = new JLabel(imageIcon);
        panel.add(imageLabel, BorderLayout.NORTH);
        // 对按钮进行布局
        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 20, 20));
        buttonPanel.setBackground(new Color(236, 242, 246));
        buttonPanel.add(easyButton);
        buttonPanel.add(normalButton);
        buttonPanel.add(hardButton);
        buttonPanel.add(backButton);

        panel.add(buttonPanel, BorderLayout.PAGE_END);

        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


}