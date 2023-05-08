package view;

import controller.GameController;
import model.Chessboard;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import resourcePlayer.*;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;
    private final int BUTTON_INTERVAL = 80;
    private final int BUTTON_FONT_SIZE = 30;

    private final int ONE_CHESS_SIZE;
    private GameController gameController;

    private JLabel turnLabel = new JLabel();


    private ChessboardComponent chessboardComponent;
    public ChessGameFrame(int width, int height) {
        setTitle("斗寿棋"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;
        

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        addChessboard();
        addLabel();
        setTurnLabel("Turn1: BLUE");
        addRestartButton();
        addSaveButton();
        addLoadButton();
        addUndoButton();
        addRedoButton();
        addSettingButton();
        addBackButton();

    }
    public void setGameController(GameController gameController){
        this.gameController = gameController;
    }

    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }

    public void setChessboardComponent(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
    }

    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        chessboardComponent.setLocation(HEIGTH / 5, HEIGTH / 10);
        add(chessboardComponent);
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        
        turnLabel.setLocation(HEIGTH, HEIGTH / 10);
        turnLabel.setSize(200, 60);
        turnLabel.setFont(FontsManager.getFont(BUTTON_FONT_SIZE,1));
        add(turnLabel);
    }
    public void setTurnLabel(String text){
        turnLabel.setText(text);
    }

    private void addRestartButton() {
        JButton button = new JButton("Restart");
        button.setLocation(HEIGTH , HEIGTH / 10 + BUTTON_INTERVAL);
        button.setSize(200, 60);
        button.setFont(FontsManager.getFont(BUTTON_FONT_SIZE,1));
       button.setBackground(Color.LIGHT_GRAY);
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click restart");
            dispose();
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
            int AIdepth = gameController.getAIDepth();
            Chessboard.currentTurn = 0;
            GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(), mainFrame, AIdepth);
            mainFrame.setGameController(gameController);
            mainFrame.setVisible(true);
            
        });
        
    }
    
    public void showDialog(String msg){
        JDialog dialog = new JDialog(this, "Jungle", true);
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(400, 200));
        JLabel label = new JLabel(msg, SwingConstants.CENTER);
        label.setFont(FontsManager.getFont(50,1));
        panel.add(label, BorderLayout.CENTER);
        JButton button = new JButton("OK");
        button.setFont(FontsManager.getFont(30,1));
        button.setBackground(Color.LIGHT_GRAY);
        button.addActionListener(e -> {
            dialog.dispose();
        });
        panel.add(button, BorderLayout.SOUTH);
        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void addSaveButton(){
        JButton button = new JButton("Save");
       button.setLocation(HEIGTH, HEIGTH / 10 + BUTTON_INTERVAL * 2);
       button.setSize(200, 60);
       button.setFont(FontsManager.getFont(BUTTON_FONT_SIZE,1));
       button.setBackground(Color.LIGHT_GRAY);
       add(button);

       button.addActionListener(e -> {
           System.out.println("Click load");
           String path = JOptionPane.showInputDialog(this,"Input Path here");
           try {
            gameController.saveGame(path);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
       });
    }

   private void addLoadButton() {
       JButton button = new JButton("Load");
       button.setLocation(HEIGTH, HEIGTH / 10 + BUTTON_INTERVAL * 3);
       button.setSize(200, 60);
       button.setFont(FontsManager.getFont(BUTTON_FONT_SIZE,1));
       button.setBackground(Color.LIGHT_GRAY);
       add(button);

       button.addActionListener(e -> {
           System.out.println("Click load");
           JFileChooser chooser = new JFileChooser();
           chooser.setCurrentDirectory(new File("."));
           chooser.showOpenDialog(chooser);
           File file = chooser.getSelectedFile();
           
           String path = file.toPath().toString();
           try {
            gameController.loadGame(path);
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
       });
   }

   private void addUndoButton(){
       JButton button = new JButton("Undo");
       button.setLocation(HEIGTH, HEIGTH / 10 + BUTTON_INTERVAL * 4);
       button.setSize(200, 60);
       button.setFont(FontsManager.getFont(BUTTON_FONT_SIZE,1));
       button.setBackground(Color.LIGHT_GRAY);
       add(button);

       button.addActionListener(e -> {
           System.out.println("Click undo");
           gameController.undo();
       });
   }

   private void addRedoButton(){
       JButton button = new JButton("Redo");
       button.setLocation(HEIGTH, HEIGTH / 10 + BUTTON_INTERVAL * 5);
       button.setFont(FontsManager.getFont(BUTTON_FONT_SIZE,1));
       button.setBackground(Color.LIGHT_GRAY);
       button.setSize(200, 60);
       add(button);

       button.addActionListener(e -> {
           System.out.println("Click redo");
           gameController.redo();
       });
   }

   private void addSettingButton(){
    JButton button = new JButton("Settings");
        button.setLocation(HEIGTH, HEIGTH / 10 + BUTTON_INTERVAL * 6);
        button.setFont(FontsManager.getFont(BUTTON_FONT_SIZE,1));
        button.setBackground(Color.LIGHT_GRAY);
        button.setSize(200, 60);
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click settings");
            SettingFrame settingFrame = new SettingFrame();
        });
   }

   private void addBackButton(){
    JButton button = new JButton("Back");
        button.setLocation(HEIGTH, HEIGTH / 10 + BUTTON_INTERVAL * 7);
        button.setFont(FontsManager.getFont(BUTTON_FONT_SIZE,1));
        button.setBackground(Color.LIGHT_GRAY);
        button.setSize(200, 60);
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click back");
            dispose();
            TitleScreen titleScreen = new TitleScreen();
        });
   }


}
