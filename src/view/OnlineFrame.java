package view;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.GameController;
import controller.NetWork;
import model.Chessboard;

import java.awt.FlowLayout;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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

            //todo: connect as server//这里其实直接启动棋盘就行
            //start game

            try {//尝试启动联网并且开启棋盘
                ServerSocket serverSocket = new ServerSocket(9002);
                Socket socket = serverSocket.accept();

                ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
                GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(), mainFrame, 200);
                mainFrame.setGameController(gameController);
                mainFrame.setVisible(true);
                SettingFrame.getGameFrame(mainFrame);

                NetWork netWork = new NetWork();

                Thread thread = new Thread(() -> {
                    try {
                        while (true) {
                            netWork.actionOutput(socket);
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                Thread thread1 = new Thread(() -> {
                    try {
                        int i = 0;
                            while(true) {
                                gameController.networkMove(netWork.actionInput(socket));
                            }
                        } catch (IOException | ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    });

                    thread.start();
                    thread1.start();

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
//            dispose();
        });

        JButton confirmButton2 = new JButton("Confirm as client");
        addButton(buttonPanel, confirmButton2, 200, 50, 30, e -> {
            String ip = ipTextField.getText();
            //开始联网，启用socket
            //todo: connect as client


            try {//尝试启动联网并启动棋盘
                Socket socket = new Socket(ip, 9002);

                ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
                GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(), mainFrame, 201);
                mainFrame.setGameController(gameController);
                mainFrame.setVisible(true);
                SettingFrame.getGameFrame(mainFrame);

                NetWork netWork = new NetWork();

                Thread thread = new Thread(() -> {
                    try {
                            netWork.actionOutput(socket);
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                Thread thread1 = new Thread(() -> {
                    try {
                            gameController.networkMove(netWork.actionInput(socket));
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                thread.start();
                thread1.start();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
//            dispose();
        });
        panel.add(buttonPanel);
    }

}
