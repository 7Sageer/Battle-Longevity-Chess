package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.GameController;
import model.Chessboard;

public class TitleScreen extends JFrame {
    private JButton startButton;
    private JButton exitButton;

    public TitleScreen() {
        super("Chess Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 250));

        JPanel panel = new JPanel(new FlowLayout());
        JLabel titleLabel = new JLabel("Welcome to Chess Game!");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
                GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(), mainFrame);
                mainFrame.setGameController(gameController);
                mainFrame.setVisible(true);
                dispose();
            }
        });
        

        exitButton = new JButton("Exit Game");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        ImageIcon imageIcon = new ImageIcon("resource\\Elephant-red.png");
        JLabel imageLabel = new JLabel(imageIcon);
        panel.add(imageLabel);

        //startButton.setBounds(100, 100, 50, 50);需要四个参数，分别是按钮的 x 坐标、y 坐标、宽度和高度

        panel.add(titleLabel);
        panel.add(startButton);
        panel.add(exitButton);
        

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}