package view;

import controller.GameController;
import controller.Trainer;
import model.Chessboard;
import resourcePlayer.FontsManager;
import resourcePlayer.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

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
    private static int theme = 0;

    private JLabel turnLabel = new JLabel();
    private JLabel timeLabel = new JLabel();

    private JLabel backgroundLabel = new JLabel();



    private ChessboardComponent chessboardComponent;
    public ChessGameFrame(int width, int height) {
        setTitle("Jungle Chess"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;
        

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        addChessboard();
        addTurnLabel();
        addTimeLabel();
        setTurnLabel("Turn1: BLUE");
        setTimeLabel("");
        addRestartButton();
        addSaveButton();
        addLoadButton();
        addUndoButton();
        addRedoButton();
        addSettingButton();
        addBackButton();
        addPlayBackButton();
        setBackground("resource\\background\\1.jpg");

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
    private void addTurnLabel() {
        
        turnLabel.setLocation(HEIGTH - 80, HEIGTH / 15);
        turnLabel.setSize(300, 60);
        turnLabel.setFont(FontsManager.getFont(40,1));
        turnLabel.setForeground(Color.BLACK);
        add(turnLabel);
    }
    public void setTurnLabel(String text){
        turnLabel.setText(text);
    }

    private void addTimeLabel() {
        
        timeLabel.setLocation(HEIGTH + 100, HEIGTH / 15);
        timeLabel.setSize(300, 60);
        timeLabel.setFont(FontsManager.getFont(30,1));
        timeLabel.setForeground(Color.BLACK);
        add(timeLabel);
    }
    public void setTimeLabel(String text){
        timeLabel.setText(text);
    }

    private void addRestartButton() {
        JButton button = new JButton("Restart");
        button.setLocation(HEIGTH , HEIGTH / 15 + BUTTON_INTERVAL);
        button.setSize(200, 60);
        button.setFont(FontsManager.getFont(BUTTON_FONT_SIZE,1));
        button.setBackground(Color.LIGHT_GRAY);
        add(button);

        button.addActionListener(e -> {
            restart();
        });  
    }

    public void restart(){
        System.out.println("Click restart");
            dispose();
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
            int AIdepth = gameController.getAIDepth();
            Chessboard.currentTurn = 0;
            GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(), mainFrame, AIdepth);
            mainFrame.setGameController(gameController);
            mainFrame.setVisible(true);
            SettingFrame.getGameFrame(mainFrame);
    }
    
    public void showDialog(String msg){
        JDialog dialog = new JDialog(this, "Jungle", true);
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(400, 200));
        JLabel label = new JLabel(msg, SwingConstants.CENTER);
        label.setFont(FontsManager.getFont(30,1));
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

    private void addButton(String label, int interval, ActionListener listener) {
        JButton button = new JButton(label);
        button.setLocation(HEIGTH, HEIGTH / 15 + BUTTON_INTERVAL * interval);
        button.setSize(200, 60);
        button.setFont(FontsManager.getFont(BUTTON_FONT_SIZE,1));
        button.setBackground(Color.LIGHT_GRAY);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 播放声音
                String soundPath = "resource\\sounds\\click-button.wav"; // 替换为您的声音文件路径
                Sound.playSound(soundPath);
            }
        });
        add(button);
    
        button.addActionListener(listener);
    }
    
    private void addSaveButton(){
        addButton("Save", 2, e -> {
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
        addButton("Load", 3, e -> {
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
        addButton("Undo", 4, e -> {
            System.out.println("Click undo");
            gameController.undo();
        });
    }
    
    private void addRedoButton(){
        addButton("Redo", 5, e -> {
            System.out.println("Click redo");
            gameController.redo();
        });
    }
    
    private void addSettingButton(){
        addButton("Settings", 6, e -> {
            System.out.println("Click settings");
            SettingFrame settingFrame = new SettingFrame();
        });
    }
    
    private void addPlayBackButton(){
        addButton("PlayBack", 7, e -> {
            System.out.println("Click playback");
            Chessboard.currentTurn = 0;
            try {
                gameController.playBack();
            } catch (IOException | InterruptedException e1) {
                e1.printStackTrace();
            }
        });
    }
    
    private void addBackButton(){
        addButton("Back", 8, e -> {
            System.out.println("Click back");
            Chessboard.currentTurn = 0;
            dispose();
            TitleScreen titleScreen = new TitleScreen();
        });
    }

   public void setBackground(String filename){
        if(filename == null){
            
        }
        ImageIcon backgroundImage = new ImageIcon(filename);
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, WIDTH, HEIGTH);

        Image scaledImage = backgroundImage.getImage().getScaledInstance(WIDTH, HEIGTH, Image.SCALE_SMOOTH);
        ImageIcon scaledBackgroundImage = new ImageIcon(scaledImage);
        backgroundLabel.setIcon(scaledBackgroundImage);
        this.add(backgroundLabel);
        System.out.println("set background");
    }

   public void changeTheme(){
        if(theme == 1){
            this.remove(backgroundLabel);
            setBackground("resource\\background\\1.jpg");
        }
        else{
            this.remove(backgroundLabel);
            setBackground("resource\\background\\5.jpg");
        }
        theme = 1 - theme;
        repaint();
   }
public void registerTrainer(Trainer trainer) {
}


}
