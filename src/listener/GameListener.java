package listener;


import model.ChessboardPoint;
import view.CellComponent;
import view.ChessComponent;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface GameListener {

    void onPlayerClickCell(ChessboardPoint point, CellComponent component) throws FileNotFoundException;


    void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component) throws IOException;


}
