package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import resourcePlayer.*;
import controller.GameController;
import model.Chessboard;

public class TitleScreen extends JFrame {
    private float buttonfontSize = 40f;

    private void addButton(JPanel panel, String buttonText, Color buttonColor, ActionListener listener) {
        JButton button = new JButton(buttonText);
        button.setFont(FontsManager.getFont(buttonfontSize,1));
        button.setBackground(buttonColor);
        button.addActionListener(listener);
        panel.add(button);
    }

    public TitleScreen() {
        super("Jungle Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400,700));

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel titleLabel = new JLabel("Jungle Chess!");
        titleLabel.setFont(FontsManager.getFont(25,0));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        

        ImageIcon imageIcon = new ImageIcon("resource\\chesspiece\\象.png");
        JLabel imageLabel = new JLabel(imageIcon);
        panel.add(imageLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        buttonPanel.add(new JLabel()); // empty spacer
        buttonPanel.add(new JLabel()); // empty spacer

        buttonPanel.add(titleLabel);

        addButton(buttonPanel, "Start 1p Game", Color.LIGHT_GRAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AIFrame aiFrame = new AIFrame();
                dispose();
            }
        });
        addButton(buttonPanel, "Start 2p Game", Color.LIGHT_GRAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
                GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(), mainFrame, 0);
                mainFrame.setGameController(gameController);
                mainFrame.setVisible(true);
                SettingFrame.getGameFrame(mainFrame);
                dispose();
            }
        });
        
        
        addButton(buttonPanel, "Settings", Color.LIGHT_GRAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SettingFrame settingFrame = new SettingFrame();
            }
        });
        addButton(buttonPanel, "Exit Game", Color.RED, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        panel.add(buttonPanel, BorderLayout.CENTER);

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
