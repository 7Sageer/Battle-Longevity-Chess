package controller;

import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
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
        // 获取输入流,读数据,并把数据显示在控制台
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        while ((line = br.readLine()) != null){
            System.out.println(line);
        }
        // 释放资源
        serverSocket.close();
    }
}
