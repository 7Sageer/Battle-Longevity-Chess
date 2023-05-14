package view;

import javax.swing.*;

import model.User;
import model.UserAdministrator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import resourcePlayer.FontsManager;

public class UserFrame extends CommonFrame{

    public UserFrame(){
        super();
        UserAdministrator.loadData();

    }

    @Override
    protected void addComponent(JPanel panel){
        JLabel titleLabel = addLabel(panel, "Welcome Guest", 30 );
        
        if(UserAdministrator.getCurrentUser() != null)
            titleLabel.setText("Welcome " + UserAdministrator.getCurrentUser().getUsername());
        

        JButton loginButton;
        if(UserAdministrator.getCurrentUser() != null)
            loginButton = new JButton("Logout");
        else
            loginButton = new JButton("Login");
        addButton(panel, loginButton, 100, 40, 30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(UserAdministrator.getCurrentUser() != null){
                    UserAdministrator.logout();
                    JOptionPane.showMessageDialog(null, "Logout success","success", JOptionPane.INFORMATION_MESSAGE);
                    titleLabel.setText("Welcome Guest");
                    loginButton.setText("Login");
                    return;
                }
                JTextField usernameField = new JTextField();
                JPasswordField passwordField = new JPasswordField();
                Object[] message = {
                        "Username:", usernameField,
                        "Password:", passwordField
                };
                int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    String username = usernameField.getText();
                    String password = new String(passwordField.getPassword());
                    User user = UserAdministrator.login(username, password);
                    if(user == null){
                        JOptionPane.showMessageDialog(null, "Wrong password","error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }else{
                        JOptionPane.showMessageDialog(null, String.format("Login success\n") + user.toString(),"success", JOptionPane.INFORMATION_MESSAGE);
                        loginButton.setText("Logout");
                        titleLabel.setText("Welcome " + UserAdministrator.getCurrentUser().getUsername());
                    }
                }
            }

        });

        JButton rankButton = new JButton("Rank");
        addButton(panel, rankButton, 100, 40, 40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<User> user = UserAdministrator.rank();
                String[][] data = new String[user.size()][5];  
                int j = -1;
                for (int i = 0; i < user.size(); i++) {
                    if(UserAdministrator.getCurrentUser() != null&&user.get(i).getUsername().equals(UserAdministrator.getCurrentUser().getUsername()))
                        j = i;
                    data[i] = new String[]{String.valueOf(i + 1), user.get(i).getUsername(), String.valueOf(user.get(i).getScore()), String.valueOf(user.get(i).getWin()), String.valueOf(user.get(i).getLose())};
                }
                JTable table = new JTable(data, new String[]{"Rank", "Username", "Score", "Win", "Lose"});
                if(j > -1)
                    table.setRowSelectionInterval(j, j);
                JOptionPane.showMessageDialog(null, new JScrollPane(table), "Rank", JOptionPane.PLAIN_MESSAGE);
        }
    });
    
        JButton backButton = new JButton("back");

        addButton(panel, backButton, 100, 40, 40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

    }

}