package model;

import view.ChessComponent;
import view.ChessboardComponent;
import java.util.ArrayList;
import java.io.Serializable;

import model.Action.Type;

/**
 * This class store the real chess information.
 * The Chessboard has 9*7 cells, and each cell has a position for chess
 */
public class Chessboard implements Serializable {
    private Cell[][] grid;
    public static ArrayList<Action> historyAction = new ArrayList<Action>();
    public static int currentTurn = 0;

    public Chessboard() {
        initialize();
    }
    public void initialize() {
        grid = new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];
        initGrid();
        initPieces();
    }

    private void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();
            }
        }
        Action.ChessBoard = this;
    }

    private void initPieces() {
        // grid[6][0].setPiece(new ChessPiece(PlayerColor.RED, "Elephant",8));
        // grid[6][1].setPiece(new ChessPiece(PlayerColor.BLUE, "Elephant",8));
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


    public ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }

    public ChessPiece[] getAllChessPiece(){
        ChessPiece[] chessPieces = new ChessPiece[16];
        int count = 0;
        for(int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++){
            for(int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++){
                if(grid[i][j].getPiece() != null){
                    chessPieces[count] = grid[i][j].getPiece();
                    count++;
                }
            }
        }
        return chessPieces;
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
        //System.out.print("remove" + point.toString() + chessPiece.toString());
        return chessPiece;
    }

    public void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        getGridAt(point).setPiece(chessPiece);
    }

    public void moveChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidMove(src, dest)) {
            throw new IllegalArgumentException("Illegal chess move!" + src.toString() + getGridAt(src).getPiece().toString() +" " + dest.toString() + getGridAt(dest).getPiece().toString());
        }
        
        if(historyAction.size() != 0 && historyAction.size() > currentTurn && historyAction.get(currentTurn).getTo()!=dest){
            historyAction.subList(currentTurn, historyAction.size()).clear();
            System.out.println("clear");
        }
        historyAction.add(new Action(src,dest,Type.MOVE));
        setChessPiece(dest, removeChessPiece(src));
        currentTurn++;
    }
    
    //这可不能悔棋，我把悔棋写到了gamecontroller里面，因为要配合redo而且懒得改了所以就这样了
    public void moveChessPiece(ChessboardPoint src, ChessboardPoint dest, boolean isUndo) {
        if (!isValidMove(src, dest)) {
            throw new IllegalArgumentException("Illegal chess move!" + src.toString() + getGridAt(src).getPiece().toString() + " " + dest.toString() + getGridAt(dest).getPiece().toString());
        }
        historyAction.add(new Action(src,dest,Type.MOVE));
        setChessPiece(dest, removeChessPiece(src));
        currentTurn++;
    }

    public void captureChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidCapture(src, dest)) {
            throw new IllegalArgumentException("Illegal chess capture!");
        }else{
            System.out.println(new Action(src,dest,Type.CAPTURE).toString());
            if(historyAction.size() != 0 && historyAction.get(currentTurn - 1).getTo()!=dest){
                historyAction.subList(historyAction.size() + currentTurn - historyAction.size(), historyAction.size()).clear();
                System.out.println("clear");
            }
            historyAction.add(new Action(src,dest,Type.CAPTURE));
            removeChessPiece(dest);
            setChessPiece(dest, removeChessPiece(src));
            currentTurn++;
        }
        
    }

    //这里同上
    public void captureChessPiece(ChessboardPoint src, ChessboardPoint dest, boolean isUndo) {
        if (!isValidCapture(src, dest)) {
            throw new IllegalArgumentException("Illegal chess capture!");
        }else{
            System.out.println(new Action(src,dest,Type.CAPTURE).toString());
            historyAction.add(new Action(src,dest,Type.CAPTURE));
            removeChessPiece(dest);
            setChessPiece(dest, removeChessPiece(src));
            currentTurn++;
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

        if(ChessboardComponent.redDenCell.contains(dest)&&getChessPieceAt(src).getOwner()==PlayerColor.RED)
            return false;
        
        if(ChessboardComponent.blueDenCell.contains(dest)&&getChessPieceAt(src).getOwner()==PlayerColor.BLUE)
            return false;

        
        if (canJumpRiver(src, dest) == true) {
            return true;
        }
    
        return calculateDistance(src, dest) == 1;
    }


    public boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest) {
        if((ChessboardComponent.redTrapCell.contains(src)&&getChessPieceAt(src).getOwner()==PlayerColor.BLUE)||(ChessboardComponent.blueTrapCell.contains(src)&&getChessPieceAt(src).getOwner()==PlayerColor.RED))
            return false;
        if(ChessboardComponent.riverCell.contains(src)||ChessboardComponent.riverCell.contains(dest)||getChessPieceAt(src) == null || getChessPieceAt(dest) == null)
            return false;

        if(canJumpRiver(src, dest) && getChessPieceAt(src).canCapture(getChessPieceAt(dest))){
            //System.out.println("jump!");
            return true;
        }

        if ((ChessboardComponent.blueTrapCell.contains(dest))||ChessboardComponent.redTrapCell.contains(dest)||getChessPieceAt(src).canCapture(getChessPieceAt(dest))) {

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
                    //System.out.println(i);
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

    public ArrayList<Action> getAllValidAction(PlayerColor playerColor) {
        ArrayList<Action> validMoves = new ArrayList<>();
        for (int row = 0; row < Constant.CHESSBOARD_ROW_SIZE.getNum(); row++) {
            for (int col = 0; col < Constant.CHESSBOARD_COL_SIZE.getNum(); col++) {
                ChessboardPoint src = new ChessboardPoint(row, col);
                if (getChessPieceAt(src) != null && getChessPieceAt(src).getOwner() == playerColor) {
                    validMoves.addAll(getValidAction(src));
                }
            }
        }
        //System.out.println(validMoves);
        return validMoves;
    }
    public ArrayList<Action> getValidAction(ChessboardPoint src) {
        ArrayList<Action> validMoves = new ArrayList<>();
        for (int row = 0; row < Constant.CHESSBOARD_ROW_SIZE.getNum(); row++) {
            for (int col = 0; col < Constant.CHESSBOARD_COL_SIZE.getNum(); col++) {
                ChessboardPoint dest = new ChessboardPoint(row, col);
                if (isValidMove(src, dest)) {
                    validMoves.add(new Action(src, dest,Type.MOVE));
                }
                if (isValidCapture(src, dest)) {
                    validMoves.add(new Action(src, dest,Type.CAPTURE));
                }
            }
        }
        return validMoves;
    }
    public int AImove(Action action){
        if (action.type == Type.MOVE){
            if(isValidMove(action.getFrom(),action.getTo())){
                setChessPiece(action.getTo(),removeChessPiece(action.getFrom()));
                //setChessPiece(dest, removeChessPiece(src));

            }else{
                System.out.println("Invalid move" + action.toString() + getGridAt(action.getFrom()).getPiece().toString() +" " + getGridAt(action.getTo()).getPiece().toString());
                return 0;
            }
            
        }else if(action.type == Type.CAPTURE){
            if(isValidCapture(action.getFrom(),action.getTo())){
                removeChessPiece(action.getTo());
                setChessPiece(action.getTo(),removeChessPiece(action.getFrom()));
            }else{
                System.out.println("Invalid capture"+action.toString() + getGridAt(action.getFrom()).getPiece().toString() +" " + getGridAt(action.getTo()).getPiece().toString());
                return 0;
            }

        }
        
        return 1;
    }
    public boolean isRedWin(){
        if(grid[0][3].getPiece()!=null){
            return true;
        }
        return false;
    }
    public boolean isBlueWin(){
        if(grid[8][3].getPiece()!=null){
            return true;
        }
        return false;
    }

    public Chessboard clone(){
        // 创建一个新的棋盘对象
        Chessboard clonedBoard = new Chessboard();
        // 遍历整个棋盘
        for (int row = 0; row < Constant.CHESSBOARD_ROW_SIZE.getNum(); row++) {
            for (int col = 0; col < Constant.CHESSBOARD_COL_SIZE.getNum(); col++) {
                ChessboardPoint point = new ChessboardPoint(row, col);
                // 获取当前位置的棋子
                ChessPiece piece = getChessPieceAt(point);
                clonedBoard.grid[row][col] = new Cell();
                if (piece != null) {
                    // 如果当前位置有棋子，就将其克隆并添加到新的棋盘对象中

                    clonedBoard.setChessPiece(point, piece.clone());
                    clonedBoard.grid[row][col].setPiece(piece.clone());
                    //System.out.println(clonedBoard.getChessPieceAt(point).toString());
                }
            }
        }
        // 返回新的棋盘对象
        return clonedBoard;
    }
    public int getEnemyDistance(ChessPiece chessPiece) {
        ChessboardPoint point = getChessPieChessboardPoint(chessPiece);
        if(chessPiece.getOwner() == PlayerColor.BLUE)
            return calculateDistance(point, new ChessboardPoint(8, 3));
        else
            return calculateDistance(point, new ChessboardPoint(0, 3));
    }

    public ChessboardPoint getChessPieChessboardPoint(ChessPiece chessPiece){
        for(int row = 0;row < Constant.CHESSBOARD_ROW_SIZE.getNum();row++){
            for(int col = 0;col < Constant.CHESSBOARD_COL_SIZE.getNum();col++){
                if(grid[row][col].getPiece() == chessPiece){
                    return new ChessboardPoint(row,col);
                }
            }
        }
        return null;
    }



}
