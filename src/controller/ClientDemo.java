package controller;

import com.sun.tools.javac.Main;
import model.Action;
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
        // 创建客户端socket对象
        // 创建流套接字并将其连接到指定IP地址的指定端口号
        Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 9002);
        // 获取输出流,写数据
        System.out.println("input you data");
        //初始化棋盘
        Thread threadClient1 = new Thread(() -> {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new BGM().playMusic("resource\\bgm.wav");
                    FontsManager.PixelFonts();
                    new TitleScreen();
                }
            });
        });
        threadClient1.start();





        //这段是聊天频道,作为输出端
        Thread threadClient2 = new Thread(() -> {
            try{
                BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                // 封装输出流对象
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                String line;
                //获取输出流，写数据；
                while ((line = br1.readLine()) != null) {
                    // 获取输出流对象
                    bw.write(line);
                    bw.newLine();
                    bw.flush();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        });
        threadClient2.start();

        //这段是聊天频道，作为接收端
        Thread threadClient3 = new Thread(() -> {
            try{
                BufferedReader br2 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line;
                while ((line = br2.readLine()) != null){
                    System.out.println(line);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        });
        threadClient3.start();

        //以对象形式输出
        Thread threadClient4 = new Thread(() -> {
            try{
                FileInputStream fileInputStream = new FileInputStream("temp.txt");//将temp作为监听器，一旦action更新就有反应，触发while
                while (fileInputStream != null) {
                    ObjectOutput objectOutput = new ObjectOutputStream(socket.getOutputStream());
                    objectOutput.writeObject(Chessboard.historyAction.get(Chessboard.currentTurn - 1));
                    objectOutput.flush();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        });
        threadClient4.start();

        //以对象形式接收,接收格式为Action newAction
        Thread threadClient5 = new Thread(() -> {
            try {
                ObjectInput objectInput = new ObjectInputStream(socket.getInputStream());
                Action newAction = (Action) objectInput.readObject();
            }catch (IOException e){
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        threadClient1.interrupt();
        threadClient2.interrupt();
        threadClient3.interrupt();
        threadClient4.interrupt();
        threadClient5.interrupt();
        // 释放资源
        socket.close();
    }
}

