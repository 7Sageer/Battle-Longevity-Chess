package controller;

import java.io.IOException;

import listener.GameListener;
import model.*;
import model.Action.Type;
import resourcePlayer.FontsManager;
import resourcePlayer.Sound;
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
public class Trainer {


    private Chessboard model;
    private ChessboardComponent view;
    private PlayerColor currentPlayer = PlayerColor.BLUE;
    private ChessGameFrame game;
    private int AIDepth = 0;
    public static AIModel modelA = new AIModel("modelA");
    public static AIModel modelB = new AIModel("modelB");
    
    //增加了构造器的参数（添加game用于显示对话框）
    public Trainer(ChessboardComponent view, Chessboard model, ChessGameFrame game, int AIDepth) {
        start(view, model, game, AIDepth);
    }

    public void start(ChessboardComponent view, Chessboard model, ChessGameFrame game, int AIDepth) {
        this.view = view;
        this.model = model;
        this.game = game;
        this.currentPlayer = PlayerColor.BLUE;
        this.AIDepth = AIDepth;
        view.registerTrainer(this);
        view.initiateChessComponent(model);
        view.repaint();
        AImove();
    }

    public static void main(String[] args) {
        FontsManager.PixelFonts();
        ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
        mainFrame.setVisible(true);
        AI.setModel(modelA);
        Trainer trainer = new Trainer(mainFrame.getChessboardComponent(), new Chessboard(), mainFrame, 5);
        mainFrame.registerTrainer(trainer);
    }


    // after a valid move swap the player 即下个回合(包含AI)
    private void AImove(){
        view.repaint();
        Action action = AI.findBestAction(model, AIDepth, currentPlayer);
        if(action == null){
            System.out.println("AI failed to find a valid action");
            return;
        }

        if(action.type == Type.MOVE){
            move(action.getFrom(), action.getTo());
        }
        else if(action.type == Type.CAPTURE){
            capture(action.getFrom(), action.getTo());
        }
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
        AI.setModel(currentPlayer == PlayerColor.BLUE ? modelA : modelB);
        //System.out.println(AI.model);

        if(win() == 0){
            AImove();
            view.paintImmediately(0, 0, 1100, 810);
        }else{
            checkWin();
        }
        
        view.repaint();
    }
        

