package view;


import controller.GameController;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;

/**
 * This class represents the checkerboard component object on the panel
 */
public class ChessboardComponent extends JComponent {
    private CellComponent[][] gridComponents = new CellComponent[CHESSBOARD_ROW_SIZE.getNum()][CHESSBOARD_COL_SIZE.getNum()];
    private final int CHESS_SIZE;
    public final static Set<ChessboardPoint> riverCell = new HashSet<>();
    public final static Set<ChessboardPoint> blueTrapCell = new HashSet<>();
    public final static Set<ChessboardPoint> redTrapCell = new HashSet<>();
    public final static Set<ChessboardPoint> blueDenCell = new HashSet<>();
    public final static Set<ChessboardPoint> redDenCell = new HashSet<>();

    private GameController gameController;

    public ChessboardComponent(int chessSize) {
        CHESS_SIZE = chessSize;
        int width = CHESS_SIZE * 7;
        int height = CHESS_SIZE * 9;
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);// Allow mouse events to occur
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        System.out.printf("chessboard width, height = [%d : %d], chess size = %d\n", width, height, CHESS_SIZE);

        initiateGridComponents();
    }


    /**
     * This method represents how to initiate ChessComponent
     * according to Chessboard information
     */
    public void initiateChessComponent(Chessboard chessboard) {
        Cell[][] grid = chessboard.getGrid();
        //gridComponents = new CellComponent[CHESSBOARD_ROW_SIZE.getNum()][CHESSBOARD_COL_SIZE.getNum()];
        
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                // TODO: Implement the initialization checkerboard
                gridComponents[i][j].removeAll();

                if (grid[i][j].getPiece() != null) {
                    ChessPiece chessPiece = grid[i][j].getPiece();
                    System.out.println(chessPiece.getName());
                    if (gridComponents[i][j].getComponents().length==0){
                        gridComponents[i][j].add(
                                new ChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE,chessJudge(chessPiece)));
                    }

                }
            }
        }

    }
    private String chessJudge(ChessPiece chess){
        if(chess.getName() == "Elephant")
            return "象";
        else if(chess.getName() == "Lion")
            return "狮";
        else if(chess.getName() == "Tiger")
            return "虎";
        else if(chess.getName() == "Leopard")
            return "豹";
        else if(chess.getName() == "Wolf")
            return "狼";
        else if(chess.getName() == "Dog")
            return "狗";
        else if(chess.getName() == "Cat")
            return "猫";
        else
            return "鼠";
    }

    public void initiateGridComponents() {

        riverCell.add(new ChessboardPoint(3,1));
        riverCell.add(new ChessboardPoint(3,2));
        riverCell.add(new ChessboardPoint(4,1));
        riverCell.add(new ChessboardPoint(4,2));
        riverCell.add(new ChessboardPoint(5,1));
        riverCell.add(new ChessboardPoint(5,2));

        riverCell.add(new ChessboardPoint(3,4));
        riverCell.add(new ChessboardPoint(3,5));
        riverCell.add(new ChessboardPoint(4,4));
        riverCell.add(new ChessboardPoint(4,5));
        riverCell.add(new ChessboardPoint(5,4));
        riverCell.add(new ChessboardPoint(5,5));

        blueTrapCell.add(new ChessboardPoint(0,2));
        blueTrapCell.add(new ChessboardPoint(0,4));
        blueTrapCell.add(new ChessboardPoint(1,3));

        redTrapCell.add(new ChessboardPoint(8,2));
        redTrapCell.add(new ChessboardPoint(8,4));
        redTrapCell.add(new ChessboardPoint(7,3));

        blueDenCell.add(new ChessboardPoint(0,3));
        redDenCell.add(new ChessboardPoint(8,3));


        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint temp = new ChessboardPoint(i, j);
                CellComponent cell;
                if (riverCell.contains(temp)) {
                    cell = new CellComponent(Color.CYAN, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                } else if (redTrapCell.contains(temp)||blueTrapCell.contains(temp)) {
                    cell = new CellComponent(Color.ORANGE, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                } else if (blueDenCell.contains(temp)) {
                    cell = new CellComponent(Color.BLUE, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                }else if(redDenCell.contains(temp)){
                    cell = new CellComponent(Color.RED, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                } else
                {
                    cell = new CellComponent(Color.LIGHT_GRAY, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                }
                gridComponents[i][j] = cell;
            }
        }
    }

    public void registerController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setChessComponentAtGrid(ChessboardPoint point, ChessComponent chess) {
        getGridComponentAt(point).add(chess);
    }

    public ChessComponent removeChessComponentAtGrid(ChessboardPoint point) {
        // Note re-validation is required after remove / removeAll.
        ChessComponent chess = (ChessComponent) getGridComponentAt(point).getComponents()[0];
        getGridComponentAt(point).removeAll();
        getGridComponentAt(point).revalidate();
        chess.setSelected(false);
        return chess;
    }
    public ChessComponent addChessComponent(ChessboardPoint point, ChessPiece piece){
        
        ChessComponent chess = new ChessComponent(
            piece.getOwner(),
            CHESS_SIZE,chessJudge(piece));
        gridComponents[point.getRow()][point.getCol()].add(chess);
        return chess;
        
    }

    private CellComponent getGridComponentAt(ChessboardPoint point) {
        return gridComponents[point.getRow()][point.getCol()];
    }

    private ChessboardPoint getChessboardPoint(Point point) {
        System.out.println("[" + point.y/CHESS_SIZE +  ", " +point.x/CHESS_SIZE + "] Clicked");
        return new ChessboardPoint(point.y/CHESS_SIZE, point.x/CHESS_SIZE);
    }
    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
            if (clickedComponent.getComponentCount() == 0) {
                System.out.print("None chess here and ");
                gameController.onPlayerClickCell(getChessboardPoint(e.getPoint()), (CellComponent) clickedComponent);
            } else {
                System.out.print("One chess here and ");
                gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (ChessComponent) clickedComponent.getComponents()[0]);
            }
        }
    }
}
