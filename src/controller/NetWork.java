package controller;

import model.Action;
import model.Chessboard;
import model.ChessboardPoint;
import view.OnlineFrame;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicReference;
public class NetWork implements Serializable{
    private static Action action;
    public void actionOutput(Socket socket) throws IOException, ClassNotFoundException {
        int i = 0;
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        while (true) {

            if (Chessboard.historyAction.size() > i) {
                i = Chessboard.historyAction.size();
                objectOutputStream.writeObject(Chessboard.historyAction.get(Chessboard.currentTurn - 1));
            }
            objectOutputStream.flush();
            objectOutputStream.reset();
        }
    }
        public Action actionInput (Socket socket) throws IOException, ClassNotFoundException {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            while (true) {

                action = (Action) objectInputStream.readObject();
                return action;
            }

    }
}