    public int win() {
        if(model.getGrid()[0][3].getPiece()!=null)
            return 2;
        if(model.getGrid()[8][3].getPiece()!=null)
            return 1;
        if(model.getAllValidAction(currentPlayer).isEmpty())
            return currentPlayer == PlayerColor.BLUE ? 2 : 1;
        if(model.currentTurn >= 100){
            return model.evaluateDraw() > 0 ? 1 : 2;
        }
        return 0;
    }
    private void checkWin(){
        int temp = win();
        if (temp == 0) {
            return;
        } else if (temp == 1) {
            System.out.println("Blue Wins!");
            modelB = AIModel.radientDescent(modelB, modelA);
            
            try {
                modelA.saveModel();
                modelB.saveModel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (temp == 2) {
            System.out.println(modelA);
            System.out.println(modelB);
            System.out.println("Red Wins!"); 
            modelA = AIModel.radientDescent(modelA, modelB);
            try {
                modelB.saveModel();
                modelA.saveModel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } 
        game.dispose();
        Chessboard.currentTurn = 0;
        System.out.println(modelA);
        System.out.println(modelB);
        FontsManager.PixelFonts();
        ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
        mainFrame.setVisible(true);
        Trainer trainer = new Trainer(mainFrame.getChessboardComponent(), new Chessboard(), mainFrame, 5);
        mainFrame.registerTrainer(trainer);
    }

    private void capture(ChessboardPoint from, ChessboardPoint to){
        model.captureChessPiece(from, to);
        view.removeChessComponentAtGrid(to);
        view.setChessComponentAtGrid(to, view.removeChessComponentAtGrid(from));
        //view.repaint();
    }

    
    public void move(ChessboardPoint from, ChessboardPoint to){

        model.moveChessPiece(from, to);
        view.setChessComponentAtGrid(to, view.removeChessComponentAtGrid(from));
        //view.repaint();
    }
}




    // public void saveGame(String path) throws IOException {
    //     File file = new File(path);
    //     FileWriter writer = new FileWriter(file);
    //     for (int i = 0; i < Chessboard.historyAction.size(); i++) {
    //         writer.write(Chessboard.historyAction.get(i).toString());
    //         //System.out.println(Chessboard.historyAction.get(i));
    //         writer.write("\r");
    //     }
    //     writer.close();
        
    // }
    // public void loadGame(String path) throws IOException, InterruptedException {
    //     System.out.println(extractActions(path));
    //     if(extractActions(path).size() == 0){
    //         game.showDialog("Error:Invalid file path or no action");
    //         return;
    //     }
    //     this.model.initialize();
    //     this.view.initiateChessComponent(model);
    //     Chessboard.currentTurn = 0;
        
    //     ArrayList<Action> loadAction = extractActions(path);
    //     for (int i = 0; i < loadAction.size(); i++) {
    //         if (loadAction.get(i).getType() == Type.MOVE){
    //             model.moveChessPiece(loadAction.get(i).getFrom(), loadAction.get(i).getTo());
    //             view.setChessComponentAtGrid(loadAction.get(i).getTo(), view.removeChessComponentAtGrid(loadAction.get(i).getFrom()));

    //         }else if(loadAction.get(i).getType() == Type.CAPTURE){
    //             model.captureChessPiece(loadAction.get(i).getFrom(), loadAction.get(i).getTo());
    //             view.removeChessComponentAtGrid(loadAction.get(i).getTo());
    //             view.setChessComponentAtGrid(loadAction.get(i).getTo(), view.removeChessComponentAtGrid(loadAction.get(i).getFrom()));                
    //         }else{
    //             game.showDialog("Error:Invalid File");
    //             return;
    //         }
    //         if(i != loadAction.size()-1)
    //             swapColor(false);
    //         view.paintImmediately(0,0,3000,3000);
    //         Thread.sleep(300);
    //         view.repaint();
            
    //     }
        

    // }

    // public void playBack() throws IOException, InterruptedException{
    //     loadGame("temp.txt");
    // }

    // public static ArrayList<Action> extractActions(String filePath) throws IOException {
    //     ArrayList<Action> actions = new ArrayList<>();
    //     File file = new File(filePath);
    //     FileReader fr = new FileReader(file);
    //     BufferedReader br = new BufferedReader(fr);
    
    //     StringBuilder sb = new StringBuilder();
    //     char[] buf = new char[8192]; 
    //     int len; 
    //     while((len = br.read(buf)) != -1) {
    //         sb.append(buf, 0, len); 
    //     }    
    //     String content = sb.toString();
    //     Pattern pattern = Pattern.compile("\\{from=\\((\\d+),(\\d+)\\) ,to=\\((\\d+),(\\d+)\\) ,chessPiece=(RED|BLUE) (\\w+)\\b,type=(MOVE|CAPTURE)(?:,capturedChessPiece=(RED|BLUE) (\\w+))?\\}");
        
    //     Matcher matcher = pattern.matcher(content);

    //     while (matcher.find()) {
    //         int fromX = Integer.parseInt(matcher.group(1));
    //         int fromY = Integer.parseInt(matcher.group(2));
    //         int toX = Integer.parseInt(matcher.group(3));
    //         int toY = Integer.parseInt(matcher.group(4));
    //         PlayerColor color = matcher.group(5).equals("BLUE") ? PlayerColor.BLUE : PlayerColor.RED;
    //         if(matcher.group(7).toUpperCase().equals("MOVE"))
    //             actions.add(new Action(new ChessboardPoint(fromX, fromY), new ChessboardPoint(toX, toY), Type.MOVE));
    //         else
    //             actions.add(new Action(new ChessboardPoint(fromX, fromY), new ChessboardPoint(toX, toY), Type.CAPTURE));
            
    //     }

    //     br.close();


    //     return actions;
    // }

    // public void undo() {
    //     int i = 1;
    //     if(AIDepth != 0&&Chessboard.currentTurn!=1)
    //         i = 2;
    //     for(int j = 0; j < i; j++){
    //         if (Chessboard.historyAction.size() > 0) {
    //             Action action = Chessboard.historyAction.get(Chessboard.currentTurn - 1);
    //             if (action.getType() == Type.MOVE) {
    //                 model.moveChessPiece(action.getTo(), action.getFrom(),true);
    //                 view.setChessComponentAtGrid(action.getFrom(), view.removeChessComponentAtGrid(action.getTo()));
    //             } else {
    //                 System.out.println(action);
    //                 model.moveChessPiece(action.getTo(), action.getFrom(),true);
    //                 model.setChessPiece(action.getTo(),action.getCapturedChessPiece());
    //                 view.setChessComponentAtGrid(action.getFrom(), view.removeChessComponentAtGrid(action.getTo()));
    //                 view.setChessComponentAtGrid(action.getTo(), view.addChessComponent(action.getTo(),action.getCapturedChessPiece()));
                    
    //             }
    //             Chessboard.currentTurn -= 2;
    //             swapColor(false);
    //             view.repaint();
    //             Chessboard.historyAction.remove(Chessboard.historyAction.size()-1);
    //             System.out.println(Chessboard.historyAction);
    
                
    //         }
    //     }
    // }

    // public void redo(){
    //     int i = 1;
    //     if(AIDepth != 0)
    //         i = 2;
    //     for(int j = 0; j < i; j++){
    //         if(Chessboard.historyAction.size() - 1 > Chessboard.currentTurn){
    //             Action action = Chessboard.historyAction.get(Chessboard.currentTurn);
    //             if (action.getType() == Type.MOVE) {
    //                 model.moveChessPiece(action.getFrom(), action.getTo(),true);
    //                 view.setChessComponentAtGrid(action.getTo(), view.removeChessComponentAtGrid(action.getFrom()));
    //             } else {
    //                 model.captureChessPiece(action.getFrom(), action.getTo(),true);
    //                 view.removeChessComponentAtGrid(action.getTo());
    //                 view.setChessComponentAtGrid(action.getTo(), view.removeChessComponentAtGrid(action.getFrom()));
    //             }
    //             swapColor(false);
    //             view.repaint();
    //         }
    // }
    // }
    // public int getAIDepth(){return AIDepth;}

