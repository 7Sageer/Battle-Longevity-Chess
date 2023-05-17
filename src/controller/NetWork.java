package controller;

import model.Action;
import model.Chessboard;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicReference;
public class NetWork {
    public static Action action;
    //聊天输出
    public void chatOutput(Socket socket) {
        Thread thread = new Thread(() -> {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }
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
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line;
                while ((line = bufferedReader.readLine()) != null){
                    System.out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public void actionOutput(Socket socket){
        Thread thread = new Thread(() ->{
           try {
               FileInputStream fileInputStream = new FileInputStream("temp.txt");
               int integer;
               while ((integer = fileInputStream.read()) != 0){
                   ObjectOutput objectOutput = new ObjectOutputStream(socket.getOutputStream());
                   if(Chessboard.historyAction.size() > 0) {
                       objectOutput.writeObject(Chessboard.historyAction.get(Chessboard.currentTurn));
                   }
                   objectOutput.flush();
               }
           }catch (IOException e){
               e.printStackTrace();
           }
        });
        thread.start();
    }

    public Action actionInput(Socket socket) {
        Thread thread = new Thread(() -> {
            try {
                ObjectInput objectInput = new ObjectInputStream(new ObjectInputStream(socket.getInputStream()));
                NetWork.action = (Action) objectInput.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
        return action;
    }
}
