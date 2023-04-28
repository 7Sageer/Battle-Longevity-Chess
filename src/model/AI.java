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
//
    // public static Action findBestAction(Chessboard chessboard, int depth, PlayerColor player) {
    //     Action bestAction = null;
    //     int bestScore = Integer.MIN_VALUE;
    //     for (Action action : chessboard.getAllValidAction(player)) {
    //         Chessboard newChessboard = chessboard.clone();
    //         newChessboard.AImove(action);
    //         int score = min(newChessboard, depth - 1, player.getOppositePlayerColor());
    //         if (score > bestScore) {
    //             bestScore = score;
    //             bestAction = action;
    //         }
    //     }
    //     return bestAction;
    // }

    // static int min(Chessboard chessboard, int depth, PlayerColor player) {
    //     if (depth == 0) {
    //         return evaluate(chessboard,player);
    //     }
    //     int bestScore = Integer.MAX_VALUE;
    //     for (Action action : chessboard.getAllValidAction(player)) {
    //         Chessboard newChessboard = chessboard.clone();
    //         newChessboard.AImove(action);
    //         int score = max(newChessboard, depth - 1, player.getOppositePlayerColor());
    //         if (score < bestScore) {
    //             bestScore = score;
    //         }
    //     }
    //     return bestScore;
    // }

    // static int max(Chessboard chessboard, int depth, PlayerColor player) {
    //     if (depth == 0) {
    //         return evaluate(chessboard,player);
    //     }
    //     int bestScore = Integer.MIN_VALUE;
    //     for (Action action : chessboard.getAllValidAction(player.getOppositePlayerColor())) {
    //         Chessboard newChessboard = chessboard.clone();
    //         newChessboard.AImove(action);
    //         int score = min(newChessboard, depth - 1, player);
    //         if (score > bestScore) {
    //             bestScore = score;
    //         }
    //     }
    //     return bestScore;
    // }
    public static Action findBestAction(Chessboard _chessboard_, int _depth_, PlayerColor _player_) {
        Action bestAction = null;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        for (Action action : _chessboard_.getAllValidAction(_player_)) {
            Chessboard newChessboard = _chessboard_.clone();
            newChessboard.AImove(action);
            int score = min(newChessboard, _depth_ - 1, _player_.getOppositePlayerColor(), alpha, beta);
            if (score > alpha) {
                alpha = score;
                bestAction = action;
            }
        }
        return bestAction;
    }
    
    static int min(Chessboard _chessboard_, int _depth_, PlayerColor _player_, int alpha, int beta) {
        if (_depth_ == 0) {
            return evaluate(_chessboard_,_player_);
        }
        int bestScore = Integer.MAX_VALUE;
        for (Action action : _chessboard_.getAllValidAction(_player_)) {
            Chessboard newChessboard = _chessboard_.clone();
            newChessboard.AImove(action);
            int score = max(newChessboard, _depth_ - 1, _player_.getOppositePlayerColor(), alpha, beta);
            bestScore = Math.min(bestScore, score);
            alpha = Math.max(alpha, bestScore);
            if (beta <= alpha) {
                break;
            }
        }
        return bestScore;
    }
    
    static int max(Chessboard _chessboard_, int _depth_, PlayerColor _player_, int alpha, int beta) {
        // 同min方法,对角色反转
        if (_depth_ == 0) {
            return evaluate(_chessboard_,_player_);
        }
        int bestScore = Integer.MIN_VALUE;
        for (Action action : _chessboard_.getAllValidAction(_player_.getOppositePlayerColor())) {
            Chessboard newChessboard = _chessboard_.clone();
            newChessboard.AImove(action);
            int score = min(newChessboard, _depth_ - 1, _player_, alpha, beta);
            bestScore = Math.max(bestScore, score);
            beta = Math.min(beta, bestScore);
            if (beta <= alpha) {
                break;
            }
        }
        return bestScore;
    }
    
    static int evaluate(Chessboard chessboard, PlayerColor player) {
        int score = 0;
        for (ChessPiece chessPiece : chessboard.getAllChessPiece()) {
            if (chessPiece == null) {
                continue;
            }
            if (chessPiece.getOwner() == player) {
                score += chessPiece.rank;
            } else {
                score -= chessPiece.rank;
            }
        }
        return score;
    }

    
}
