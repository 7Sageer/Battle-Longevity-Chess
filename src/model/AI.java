package model;

import java.util.ArrayList;

public class AI {
    public static Action findAction(Chessboard chessboard, int depth, PlayerColor player) {
        ArrayList<Action> allactions = chessboard.getAllValidAction(player);
        System.out.println(allactions.size());
        //random
        int index = (int)(Math.random() * allactions.size());
        return allactions.get(index);
    }
    // static Action findAction(Chessboard chessboard, int depth) {
    //     Action.ChessBoard = chessboard;
    //     Action bestAction = null;
    //     int bestScore = Integer.MIN_VALUE;
    //     for (Action action : chessboard.getAllPossibleActions()) {
    //         Chessboard newChessboard = chessboard.clone();
    //         newChessboard.move(action);
    //         int score = min(newChessboard, depth - 1);
    //         if (score > bestScore) {
    //             bestScore = score;
    //             bestAction = action;
    //         }
    //     }
    //     return bestAction;
    // }
    
}
