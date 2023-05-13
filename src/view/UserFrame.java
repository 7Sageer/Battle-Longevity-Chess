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

public class UserFrame extends JFrame{

    public UserFrame(){
        super("Jungle Chess");
        UserAdministrator.loadData();

        JPanel panel = new JPanel(new GridLayout(0, 1,5,10));
        panel.setBackground(new Color(236, 242, 246));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel titleLabel = new JLabel("Settings");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(FontsManager.getFont(20,1));

        this.addComponent(panel);
        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }
    private void addComponent(JPanel panel){
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
                    }else
                        JOptionPane.showMessageDialog(null, String.format("Login success\n") + user.toString(),"success", JOptionPane.INFORMATION_MESSAGE);
                        loginButton.setText("Logout");
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

    private JLabel addLabel(JPanel panel, String text, int fontSize, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(FontsManager.getFont(fontSize,1));
        label.setForeground(color);
        panel.add(label);
        return label;
    }

    private JSlider addSlider(JPanel panel, int orientation, int min, int max, int value, javax.swing.event.ChangeListener listener) {
        JSlider slider = new JSlider(orientation, min, max, value);
        slider.addChangeListener(listener);
        panel.add(slider);
        return slider;
    }
    private JComboBox<String> addComboBox(JPanel panel, String string, int fontSize, Color color, javax.swing.event.ChangeListener listener){
        JComboBox<String> comboBox = new JComboBox<>(new String[]{string});
        comboBox.setFont(FontsManager.getFont(fontSize,1));
        comboBox.setForeground(color);
        panel.add(comboBox);
        return comboBox;
    }
    private void addButton(JPanel panel, JButton button, int width, int height, int fontSize, ActionListener listener) {
        button.setPreferredSize(new Dimension(width, height));
        button.setBackground(Color.LIGHT_GRAY);
        button.setFont(FontsManager.getFont(fontSize,1));
        button.addActionListener(listener);
        panel.add(button);
    }
}