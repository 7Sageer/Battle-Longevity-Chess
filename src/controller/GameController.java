package controller;

import listener.GameListener;
import model.*;
import model.Action.Type;
import resourcePlayer.Sound;
import view.CellComponent;
import view.ChessComponent;
import view.ChessGameFrame;
import view.ChessboardComponent;

import java.io.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private PlayerColor currentPlayer = PlayerColor.BLUE;
    private ChessGameFrame game;
    private int AIDepth = 0;
    private PlayerColor AIcolor;
    public static boolean isTimer = false;
    private boolean isGameOver = false;


    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;

    //增加了构造器的参数（添加game用于显示对话框）
    public GameController(ChessboardComponent view, Chessboard model, ChessGameFrame game, int AIDepth) {
        start(view, model, game, AIDepth);
    }

    public void start(ChessboardComponent view, Chessboard model, ChessGameFrame game, int AIDepth) {
        this.view = view;
        this.model = model;
        this.game = game;
        this.currentPlayer = PlayerColor.BLUE;
        this.AIDepth = AIDepth;
        view.registerController(this);
        initialize();
        view.initiateChessComponent(model);
        view.repaint();
        if (AIDepth != 0) {
            //random color
            // if (Math.random() > 0.5) {
                AIcolor = PlayerColor.BLUE;
                game.showDialog("You are Red, AI is Blue");
                AImove();
            // } else {
            //     AIcolor = PlayerColor.RED;
            //     game.showDialog("You are Blue, AI is Red");
            // }
        }

    }

    private void initialize() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                //目前没想到怎么用，用于读档？
                //读档写完了，好像还是没用
            }
        }
    }

    // after a valid move swap the player 即下个回合(包含AI)
    protected void swapColor() {
        checkWin();
        if (isGameOver)
            return;
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;

        if((!(AIDepth != 0 && currentPlayer == AIcolor))){
            if(isTimer){
                game.setTimeLabel(String.format("Time Left: %ds", 30));
                testTimer(30, currentPlayer,Chessboard.currentTurn);
            }
        }
        

        view.removePossibleMove();
        game.setTurnLabel("Turn" + Chessboard.currentTurn + ": "  + currentPlayer);
        //view.paintImmediately(-1000, -1000, 3000, 3000);

        if(AIDepth != 0){
            if(currentPlayer == AIcolor){
                AImove();
            }
        }
        view.repaint();
        try {
            saveGame("temp.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void AImove(){
        //view.repaint();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Action action = AI.findBestAction(model, AIDepth, AIcolor);
                if(action.type == Type.MOVE){
                    move(action.getFrom(), action.getTo());
                }
                else if(action.type == Type.CAPTURE){
                    capture(action.getFrom(), action.getTo());
                }
                currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
                swapColor();
                //view.repaint();
            }
        });
        t.start();

    }

    //重载一个没有AI的swapColor
    private void swapColor(boolean AI){
        checkWin();
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
        view.removePossibleMove();
        game.setTurnLabel("Turn" + Chessboard.currentTurn + ": "  + currentPlayer);
        view.repaint();
    }

    public int win() {
        if(model.getGrid()[0][3].getPiece()!=null)
            return 2;
        if(model.getGrid()[8][3].getPiece()!=null)
            return 1;
        if(model.getAllValidAction(currentPlayer).isEmpty())
            return currentPlayer == PlayerColor.BLUE ? 2 : 1;
        return 0;
    }
    private void checkWin(){
        int temp = win();
        if (temp == 1) {
            Sound.playSound("resource\\sounds\\winsquare.wav");
            System.out.println("Blue Win!");
            game.showDialog("Blue Win!");
            if(AIcolor == PlayerColor.BLUE&&AIDepth!=0){
                UserAdministrator.lose();
            }
            else if(AIcolor == PlayerColor.RED&&AIDepth!=0){
                UserAdministrator.win();
            }
            isGameOver = true;

        } else if (temp == 2) {
            Sound.playSound("resource\\sounds\\winsquare.wav");
            System.out.println("Red Win!");
            game.showDialog("Red Win!");
            if(AIcolor == PlayerColor.BLUE&&AIDepth!=0){
                UserAdministrator.win();
            }
            else if(AIcolor == PlayerColor.RED&&AIDepth!=0){
                UserAdministrator.lose();
            }
            isGameOver = true;
        }
    }

    private void capture(ChessboardPoint from, ChessboardPoint to){
        Sound.playSound("resource\\sounds\\capture.wav");
        model.captureChessPiece(from, to);
        view.removeChessComponentAtGrid(to);
        view.setChessComponentAtGrid(to, view.removeChessComponentAtGrid(from));
        selectedPoint = null;
        swapColor();
        //view.repaint();
    }

    
    public void move(ChessboardPoint from, ChessboardPoint to){

        Sound.playSound("resource\\sounds\\lawn.wav");
        model.moveChessPiece(from, to);
        view.setChessComponentAtGrid(to, view.removeChessComponentAtGrid(from));
        selectedPoint = null;
        swapColor();
        //view.repaint();
    }


    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
        if (selectedPoint != null) {
            if (model.isValidMove(selectedPoint, point)) {
                move(selectedPoint, point);
            }
        }
    }

    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component) {
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                selectedPoint = point;
                //
                ArrayList<Action> validAction = model.getValidAction(point);
                ArrayList<ChessboardPoint> validPoint = new ArrayList<ChessboardPoint>();
                for (int i = 0; i < validAction.size(); i++) {
                    validPoint.add(validAction.get(i).getTo());
                }
                System.out.println(validPoint);
                view.renderPossibleMove(validPoint);
                //
                component.setSelected(true);
                component.repaint();
            }
        } else if (selectedPoint.equals(point)) {
            selectedPoint = null;
            view.removePossibleMove();
            component.setSelected(false);
            component.repaint();
        } else {
            if(model.isValidCapture(selectedPoint, point))
                capture(selectedPoint, point);
        }

    }



    public void saveGame(String path) throws IOException {
        File file = new File(path);
        FileWriter writer = new FileWriter(file);
        for (int i = 0; i < Chessboard.historyAction.size(); i++) {
            writer.write(Chessboard.historyAction.get(i).toString());
            //System.out.println(Chessboard.historyAction.get(i));
            writer.write("\r");
        }
        writer.close();
        
    }
    public void loadGame(String path) throws IOException, InterruptedException {
        System.out.println(extractActions(path));
        if(extractActions(path).size() == 0){
            game.showDialog("Error:Invalid file path or no action");
            return;
        }
        this.model.initialize();
        this.view.initiateChessComponent(model);
        Chessboard.currentTurn = 0;
        
        ArrayList<Action> loadAction = extractActions(path);
        for (int i = 0; i < loadAction.size(); i++) {
            if (loadAction.get(i).getType() == Type.MOVE){
                model.moveChessPiece(loadAction.get(i).getFrom(), loadAction.get(i).getTo());
                view.setChessComponentAtGrid(loadAction.get(i).getTo(), view.removeChessComponentAtGrid(loadAction.get(i).getFrom()));

            }else if(loadAction.get(i).getType() == Type.CAPTURE){
                model.captureChessPiece(loadAction.get(i).getFrom(), loadAction.get(i).getTo());
                view.removeChessComponentAtGrid(loadAction.get(i).getTo());
                view.setChessComponentAtGrid(loadAction.get(i).getTo(), view.removeChessComponentAtGrid(loadAction.get(i).getFrom()));                
            }else{
                game.showDialog("Error:Invalid File");
                return;
            }
            if(i != loadAction.size()-1)
                swapColor(false);
            view.paintImmediately(0,0,3000,3000);
            Thread.sleep(300);
            view.repaint();
            
        }
        

    }

    public void playBack() throws IOException, InterruptedException{
        loadGame("temp.txt");
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
        int i = 1;
        if(AIDepth != 0&&Chessboard.currentTurn!=1)
            i = 2;
        for(int j = 0; j < i; j++){
            if (Chessboard.historyAction.size() > 0) {
                Action action = Chessboard.historyAction.get(Chessboard.currentTurn - 1);
                if (action.getType() == Type.MOVE) {
                    model.moveChessPiece(action.getTo(), action.getFrom(),true);
                    view.setChessComponentAtGrid(action.getFrom(), view.removeChessComponentAtGrid(action.getTo()));
                } else {
                    model.moveChessPiece(action.getTo(), action.getFrom(),true);
                    model.setChessPiece(action.getTo(),action.getCapturedChessPiece());
                    view.setChessComponentAtGrid(action.getFrom(), view.removeChessComponentAtGrid(action.getTo()));
                    view.setChessComponentAtGrid(action.getTo(), view.addChessComponent(action.getTo(),action.getCapturedChessPiece()));
                    
                }
                Chessboard.currentTurn -= 2;
                swapColor(false);
                view.repaint();
                Chessboard.historyAction.remove(Chessboard.historyAction.size()-1);
                System.out.println(Chessboard.historyAction);
    
                
            }
        }
    }

    public void redo(){
        int i = 1;
        if(AIDepth != 0)
            i = 2;
        for(int j = 0; j < i; j++){
            if(Chessboard.historyAction.size() - 1 > Chessboard.currentTurn){
                Action action = Chessboard.historyAction.get(Chessboard.currentTurn);
                if (action.getType() == Type.MOVE) {
                    model.moveChessPiece(action.getFrom(), action.getTo(),true);
                    view.setChessComponentAtGrid(action.getTo(), view.removeChessComponentAtGrid(action.getFrom()));
                } else {
                    model.captureChessPiece(action.getFrom(), action.getTo(),true);
                    view.removeChessComponentAtGrid(action.getTo());
                    view.setChessComponentAtGrid(action.getTo(), view.removeChessComponentAtGrid(action.getFrom()));
                }
                swapColor(false);
                view.repaint();
            }
    }
    }
    public int getAIDepth(){return AIDepth;}



    public void testTimer(int second, PlayerColor player, int turn){//if no input, time default limit to 30s
        if(currentPlayer != player||Chessboard.currentTurn > turn + 1||isTimer == false){
            return;
        }
        
        if (second == 0){
            swapColor();
            return;
        }
        
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            
            @Override
            public void run() {//build a thread
                if(currentPlayer != player||Chessboard.currentTurn > turn + 1){
                    return;
                }
                if (second == 0){
                    swapColor();
                    return;
                }
                game.setTimeLabel(String.format("Time Left: %ds", second - 1));
                testTimer(second - 1, player, turn);
            }
        }, 1000);
    }



public Chessboard getModel() {//getter and setter，下面都是
        return model;
    }

    public void setModel(Chessboard model) {
        this.model = model;
    }

    public ChessboardComponent getView() {
        return view;
    }

    public void setView(ChessboardComponent view) {
        this.view = view;
    }

    public void setCurrentPlayer(PlayerColor currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public ChessGameFrame getGame() {
        return game;
    }

    public void setGame(ChessGameFrame game) {
        this.game = game;
    }

    public void setAIDepth(int AIDepth) {
        this.AIDepth = AIDepth;
    }

    public PlayerColor getAIcolor() {
        return AIcolor;
    }

    public void setAIcolor(PlayerColor AIcolor) {
        this.AIcolor = AIcolor;
    }

    public ChessboardPoint getSelectedPoint() {
        return selectedPoint;
    }

    public void setSelectedPoint(ChessboardPoint selectedPoint) {
        this.selectedPoint = selectedPoint;
    }
}
