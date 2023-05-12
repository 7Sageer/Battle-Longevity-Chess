package controller;

import com.sun.tools.javac.Main;
import resourcePlayer.BGM;
import resourcePlayer.FontsManager;
import view.TitleScreen;
import model.Chessboard;

import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * TCP 发送数据步骤
 * 1.创建客户端socket对象
 * 2.获取输出流，写数据
 * 3.释放资源
 */
public class ClientDemo {

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
        // 创建客户端socket对象
        // 创建流套接字并将其连接到指定IP地址的指定端口号
        Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 9002);
        // 获取输出流,写数据
        System.out.println("input you data");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 封装输出流对象
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        String line;
        //获取输出流，写数据；
        while ((line = br.readLine()) != null) {
            // 获取输出流对象
            bw.write(line);
            bw.newLine();
            bw.flush();
        }
        //以对象形式输出
        FileInputStream fileInputStream = new FileInputStream("temp.txt");
        while (fileInputStream != null) {
            ObjectOutput objectOutput = new ObjectOutputStream(socket.getOutputStream());
            Chessboard chessboard = new Chessboard();
            objectOutput.writeObject(chessboard.historyAction);
            objectOutput.flush();
        }
        // 释放资源
        socket.close();
    }
}

