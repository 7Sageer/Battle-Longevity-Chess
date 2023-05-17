package model;

import java.util.ArrayList;


public class AI {
    private final static int windowsize = 200;
    public static Action findRandomAction(Chessboard chessboard, int depth, PlayerColor player) {
        ArrayList<Action> allactions = chessboard.getAllValidAction(player);
        System.out.println(allactions.size());
        
        //random
        int index = (int)(Math.random() * allactions.size());
        return allactions.get(index);
    }

    public static Action findBestAction(Chessboard chessboard, int depth, PlayerColor player) {

        Action bestAction = null;
        int alpha, beta;

        if(depth > 3){
            int originalscore = max (chessboard, depth - 2, Integer.MIN_VALUE, Integer.MAX_VALUE, player);
            alpha = originalscore - windowsize;
            beta = originalscore + windowsize;
        }else{
            alpha = Integer.MIN_VALUE;
            beta = Integer.MAX_VALUE;
        }

        int bestScore;
        bestScore = Integer.MAX_VALUE;
        for (Action action : chessboard.getAllValidAction(player)) {
            Chessboard newChessboard = chessboard.clone();
            newChessboard.AImove(action);
            int score = max(newChessboard, depth - 1, alpha, beta, player.getOppositePlayerColor());
            if (alpha >= score) {
                //System.out.print("alpha cut faild");
                score = max(newChessboard, depth - 1, Integer.MIN_VALUE, alpha, player.getOppositePlayerColor());
            }else if (score >= beta) {
                //System.out.print("beta cut faild");
                score = max(newChessboard, depth - 1, beta, Integer.MAX_VALUE, player.getOppositePlayerColor());
            }
            if (score < bestScore) {
                bestScore = score;
                bestAction = action;
            }
        }
        if (bestAction == null) {
            System.out.println("AI give up");
            return findRandomAction(chessboard, depth, player);
        }
        System.out.println("AI choose " + bestAction + " score: " + bestScore);
        return bestAction;
    }

    static int max(Chessboard chessboard, int depth, int alpha, int beta, PlayerColor player) {
        //System.out.print(depth);
        if (depth == 0 || chessboard.isBlueWin()||chessboard.isRedWin()) 
            return evaluate(chessboard, player);
    
        int bestScore = Integer.MIN_VALUE;
        for (Action action : chessboard.getAllValidAction(player)) {
            Chessboard newChessboard = chessboard.clone();
            newChessboard.AImove(action);
            int score;  
            score = min(newChessboard, depth - 1, alpha, beta, player.getOppositePlayerColor());
    
            bestScore = Math.max(bestScore, score);
            alpha = Math.max(alpha, bestScore);
            if (beta <= alpha) 
                break;   // beta cut-off
        }
        return bestScore;
    }
    
    static int min(Chessboard chessboard, int depth, int alpha, int beta, PlayerColor player) {
        // 同max函数,对alpha和beta进行相反的比较和更新
        //System.out.print(depth);
        if (depth == 0 || chessboard.isBlueWin()||chessboard.isRedWin()) 
            return evaluate(chessboard, player);
        
        int bestScore = Integer.MAX_VALUE;
        for (Action action : chessboard.getAllValidAction(player)) {
            Chessboard newChessboard = chessboard.clone();
            newChessboard.AImove(action);
            int score;
            score = max(newChessboard, depth - 1, alpha, beta, player.getOppositePlayerColor());
            bestScore = Math.min(bestScore, score);
            beta = Math.min(beta, bestScore);
            if (beta <= alpha) 
                break;   // alpha cut-off
        }
        return bestScore;
        
    }

    
    static int evaluate(Chessboard chessboard, PlayerColor player) {
        int score = 0;
        if (chessboard.isRedWin()) {
            if (player == PlayerColor.BLUE) {
                return Integer.MIN_VALUE;
            } else {
                return Integer.MAX_VALUE;
            }
        }
        if (chessboard.isBlueWin()) {
            if (player == PlayerColor.RED) {
                return Integer.MIN_VALUE;
            } else {
                return Integer.MAX_VALUE;
            }
        }
        ChessPiece chessPieces[] = chessboard.getAllChessPiece();
        //System.out.print("---------" + player);
        //for (ChessPiece i: chessPieces)
        //    System.out.print(i);
        for (ChessPiece chessPiece : chessPieces) {
            if (chessPiece == null) {
                continue;
            }
            if (chessPiece.getOwner() == player) {
                score += 500;
                score += chessPiece.rank * 100;
                score += chessboard.caculateCaptureScore(chessPiece) * 5;
                score -= chessboard.getEnemyDistance(chessPiece);

            } else {
                score -= 500;
                score -= chessPiece.rank * 100;
                score -= chessboard.caculateCaptureScore(chessPiece) * 5;
                score += chessboard.getEnemyDistance(chessPiece);
            }
        }
        return score;
    }

    // for (ChessPiece chessPiece : chessPieces) {
    //     if (chessPiece == null) {
    //         continue;
    //     }
    //     if (chessPiece.getOwner() == player) {
    //         score += 500;
    //         score += chessPiece.rank * 100;
    //         score += chessboard.caculateCaptureScore(chessPiece) * 5;
    //         score -= chessboard.getEnemyDistance(chessPiece);

    //     } else {
    //         score -= 500;
    //         score -= chessPiece.rank * 100;
    //         score -= chessboard.caculateCaptureScore(chessPiece) * 5;
    //         score += chessboard.getEnemyDistance(chessPiece);
    //     }
    // }
    
}
