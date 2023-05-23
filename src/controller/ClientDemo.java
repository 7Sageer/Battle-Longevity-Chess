package controller;

import model.UserAdministrator;
import resourcePlayer.BGM;
import resourcePlayer.FontsManager;
import view.TitleScreen;

import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * TCP 发送数据步骤
 * 1.创建客户端socket对象
 * 2.获取输出流，写数据
 * 3.释放资源
 */
public class ClientDemo {

    public static void linkStart() throws IOException, ClassNotFoundException {
        // 创建客户端socket对象
        // 创建流套接字并将其连接到指定IP地址的指定端口号

        Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 9002);

        // 获取输出流,写数据
        System.out.println("input you data");
        //初始化棋盘
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
        netWork.chatInput(socket);
        netWork.chatOutput(socket);
    }
}

