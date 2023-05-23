package view;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.GameController;
import controller.NetWork;
import model.Action;
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
            ServerSocket serverSocket = null;
            try {//尝试启动联网并且开启棋盘
                serverSocket = new ServerSocket(9002);
                Socket socket = serverSocket.accept();
                NetWork netWork = new NetWork();
                netWork.actionOutput(socket);
                ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
                GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(), mainFrame, 200);
                mainFrame.setGameController(gameController);
                mainFrame.setVisible(true);
                SettingFrame.getGameFrame(mainFrame);
                Thread thread1 = new Thread(() -> {
                    netWork.actionOutput(socket);
                });
                Thread thread2 = new Thread(() ->{//创建一个用来移动棋子的线程
                    Action action = null;
                    while ((action = netWork.actionInput(socket)) != null){//无限使用的触发器
                        gameController.networkMove(action);
                    }
                });
                thread1.start();
                thread2.start();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }finally {
//                System.out.println(ip);
                dispose();
            }


        });

        JButton confirmButton2 = new JButton("Confirm as client");
        addButton(buttonPanel, confirmButton2, 200, 50, 30, e -> {
            String ip = ipTextField.getText();
            //开始联网，启用socket
            //todo: connect as client
            try {//尝试启动联网并启动棋盘
                Socket socket = new Socket(ip, 9002);
                NetWork netWork = new NetWork();
                netWork.actionOutput(socket);//开启向主机传输Action的线程
                ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
                GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(), mainFrame, 201);
                mainFrame.setGameController(gameController);
                mainFrame.setVisible(true);
                SettingFrame.getGameFrame(mainFrame);
                Thread thread1 = new Thread(() -> {
                    netWork.actionOutput(socket);
                });
                Thread thread2 = new Thread(() ->{
                    Action action = null;
                    while ((action = netWork.actionInput(socket)) != null){
                        gameController.networkMove(action);
                    }
                });
                thread1.start();
                thread2.start();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }finally {
//                System.out.println(ip);
                dispose();
            }



            //start game

        });

        panel.add(buttonPanel);

    }

}
