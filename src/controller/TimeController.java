package controller;

import java.util.Timer;
import java.util.TimerTask;
import controller.GameController;
import model.Chessboard;
import view.ChessGameFrame;
import view.ChessboardComponent;

public class TimeController extends GameController{//a class to limit the competition length
    Timer timer = new Timer();
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            swapColor();
        }
    };


    public TimeController(ChessboardComponent view, Chessboard model, ChessGameFrame game, int AIDepth) {
        super(view, model, game, AIDepth);
    }
    public void timeLimit(){
        timer.schedule(timerTask, 2000, 2000);
    }
    public void timerStop(){
        timer.cancel();
    }
    public Timer getTimer(){
        return timer;
    }
    public TimerTask getTimerTask(){
        return timerTask;
    }
}