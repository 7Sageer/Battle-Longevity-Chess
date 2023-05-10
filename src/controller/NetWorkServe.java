package controller;

import model.Chessboard;
import model.ChessboardPoint;
import view.ChessGameFrame;
import view.ChessboardComponent;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Provider;

public class NetWorkServe extends  GameController{
    public NetWorkServe(ChessboardComponent view, Chessboard model, ChessGameFrame game, int AIDepth) {
        super(view, model, game, AIDepth);
    }

    public static void main(String args[]) throws IOException {
        GameController gameController;
        int port = 114514;//default port, can change
        ServerSocket serverSocket = new ServerSocket(port);//创建服务器套接字,作为接收客户端信息的接口
        Socket socket = serverSocket.accept();//接收客户端信息
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));//将从socket收到的数据传入流中
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

        int x = dataInputStream.readInt();//坐标
        int y = dataInputStream.readInt();
        gameController.move(ChessboardPoint from, ChessboardPoint to);//需要填入坐标

        dataOutputStream.writeUTF("MoveFinish");//向客户端返回正确值
        dataOutputStream.flush();

        socket.close();
        serverSocket.close();
    }

}
