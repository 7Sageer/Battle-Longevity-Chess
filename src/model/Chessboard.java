package model;

import view.ChessboardComponent;

/**
 * This class store the real chess information.
 * The Chessboard has 9*7 cells, and each cell has a position for chess
 */
public class Chessboard {
    private Cell[][] grid;

    public Chessboard() {
        this.grid =
                new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];//19X19

        initGrid();
        initPieces();
    }

    private void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    private void initPieces() {
        //棋子初始化
        grid[6][0].setPiece(new ChessPiece(PlayerColor.RED, "Elephant",8));
        grid[2][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Elephant",8));

        grid[0][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Lion",7));
        grid[8][6].setPiece(new ChessPiece(PlayerColor.RED, "Lion",7));
        
        grid[0][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Tiger",6));
        grid[8][0].setPiece(new ChessPiece(PlayerColor.RED, "Tiger",6));

        grid[2][2].setPiece(new ChessPiece(PlayerColor.BLUE, "Leopard",5));
        grid[6][4].setPiece(new ChessPiece(PlayerColor.RED, "Leopard",5));

        grid[2][4].setPiece(new ChessPiece(PlayerColor.BLUE, "Wolf",4));
        grid[6][2].setPiece(new ChessPiece(PlayerColor.RED, "Wolf",4));

        grid[1][1].setPiece(new ChessPiece(PlayerColor.BLUE, "Dog",3));
        grid[7][5].setPiece(new ChessPiece(PlayerColor.RED, "Dog",3));

        grid[1][5].setPiece(new ChessPiece(PlayerColor.BLUE, "Cat",2));
        grid[7][1].setPiece(new ChessPiece(PlayerColor.RED, "Cat",2));

        grid[2][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Rat",1));
        grid[6][6].setPiece(new ChessPiece(PlayerColor.RED, "Rat",1));
    }

    private ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }

    private Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }

    private int calculateDistance(ChessboardPoint src, ChessboardPoint dest) {
        return Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol() - dest.getCol());
    }

    private ChessPiece removeChessPiece(ChessboardPoint point) {
        ChessPiece chessPiece = getChessPieceAt(point);
        getGridAt(point).removePiece();
        return chessPiece;
    }

    private void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        getGridAt(point).setPiece(chessPiece);
    }

    public void moveChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidMove(src, dest)) {
            throw new IllegalArgumentException("Illegal chess move!");
        }
        setChessPiece(dest, removeChessPiece(src));
    }

    public void captureChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        // TODO: Finish the method.
        if (!isValidCapture(src, dest)) {
            throw new IllegalArgumentException("Illegal chess capture!");
        }else{
            removeChessPiece(dest);
            setChessPiece(dest, removeChessPiece(src));
        }
        
    }

    public Cell[][] getGrid() {
        return grid;
    }
    public PlayerColor getChessPieceOwner(ChessboardPoint point) {
        return getGridAt(point).getPiece().getOwner();
    }

    public boolean isValidMove(ChessboardPoint src, ChessboardPoint dest) {
        if (getChessPieceAt(src) == null || getChessPieceAt(dest) != null) {
            return false;
        }
        if (ChessboardComponent.riverCell.contains(dest) && !getChessPieceAt(src).getName().equals("Rat")) {
            return false;
        }
        
        if (canJumpRiver(src, dest) == true) {
            return true;
        }
    
        return calculateDistance(src, dest) == 1;
    }


    public boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest) {
        if(ChessboardComponent.riverCell.contains(src)||ChessboardComponent.riverCell.contains(dest))
            return false;

        if (getChessPieceAt(src) == null || getChessPieceAt(dest) == null) {
            return false;
        }

        if(canJumpRiver(src, dest) && getChessPieceAt(src).canCapture(getChessPieceAt(dest))){
            System.out.println("jump!");
            return true;
        }

        if (getChessPieceAt(src).canCapture(getChessPieceAt(dest))) {
            if (getChessPieceAt(dest).getOwner() != getChessPieceAt(src).getOwner()) {
                return calculateDistance(src, dest) == 1;
            }
        }
        return false;
    }
    public boolean canJumpRiver(ChessboardPoint src, ChessboardPoint dest){
        //狮子和老虎能跳过河流
        if(getChessPieceAt(src).getName().equals("Lion")||getChessPieceAt(src).getName().equals("Tiger")){
            ChessboardPoint temp1 = src;
            ChessboardPoint temp2 = dest;
            if(src.getRow()==dest.getRow()&&calculateDistance(src, dest) > 1){
                if(src.getCol() > dest.getCol()){
                    temp1 = dest;
                    temp2 = src;
                }else{
                    temp1 = src;
                    temp2 = dest;
                }
                for(int i = temp1.getCol() + 1;i < temp2.getCol();i++){
                    System.out.println(i);
                    if(getChessPieceAt(new ChessboardPoint(src.getRow(),i))!=null){
                        return false;
                    }
                    if(!ChessboardComponent.riverCell.contains(new ChessboardPoint(src.getRow(),i))){
                        return false;
                    }
                }
                return true;
            }else if(src.getCol()==dest.getCol()&&calculateDistance(src, dest) > 1){
                if(src.getRow() > dest.getRow()){
                    temp1 = dest;
                    temp2 = src;
                }else{
                    temp1 = src;
                    temp2 = dest;
                }
                for(int i = temp1.getRow() + 1;i < temp2.getRow();i++){
                    if(getChessPieceAt(new ChessboardPoint(i,src.getCol()))!=null){
                        return false;
                    }
                    if(!ChessboardComponent.riverCell.contains(new ChessboardPoint(i,src.getCol()))){
                        return false;
                    }
                }
                return true;
            }
    }
        return false;
    }
}
