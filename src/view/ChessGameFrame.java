package view;

import controller.GameController;
import model.Chessboard;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;

    private final int ONE_CHESS_SIZE;
    private GameController gameController;


    private ChessboardComponent chessboardComponent;
    public ChessGameFrame(int width, int height) {
        setTitle("2023 CS109 Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;
        

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        addChessboard();
        addLabel();
        addRestartButton();
        addSaveButton();
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
        JLabel statusLabel = new JLabel("Sample label");
        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addHelloButton() {
        JButton button = new JButton("Show Hello Here");
        button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Hello, world!"));
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addRestartButton() {
        JButton button = new JButton("Restart");
        button.setLocation(HEIGTH , HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click restart");
            dispose();
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
            GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(), mainFrame);
            mainFrame.setGameController(gameController);
            mainFrame.setVisible(true);
            
        });
        
    }
    
    public void showWinDialog(String player){
        JOptionPane.showMessageDialog(this, player+" win!");
    }

    private void addSaveButton(){
        JButton button = new JButton("Save");
       button.setLocation(HEIGTH, HEIGTH / 10 + 240);
       button.setSize(200, 60);
       button.setFont(new Font("Rockwell", Font.BOLD, 20));
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

//    private void addLoadButton() {
//        JButton button = new JButton("Load");
//        button.setLocation(HEIGTH, HEIGTH / 10 + 240);
//        button.setSize(200, 60);
//        button.setFont(new Font("Rockwell", Font.BOLD, 20));
//        add(button);

//        button.addActionListener(e -> {
//            System.out.println("Click load");
//            String path = JOptionPane.showInputDialog(this,"Input Path here");
//            gameController.loadGameFromFile(path);
//        });
//    }


}
