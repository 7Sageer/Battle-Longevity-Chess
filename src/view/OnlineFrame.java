package view;

import java.awt.TextField;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class OnlineFrame extends CommonFrame {
    public OnlineFrame() {
        super();
        setTitle("Input IP");
        setSize(500, 200);            
        setLocationRelativeTo(null); // 把窗口设在屏幕中央
    }
    @Override
    protected void addComponent(JPanel panel){
        addLabel(panel, "Input IP", 30);
        
        JTextField ipTextField = new JTextField();
        addTextField(panel, ipTextField, 20);

        JButton confirmButton = new JButton("Confirm");
        addButton(panel, confirmButton, 200, 50, 30, e -> {
            String ip = ipTextField.getText();
            System.out.println(ip);
            
            dispose();
        });
    }

}
