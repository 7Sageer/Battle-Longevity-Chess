package view;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.GameController;
import model.Chessboard;

import java.awt.FlowLayout;

public class OnlineFrame extends CommonFrame {
    public OnlineFrame() {
        super();
        setTitle("Input IP");
        setSize(500, 300);            
        setLocationRelativeTo(null); // 把窗口设在屏幕中央
    }
    @Override
    protected void addComponent(JPanel panel){
        addLabel(panel, "Input IP", 30);
        
        JTextField ipTextField = new JTextField();
        addTextField(panel, ipTextField, 20);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton confirmButton = new JButton("Confirm as server");
        addButton(buttonPanel, confirmButton, 200, 50, 30, e -> {
            String ip = ipTextField.getText();
            System.out.println(ip);
            //todo: connect as server

            //start game
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
            GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(), mainFrame, 200);
            mainFrame.setGameController(gameController);
            mainFrame.setVisible(true);
            SettingFrame.getGameFrame(mainFrame);
            
            dispose();
        });

        JButton confirmButton2 = new JButton("Confirm as client");
        addButton(buttonPanel, confirmButton2, 200, 50, 30, e -> {
            String ip = ipTextField.getText();
            System.out.println(ip);
            //todo: connect as client

            //start game
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
            GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(), mainFrame, 201);
            mainFrame.setGameController(gameController);
            mainFrame.setVisible(true);
            SettingFrame.getGameFrame(mainFrame);
            
            
            dispose();
        });

        panel.add(buttonPanel);

    }

}
