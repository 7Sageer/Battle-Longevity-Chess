package model;

public class Action {
    public static Chessboard ChessBoard;
    private ChessboardPoint from;
    private ChessboardPoint to;
    private ChessPiece chessPiece;
    enum type{MOVE, CAPTURE}
    private type type;
    private ChessPiece capturedChessPiece;


    public Action(ChessboardPoint from, ChessboardPoint to, type type) {
        this.from = from;
        this.to = to;
        this.chessPiece = ChessBoard.getChessPieceAt(from);
        this.type = type;
        if(this.type == type.CAPTURE){
            this.capturedChessPiece = ChessBoard.getChessPieceAt(to);
        }
    }
    @Override
    public String toString() {
        return "Action{" +
                "from=" + from +
                ", to=" + to +
                ", chessPiece=" + chessPiece +
                ", type=" + type +
                (type == type.CAPTURE ? ", capturedChessPiece=" + capturedChessPiece : "") + 
                '}';
    }

    
}
