package tcp;

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
        Socket socket = new Socket(InetAddress.getByName("127.0.0.1"),9002);
        // 获取输出流,写数据
        System.out.println("input you data");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 封装输出流对象
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        String line ;
        //获取输出流，写数据；
        while ((line = br.readLine()) != null){
            // 获取输出流对象
            bw.write(line);
            bw.newLine();
            bw.flush();
        }
        // 释放资源
        socket.close();
    }
}