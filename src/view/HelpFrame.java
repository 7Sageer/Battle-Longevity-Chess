package view;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import resourcePlayer.FontsManager;

public class HelpFrame extends CommonFrame{
        
    public HelpFrame(){
        super();
    }

    @Override
    protected void addComponent(JPanel panel){
        String text ="- Jungle Chess is a modern Chinese board game played on a 7x9 board.\n- The game is popular among children in the Far East and is a two-player strategy game similar to the Western game Stratego.\n - The objective of the game is to either occupy the opponent's den or capture all of their animal pieces.\n - There are eight different animal pieces, each with a specific rank, and higher-ranked animals can capture lower-ranked or equal-ranked animals.\n - There is an exception for a special case involving the elephant and the rat.\n - The game board has three special terrains: den, trap, and river. Animals cannot enter their own den, traps temporarily reduce an animal's rank.\n - And only the rat can enter the river while the lion and tiger can jump over it.\n - At the start of the game, all 16 pieces are placed on the board.\n - Players take turns moving their pieces to adjacent empty spaces or capturing the opponent's pieces, following certain rules such as not moving into their own den and the limitations of the river and trap.\n - The game can be won by either occupying the opponent's den or capturing all of their pieces.\n - After the game ends, players have the option to restart or exit the game.";

        JTextArea textArea = new JTextArea(text,10, 80);
        textArea.setFont(FontsManager.getFont(20, 1));
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane);
        textArea.setEditable(false);

        JPanel buttonPanel = new JPanel();
        


        JButton OK = new JButton("Play Now!");
        addButton(buttonPanel, OK, 200, 100, 40, new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                dispose();
            }
        });
        buttonPanel.add(new JLabel());
        buttonPanel.add(new JLabel());
        ImageIcon imageIcon = new ImageIcon("resource\\chesspiece\\鼠.png");
        JLabel imageLabel = new JLabel(imageIcon);
        buttonPanel.add(imageLabel, BorderLayout.SOUTH);

        panel.add(buttonPanel);
        
    }
}
