package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.Action;
import model.ChessboardPoint;
import model.Action.Type;

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

        
    public Action receiveAction() throws IOException, ClassNotFoundException {
        // 从输入流中接收Action对象
        //inputStream = new ObjectInputStream(socket.getInputStream());
        Action action = new Action(new ChessboardPoint(2, 6), new ChessboardPoint(3, 6),Type.MOVE);
        // action = (Action) inputStream.readObject();
        // if(inputStream.available() == 0) {
        //     System.out.println("No data received.");
        // }
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
        if (socket != null) {
            socket.close();
        }
    }

    public void sendAction(Action action) throws IOException {
        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            // 向输出流发送Action对象
            outputStream.writeObject(action);
            outputStream.flush();
        } finally {
            if (outputStream != null) {
                outputStream.flush();
            }
            if (socket.isClosed()) {
                System.out.println("Connection closed.");
            }
        }
    }
            
}
        
        