package view;

import controller.GameController;
import model.Chessboard;
import resourcePlayer.FontsManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

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
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JPanel buttonpanel = new JPanel(new GridLayout(0, 1, 5, 5));


        panel.setBackground(new Color(236, 242, 246));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Choose the level of AI!");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(FontsManager.getFont(40,1));

        panel.add(titleLabel, BorderLayout.CENTER);
        ImageIcon imageIcon = new ImageIcon("resource\\chesspiece\\狮.png");
        JLabel imageLabel = new JLabel(imageIcon);
        panel.add(imageLabel, BorderLayout.NORTH);
        panel.add(buttonpanel, BorderLayout.SOUTH);

        addButton("Easy", buttonfontsize, Color.LIGHT_GRAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
                GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(), mainFrame, 1);
                mainFrame.setGameController(gameController);
                mainFrame.setVisible(true);
                dispose();
            }
        }, buttonpanel);

        addButton("Normal", buttonfontsize, Color.LIGHT_GRAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
                GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(), mainFrame, 3);
                mainFrame.setGameController(gameController);
                mainFrame.setVisible(true);
                dispose();
            }
        }, buttonpanel);

        addButton("Hard", buttonfontsize, Color.LIGHT_GRAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
                GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(), mainFrame, 5);
                mainFrame.setGameController(gameController);
                mainFrame.setVisible(true);
                dispose();
            }
        }, buttonpanel);

        addButton("Back", buttonfontsize, Color.LIGHT_GRAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TitleScreen titleScreen = new TitleScreen();
                dispose();
            }
        }, buttonpanel);

        // 对页面标签进行美化处理


        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addButton(String text, float fontsize, Color color, ActionListener listener, JPanel panel) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(100, 40));
        button.setBackground(color);
        button.setFont(FontsManager.getFont(fontsize,1));
        button.addActionListener(listener);
        panel.add(button, BorderLayout.PAGE_END);
    }
}