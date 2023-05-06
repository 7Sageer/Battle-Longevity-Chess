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
    private JButton aiButton;

    public TitleScreen() {
        super("Jungle Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400,500));

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel titleLabel = new JLabel("Welcome to Jungle Chess!");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        startButton = new JButton("Start 2p Game");
        startButton.setFont(new Font("Arial", Font.PLAIN, 16));
        startButton.setBackground(Color.LIGHT_GRAY);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
                GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(), mainFrame, 0);
                mainFrame.setGameController(gameController);
                mainFrame.setVisible(true);
                dispose();
            }
        });

        exitButton = new JButton("Exit Game");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 16));
        exitButton.setBackground(Color.RED);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        aiButton = new JButton("Start 1p Game");
        aiButton.setFont(new Font("Arial", Font.PLAIN, 16));
        aiButton.setBackground(Color.LIGHT_GRAY);
        aiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AIFrame aiFrame = new AIFrame();
                dispose();
            }
        });

        ImageIcon imageIcon = new ImageIcon("resource\\Elephant-red.png");
        JLabel imageLabel = new JLabel(imageIcon);
        panel.add(imageLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        buttonPanel.add(new JLabel()); // empty spacer
        buttonPanel.add(new JLabel()); // empty spacer
        buttonPanel.add(titleLabel);
        buttonPanel.add(aiButton);
        buttonPanel.add(startButton);
        buttonPanel.add(exitButton);
        panel.add(buttonPanel, BorderLayout.CENTER);

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
