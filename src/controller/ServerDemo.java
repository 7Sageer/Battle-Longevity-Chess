package controller;

import model.Action;
import model.Chessboard;
import model.UserAdministrator;
import resourcePlayer.BGM;
import resourcePlayer.FontsManager;
import view.TitleScreen;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TCP 接收数据步骤
 * 1.创建服务端socket对象
 * 2.监听客户端连接,返回一个socket对象
 * 3.获取输入流,读数据,并把数据显示在控制台
 * 4.释放资源
 */
public class ServerDemo {
    public static void linkStart() throws IOException, ClassNotFoundException {

        // 创建服务端socket对象
        ServerSocket serverSocket = new ServerSocket(9002);
        // 监听客户端连接,返回一个socket对象
        Socket socket = serverSocket.accept();
        NetWork netWork = new NetWork();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BGM().playMusic("resource\\bgm.wav");
                FontsManager.PixelFonts();
                UserAdministrator.loadData();
                new TitleScreen();
            }
        });

        netWork.actionOutput(socket);
        netWork.actionInput(socket);

    }
}
