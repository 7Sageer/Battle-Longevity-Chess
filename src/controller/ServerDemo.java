package controller;

import resourcePlayer.BGM;
import resourcePlayer.FontsManager;
import view.CellComponent;
import view.TitleScreen;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import model.Chessboard;

/**
 * TCP 接收数据步骤
 * 1.创建服务端socket对象
 * 2.监听客户端连接,返回一个socket对象
 * 3.获取输入流,读数据,并把数据显示在控制台
 * 4.释放资源
 */
public class ServerDemo {
    public static void main(String[] args) throws IOException {
        Thread thread = new Thread(() -> {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new BGM().playMusic("resource\\bgm.wav");
                    FontsManager.PixelFonts();
                    new TitleScreen();
                }
            });
        });
        thread.start();
        // 创建服务端socket对象
        ServerSocket serverSocket = new ServerSocket(9002);
        // 监听客户端连接,返回一个socket对象
        Socket socket = serverSocket.accept();
        // 获取输入流,读数据,并把数据显示在控制台
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        while ((line = br.readLine()) != null){
            System.out.println(line);
        }
        // 释放资源
        serverSocket.close();
        //接收ChessBoard对象
        try {
            ObjectInput objectInput = new ObjectInputStream(socket.getInputStream());
            Chessboard chessboard = (Chessboard) objectInput.readObject();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
