package model;

public class Action {
    private ChessboardPoint from;
    private ChessboardPoint to;
    private ChessPiece chessPiece;
    enum type{MOVE, CAPTURE}
    private type type;

    public Action(ChessboardPoint from, ChessboardPoint to, ChessPiece chessPiece, type type) {
        this.from = from;
        this.to = to;
        this.chessPiece = chessPiece;
        this.type = type;
    }
    @Override
    public String toString() {
        return "Action{" +
                "from=" + from +
                ", to=" + to +
                ", chessPiece=" + chessPiece +
                ", type=" + type +
                '}';
    }

    
}
