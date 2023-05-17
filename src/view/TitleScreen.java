package view;

import controller.GameController;
import model.Chessboard;
import resourcePlayer.FontsManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TitleScreen extends CommonFrame {
    private float buttonfontSize = 40f;

    private void addButton(JPanel panel, String buttonText, Color buttonColor, ActionListener listener) {
        JButton button = new JButton(buttonText);
        button.setFont(FontsManager.getFont(buttonfontSize,1));
        button.setBackground(buttonColor);
        button.addActionListener(listener);
        panel.add(button);
    }

    public TitleScreen() {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400,700));

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    @Override
    protected void addComponent(JPanel panel) {
        JLabel titleLabel = new JLabel("Jungle Chess!");
        titleLabel.setFont(FontsManager.getFont(23,0));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 5, 5));

        buttonPanel.add(titleLabel);

        ImageIcon imageIcon = new ImageIcon("resource\\chesspiece\\象.png");
        JLabel imageLabel = new JLabel(imageIcon);
        panel.add(imageLabel, BorderLayout.NORTH);
        

        JButton AIbutton = new JButton("Start 1p Game");

        addButton(buttonPanel, AIbutton,200,50, 30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AIFrame aiFrame = new AIFrame();
                dispose();
            }
        });

        JButton startbutton = new JButton("Start 2p Game");
        addButton(buttonPanel, startbutton,200,50, 30, new ActionListener() {
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

        JButton onlinebutton = new JButton("Online Game");
        addButton(buttonPanel, onlinebutton,200,50, 30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OnlineFrame onlineFrame = new OnlineFrame();
                dispose();
            }
        });
        
        JButton settingbutton = new JButton("Settings");
        addButton(buttonPanel, settingbutton,200,50, 30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SettingFrame settingFrame = new SettingFrame();
            }
        });

        JButton helpbutton = new JButton("Help");

        addButton(buttonPanel, helpbutton,200,50, 30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HelpFrame helpFrame = new HelpFrame();

            }
        });

        JButton exitbutton = new JButton("Exit Game");
        addButton(buttonPanel, exitbutton,200,50, 30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        exitbutton.setBackground(Color.RED);


        panel.add(buttonPanel, BorderLayout.CENTER);


    }
}
