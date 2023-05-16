package model;

import java.util.ArrayList;


public class AI {
    
    private final static int windowsize = 300;
    public static Action findRandomAction(Chessboard chessboard, int depth, PlayerColor player) {
        ArrayList<Action> allactions = chessboard.getAllValidAction(player);
        //System.out.println(allactions.size());
        //random
        int index = (int)(Math.random() * allactions.size());
        return allactions.get(index);
    }

    public static Action findBestAction(Chessboard chessboard, int depth, PlayerColor player) {

        Action bestAction = null;
        int alpha, beta;
        int bestScore;
        
        Chessboard yieldchessboard = chessboard.clone();
        if(depth % 2 == 0)
        {
            if(depth > 2){
                int originalscore = max(yieldchessboard, depth - 1,Integer.MIN_VALUE,Integer.MAX_VALUE, player).score;
                alpha = originalscore - windowsize;
                beta =  originalscore + windowsize;
            }else{
                alpha = Integer.MIN_VALUE;
                beta =  Integer.MAX_VALUE;
            }
            bestAction = min(yieldchessboard, depth - 1, alpha, beta, player);
            if(bestAction.score < alpha||bestAction.score > beta){
                bestAction = min(yieldchessboard, depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, player);
                System.out.println("cut failed");
            }
        }
        else{
            if(depth > 2){
                int originalscore = min(yieldchessboard, depth - 1,Integer.MIN_VALUE,Integer.MAX_VALUE, player).score;
                alpha = originalscore - windowsize;
                beta =  originalscore + windowsize;
            }
            else{
                alpha = Integer.MIN_VALUE;
                beta =  Integer.MAX_VALUE;
            }
            bestAction = max(yieldchessboard, depth - 1, alpha, beta, player);
            if(bestAction.score < alpha||bestAction.score > beta){
                bestAction = max(yieldchessboard, depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, player);
                System.out.println("cut failed");
            }
        }
        if (bestAction == null) {
            System.out.println("AI give up");
            return null;
        }
        System.out.println("AI choose " + bestAction + " score: " + bestAction.score);
        return bestAction;
    }

    static Action max(Chessboard chessboard, int depth, int alpha, int beta, PlayerColor player) {
        //System.out.print(depth);
        Action bestAction = null;
        if (depth == 0 || chessboard.isBlueWin()||chessboard.isRedWin()){
            bestAction = findRandomAction(chessboard, depth, player);
            bestAction.score = evaluate(chessboard, player);
            return bestAction;
        } 
            
    
        int bestScore = Integer.MIN_VALUE;
        
        for (Action action : chessboard.getAllValidAction(player)) {
            Chessboard newChessboard = chessboard.clone();
            newChessboard.AImove(action);
            int score;  
            score = min(newChessboard, depth - 1, alpha, beta, player.getOppositePlayerColor()).score;
            if(bestScore < score){
                bestAction = action;
                bestScore = score;
                bestAction.score = score;
            }
            alpha = Math.max(alpha, bestScore);

            if (beta <= alpha) 
                break;   // beta cut-off
        }
        if(bestAction == null)
            return findRandomAction(chessboard, depth, player);
        return bestAction;
    }
    
    static Action min(Chessboard chessboard, int depth, int alpha, int beta, PlayerColor player) {
        // 同max函数,对alpha和beta进行相反的比较和更新
        //System.out.print(depth);
        Action bestAction = null;
        if (depth == 0 || chessboard.isBlueWin()||chessboard.isRedWin()){
            bestAction = findRandomAction(chessboard, depth, player);
            bestAction.score = evaluate(chessboard, player);
            return bestAction;
        } 
        
        int bestScore = Integer.MAX_VALUE;
        for (Action action : chessboard.getAllValidAction(player)) {
            Chessboard newChessboard = chessboard.clone();
            newChessboard.AImove(action);
            int score;
            score = max(newChessboard, depth - 1, alpha, beta, player.getOppositePlayerColor()).score;
            if(bestScore > score){
                bestAction = action;
                bestScore = score;
                bestAction.score = score;
            }
            beta = Math.min(beta, bestScore);
            if (beta <= alpha) 
                break;   // alpha cut-off
        }
        if(bestAction == null)
            return findRandomAction(chessboard, depth, player);
        return bestAction;
        
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

    
}
