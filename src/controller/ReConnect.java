package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReConnect {
    public void reConnect(Socket socket) {
        while (true) {
            try {
                socket.connect(socket.getRemoteSocketAddress(), 3 * 1000);
                socket.setSoTimeout(2 * 2000);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line;
                if((line = bufferedReader.readLine()) == null){
                    break;
                }
                bufferedReader.reset();
                socket.close();
                line = null;
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    Thread.sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
