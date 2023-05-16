package model;

public class Action {
    public static Chessboard ChessBoard;
    private ChessboardPoint from;
    private ChessboardPoint to;
    private ChessPiece chessPiece;
    public int score;
    public enum Type{MOVE, CAPTURE}
    public Type type;
    private ChessPiece capturedChessPiece;


    public Action(ChessboardPoint from, ChessboardPoint to, Type type) {
        this.from = from;
        this.to = to;
        this.chessPiece = ChessBoard.getChessPieceAt(from);
        this.type = type;
        if(this.type == Type.CAPTURE){
            this.capturedChessPiece = ChessBoard.getChessPieceAt(to);
        }
    }
    public Action(ChessboardPoint from, ChessboardPoint to, Type type, ChessPiece chessPiece) {
        this.from = from;
        this.to = to;
        this.chessPiece = ChessBoard.getChessPieceAt(from);
        this.type = type;
        this.capturedChessPiece = chessPiece;
    }
    public Action() {
    }
    @Override
    public String toString() {
        return "{from=" + from +
                ",to=" + to +
                ",chessPiece=" + chessPiece +
                ",type=" + type +
                (type == Type.CAPTURE ? ",capturedChessPiece=" + capturedChessPiece : "") + "}";
    }
    public ChessboardPoint getFrom() {
        return from;
    }

    public ChessboardPoint getTo() {
        return to;
    }
    public ChessPiece getChessPiece() {
        return chessPiece;
    }
    public ChessPiece getCapturedChessPiece() {
        return capturedChessPiece;
    }
    public Type getType() {
        return type;
    }

    
}
