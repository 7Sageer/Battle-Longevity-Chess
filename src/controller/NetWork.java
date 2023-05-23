package controller;

import model.Action;
import model.Chessboard;
import model.ChessboardPoint;
import view.OnlineFrame;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicReference;
public class NetWork implements Serializable{
    File file = new File("src/substitute");
    public static Action action = null;
    //聊天输出
    public void chatOutput(Socket socket) {
        Thread thread = new Thread(() -> {
            try {
//                socket.shutdownInput();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }
//                socket.shutdownOutput();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    //接收聊天信息
    public void chatInput(Socket socket) {
        Thread thread = new Thread(() -> {
            try {
//                socket.shutdownOutput();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line;
                while ((line = bufferedReader.readLine()) != null){
                    System.out.println(line);
                }
//                socket.shutdownInput();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public void actionOutput(Socket socket) throws IOException, ClassNotFoundException {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            FileInputStream fileInputStream = new FileInputStream(file);
            while (true) {
                if (Chessboard.currentTurn > 0) {
                    objectOutputStream.writeObject(Chessboard.historyAction.get(Chessboard.currentTurn - 1));
                    FileWriter fileWriter = new FileWriter(file);
                    fileWriter.write("");
                    fileWriter.flush();
                    fileWriter.close();
                }
            }
    }
        public Action actionInput (Socket socket) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        Action action1 = null;
        while ((action1 = (Action) objectInputStream.readObject()) != null){
            action = (Action) objectInputStream.readObject();
            return action;
        }
            return null;
        }
        public Action getAction () {
            return action;
        }
}
