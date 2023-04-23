package controller;


import listener.GameListener;
import model.Constant;
import model.PlayerColor;
import model.Chessboard;
import model.ChessboardPoint;
import view.CellComponent;
import view.ChessComponent;
import view.ChessGameFrame;
import view.ChessboardComponent;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and onPlayerClickChessPiece()]
 *
*/
public class GameController implements GameListener {


    private Chessboard model;
    private ChessboardComponent view;
    private PlayerColor currentPlayer;
    private ChessGameFrame game;

    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;

    //增加了构造器的参数（添加game用于显示对话框）
    public GameController(ChessboardComponent view, Chessboard model, ChessGameFrame game) {
        start(view, model, game);
    }

    public void start(ChessboardComponent view, Chessboard model, ChessGameFrame game){
        this.view = view;
        this.model = model;
        this.game = game;
        this.currentPlayer = PlayerColor.BLUE;

        view.registerController(this);
        initialize();
        view.initiateChessComponent(model);
        view.repaint();
    }

    private void initialize() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                //目前没想到怎么用，用于读档？
            }
        }
    }

    // after a valid move swap the player 即下个回合
    private void swapColor() {
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
    }

    //这里原本返回值是布尔型
    private int win() {
        // TODO: Check the board if there is a winner
        if(model.getGrid()[0][3].getPiece()!=null)
            return 2;
        
        if(model.getGrid()[8][3].getPiece()!=null)
            return 1;
        
        
        return 0;
    }


    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
        if (selectedPoint != null && model.isValidMove(selectedPoint, point)) {
            model.moveChessPiece(selectedPoint, point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            selectedPoint = null;
            if (win() == 1) {
                System.out.println("Blue Win!");
                game.showWinDialog("Blue Win!");
            } else if (win() == 2) {
                System.out.println("Red Win!");
                game.showWinDialog("Red Win!");
            }
            swapColor();
            view.repaint();
            
            // TODO: if the chess enter Dens or Traps and so on
            //写在chessBoard的isValidCaptured里面
            
        }
    }

    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component) {
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                selectedPoint = point;
                component.setSelected(true);
                component.repaint();
            }
        } else if (selectedPoint.equals(point)) {
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
        } else {
            model.captureChessPiece(selectedPoint, point);
            view.removeChessComponentAtGrid(point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            selectedPoint = null;
            swapColor();
            view.repaint();
        }
        // TODO: Implement capture function  做完了

    }
}
