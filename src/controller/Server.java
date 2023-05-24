package controller;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import model.Action;

public class Server {
    public ServerSocket serverSocket;
    public Socket clientSocket;
    public ObjectOutputStream outputStream;
    public ObjectInputStream inputStream;

    public Server(int port) {
        try {
            // 创建服务器端Socket并绑定到指定端口
            serverSocket = new ServerSocket(port);
            System.out.println("Waiting for connection...");
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Action receiveAction() throws IOException, ClassNotFoundException {
        // 从输入流中接收Action对象
        inputStream = new ObjectInputStream(clientSocket.getInputStream());
        Action action = null;
        if (inputStream.available() > 0) {
            action = (Action) inputStream.readObject();
        }
        // 不要在这里关闭inputStream
        return action;
    }

    public void close() throws IOException {
        if (inputStream != null) {
            inputStream.close();
        }
        if (outputStream != null) {
            outputStream.close();
        }
        if (clientSocket != null) {
            clientSocket.close();
        }
    }

    public void sendAction(Action action) throws IOException {
        try {
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            // 向输出流发送Action对象
            outputStream.writeObject(action);
            outputStream.flush();
        } finally {
            if (outputStream != null) {
                outputStream.flush();
            }
            if (clientSocket.isClosed()) {
                System.out.println("Connection closed.");
            }
        }
    }
            
}

