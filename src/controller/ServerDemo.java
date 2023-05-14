package controller;

import model.Action;
import model.Chessboard;
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
    public static void main(String[] args) throws IOException {

        // 创建服务端socket对象
        ServerSocket serverSocket = new ServerSocket(9002);
        // 监听客户端连接,返回一个socket对象
        Socket socket = serverSocket.accept();


        //运行棋盘，相当于把Main调用到这里
        Thread thread1 = new Thread(() -> {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new BGM().playMusic("resource\\bgm.wav");
                    FontsManager.PixelFonts();
                    new TitleScreen();
                }
            });
        });
        thread1.start();

        //创建一个用于聊天的线程，这里是接收端
        Thread thread2 = new Thread(() -> {

            // 获取输入流,读数据,并把数据显示在控制台
            //这段是聊天频道

            try {
                BufferedReader br1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line;
                while ((line = br1.readLine()) != null) {
                    System.out.println(line);
                }
            }catch (IOException e){
                e.printStackTrace();
            }

        });


        //创建一个聊天用线程，这里是输出端
        Thread thread3 = new Thread(() -> {
            //这段是聊天频道
            BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
            // 封装输出流对象
            try {
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                String line;
                //获取输出流，写数据；
                while ((line = br2.readLine()) != null) {
                    // 获取输出流对象
                    bw.write(line);
                    bw.newLine();
                    bw.flush();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        });

        //创建一个用于传输棋盘的线程。这里是接收端,接收格式为Action newAction
        Thread thread4 = new Thread(() -> {
            //接收n-1步的Action对象，在本地的对象名为newAction
            try {
                ObjectInput objectInput = new ObjectInputStream(socket.getInputStream());
                Action newAction = (Action) objectInput.readObject();
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        //创建一个用于传输期盼的线程，这里是输出端
        Thread thread5 = new Thread(() -> {
            //输出n-1步对象
            try {
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


        // 释放资源
//         thread1.interrupt();
//         thread2.interrupt();
//         thread3.interrupt();
//         thread4.interrupt();
//         thread5.interrupt();
//         serverSocket.close();
    }
}
