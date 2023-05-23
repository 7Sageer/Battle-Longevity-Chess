package controller;

import model.Chessboard;
import model.ChessboardPoint;
import resourcePlayer.Sound;
import view.ChessGameFrame;
import view.ChessboardComponent;

public class SpecialMove extends GameController{
    public SpecialMove(ChessboardComponent view, Chessboard model, ChessGameFrame game, int gamemode) {
        super(view, model, game, gamemode);

    }

}
