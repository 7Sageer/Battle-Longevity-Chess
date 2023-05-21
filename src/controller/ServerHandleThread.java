package controller;

import model.Action;

import java.io.*;
import java.net.Socket;

public class ServerHandleThread implements Runnable{
    Socket socket = null;
    public Action action = null;

    public ServerHandleThread(Socket socket){
        super();
        this.socket = socket;
    }
    @Override
    public void run(){
        OutputStream outputStream = null;
        PrintWriter printWriter = null;

        try{
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            action = (Action) objectInputStream.readObject();
            socket.shutdownInput();
            outputStream = socket.getOutputStream();
            printWriter  = new PrintWriter(outputStream);
            printWriter.println("Welcome");
            printWriter.flush();
            socket.shutdownOutput();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }finally {
            try{
                if(printWriter!=null){
                    printWriter.close();
                }
                if(outputStream!=null){
                    outputStream.close();
                }
                if(socket!=null){
                    socket.close();
                }
            }catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
