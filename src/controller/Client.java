package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.Action;

public class Client {
    Socket socket;
    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;

    public Client(String serverIP, int port) {
        try {
            // 创建客户端Socket并连接到服务器
            socket = new Socket(serverIP, port);
            System.out.println("Connected to server: " + serverIP);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendAction(Action action) throws IOException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {
            // 向输出流发送Action对象
            outputStream.writeObject(action);
            outputStream.flush();
        }
    }

    public Action receiveAction() throws IOException, ClassNotFoundException {
        // 从输入流中接收Action对象
        inputStream = new ObjectInputStream(socket.getInputStream());
        if (socket.isClosed()) {
            throw new IOException("Socket is closed");
        }
        Action action = (Action) inputStream.readObject();
        return action;
    }

    public void close() throws IOException {
        if (inputStream != null) {
            inputStream.close();
        }
        if (outputStream != null) {
            outputStream.close();
        }
        if (socket != null) {
            socket.close();
        }
    }
}