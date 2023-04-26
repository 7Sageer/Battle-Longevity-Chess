package controller;

import java.io.*;
import java.nio.*;
import java.util.*;
import java.util.regex.*;

import listener.GameListener;
import model.Constant;
import model.PlayerColor;
import model.Action.Type;
import model.Chessboard;
import model.ChessboardPoint;
import model.Action;
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
        game.setTurnLabel("Turn: " + currentPlayer);
    }

    //这里原本返回值是布尔型
    private int win() {
        // TODO: Check the board if there is a winner
        if(model.getGrid()[0][3].getPiece()!=null)
            return 2;
        
        if(model.getGrid()[8][3].getPiece()!=null)
            return 1;
        if(model.getAllValidAction(currentPlayer).isEmpty())
            return currentPlayer == PlayerColor.BLUE ? 2 : 1;
        
        return 0;
    }


    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
        if (selectedPoint != null && model.isValidMove(selectedPoint, point)) {
            model.moveChessPiece(selectedPoint, point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            selectedPoint = null;
            swapColor();
            int temp = win();
            view.repaint();
            if (temp == 1) {
                System.out.println("Blue Win!");
                game.showDialog("Blue Win!");
            } else if (temp == 2) {
                System.out.println("Red Win!");
                game.showDialog("Red Win!");
            }
            
            
            // TODO: if the chess enter Dens or Traps and so on
            //写在chessBoard的isValidCaptured和win里面
            
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
            int temp = win();
            view.repaint();
            if (temp == 1) {
                System.out.println("Blue Win!");
                game.showDialog("Blue Win!");
            } else if (temp == 2) {
                System.out.println("Red Win!");
                game.showDialog("Red Win!");
            }
            
        }
        // TODO: Implement capture function  做完了

    }
    public void saveGame(String path) throws IOException {
        File file = new File(path);
        FileWriter writer = new FileWriter(file);
        for (int i = 0; i < Chessboard.historyAction.size(); i++) {
            writer.write(Chessboard.historyAction.get(i).toString());
            System.out.println(Chessboard.historyAction.get(i));
            writer.write("\r");
        }
    writer.close();
        
    }
    public void loadGame(String path) throws IOException, InterruptedException {
        System.out.println(extractActions(path));
        this.model.initialize();
        this.view.initiateChessComponent(model);
        
        ArrayList<Action> loadAction = extractActions(path);
        for (int i = 0; i < loadAction.size(); i++) {
            if (loadAction.get(i).getType() == Type.MOVE){
                model.moveChessPiece(loadAction.get(i).getFrom(), loadAction.get(i).getTo());
                view.setChessComponentAtGrid(loadAction.get(i).getTo(), view.removeChessComponentAtGrid(loadAction.get(i).getFrom()));

            }else{
                model.captureChessPiece(loadAction.get(i).getFrom(), loadAction.get(i).getTo());
                view.removeChessComponentAtGrid(loadAction.get(i).getTo());
                view.setChessComponentAtGrid(loadAction.get(i).getTo(), view.removeChessComponentAtGrid(loadAction.get(i).getFrom()));
                }
            swapColor();
            view.repaint();
            
        }

    }
    public static ArrayList<Action> extractActions(String filePath) throws IOException {
        ArrayList<Action> actions = new ArrayList<>();
        File file = new File(filePath);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
    
        StringBuilder sb = new StringBuilder();
        char[] buf = new char[8192]; 
        int len; 
        while((len = br.read(buf)) != -1) {
            sb.append(buf, 0, len); 
        }    
        String content = sb.toString();
        //String patternString = "\{from=\((\d+),(\d+)\) ,to=\((\d+),(\d+)\) ,chessPiece=(RED|BLUE) (?:\w+) ,type=(MOVE|CAPTURE)(?:,capturedChessPiece=(RED|BLUE) (?:\w+))?\}";

        Pattern pattern = Pattern.compile("\\{from=\\((\\d+),(\\d+)\\) ,to=\\((\\d+),(\\d+)\\) ,chessPiece=(RED|BLUE) (\\w+)\\b,type=(MOVE|CAPTURE)(?:,capturedChessPiece=(RED|BLUE) (\\w+))?\\}");
        
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            int fromX = Integer.parseInt(matcher.group(1));
            int fromY = Integer.parseInt(matcher.group(2));
            int toX = Integer.parseInt(matcher.group(3));
            int toY = Integer.parseInt(matcher.group(4));
            PlayerColor color = matcher.group(5).equals("BLUE") ? PlayerColor.BLUE : PlayerColor.RED;
            if(matcher.group(7).toUpperCase().equals("MOVE"))
                actions.add(new Action(new ChessboardPoint(fromX, fromY), new ChessboardPoint(toX, toY), Type.MOVE));
            else
                actions.add(new Action(new ChessboardPoint(fromX, fromY), new ChessboardPoint(toX, toY), Type.CAPTURE));
            
        }

        br.close();


        return actions;
    }

    public void undo() {
        if (Chessboard.historyAction.size() > 0) {
            Action action = Chessboard.historyAction.get(Chessboard.historyAction.size() - 1);
            if (action.getType() == Type.MOVE) {
                model.moveChessPiece(action.getTo(), action.getFrom());
                view.setChessComponentAtGrid(action.getFrom(), view.removeChessComponentAtGrid(action.getTo()));
            } else {
                model.moveChessPiece(action.getTo(), action.getFrom());
                model.setChessPiece(action.getTo(),action.getCapturedChessPiece());
                view.setChessComponentAtGrid(action.getFrom(), view.removeChessComponentAtGrid(action.getTo()));
                view.setChessComponentAtGrid(action.getTo(), view.addChessComponent(action.getTo(),action.getCapturedChessPiece()));
                
            }
            swapColor();
            view.repaint();
            Chessboard.historyAction.remove(Chessboard.historyAction.size() - 1);
            Chessboard.historyAction.remove(Chessboard.historyAction.size() - 1);
        }
    }
}
