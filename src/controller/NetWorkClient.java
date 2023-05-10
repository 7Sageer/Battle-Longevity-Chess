package controller;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.Buffer;

public class NetWorkClient {
    public void netClient(String host, int port) throws IOException {
        Socket socket = new Socket(host, port);//套接字

        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));//输入与输出
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

        dataOutputStream.write(from);//向服务器输出起始地点
        dataOutputStream.write(to);//向服务器输出目标地点

        System.out.println(dataInputStream.readUTF());//打印服务器返回值
    }
}
