package model;
import controller.GameController;
import view.ChessGameFrame;
import view.ChessboardComponent;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
public class TimeLimit extends GameController {
    private PlayerColor currentPlayer = getCurrentPlayer();
    public TimeLimit(ChessboardComponent view, Chessboard model, ChessGameFrame game, int AIDepth) {
        super(view, model, game, AIDepth);

    }

    public void testTimer(int limitedTime){//input time limit
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {//build a thread
                currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;//change chess player
            }
        }, limitedTime);
    }
    public void testTimer(){//if no input, time default limit to 30s
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {//build a thread
                currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;//change chess player
            }
        }, 30000);
    }

}
