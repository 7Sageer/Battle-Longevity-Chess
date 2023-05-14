package view;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.HeadlessException;

import javax.swing.Icon;

import resourcePlayer.FontsManager;

import javax.swing.*;

public class PixelOptionPane extends JOptionPane {
        public static void showMessageDialog(String message, String title, int messageType,int fontSize) {
            JDialog dialog = new JDialog();
            dialog.setTitle(title);
            dialog.setModal(true);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setBackground(new Color(51, 97, 129));
            dialog.setPreferredSize(new Dimension(400, 300));
            
            JLabel label = new JLabel(message);
            
            label.setFont(FontsManager.getFont(fontSize,1));
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            dialog.add(label);

            JButton button = new JButton("OK");
            button.setFont(FontsManager.getFont(20,1));
            button.setPreferredSize(new Dimension(200, 80));
            button.setBackground(Color.LIGHT_GRAY);
            button.addActionListener(e -> dialog.dispose());
            dialog.add(button, "South");

            
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);


        }

        public static void main(String[] args) {
            FontsManager.PixelFonts();
            new PixelOptionPane().showMessageDialog("Hello", "Title", JOptionPane.INFORMATION_MESSAGE,40);
        }

        
}
