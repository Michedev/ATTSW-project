package edu.mikedev.task_manager.ui;

import edu.mikedev.task_manager.Model;

import javax.swing.*;
import java.awt.*;
import net.miginfocom.swing.MigLayout;

public class RegistrationPage extends JPanel {
    private final Model model;
    private JTextField tfUsername;
    private JPasswordField tfPassword;
    private JTextField tfEmail;

    public RegistrationPage(Model model) {
        this.model = model;
        setLayout(new MigLayout("", "[][][][][]", "[][][][][][][][][][][][][]"));

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setName("lblUsername");
        add(lblUsername, "cell 1 1");
        
        tfUsername = new JTextField();
        tfUsername.setName("tfUsername");
        add(tfUsername, "cell 1 3 2 1,growx");
        tfUsername.setColumns(10);
        
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setName("lblPassword");
        add(lblPassword, "cell 1 5");
        
        tfPassword = new JPasswordField();
        tfPassword.setName("tfPassword");
        add(tfPassword, "cell 1 6 2 1,growx");
        
        JLabel lblEmail = new JLabel("E-mail");
        lblEmail.setName("lblEmail");
        add(lblEmail, "cell 1 8");
        
        tfEmail = new JTextField();
        tfEmail.setName("tfEmail");
        add(tfEmail, "cell 1 9 2 1,growx");
        tfEmail.setColumns(10);
        
        JLabel lblErrorMessage = new JLabel("Error message");
        lblErrorMessage.setName("lblErrorMessage");
        lblErrorMessage.setVisible(false);
        lblErrorMessage.setForeground(Color.RED);
        add(lblErrorMessage, "cell 1 10 2 1");
        
        JButton btnRegister = new JButton("Register");
        btnRegister.setName("btnConfirmRegister");
        btnRegister.addActionListener(e ->{
            lblErrorMessage.setVisible(true);
        });
        add(btnRegister, "cell 1 12");
        
        JButton btnCancel = new JButton("Cancel");
        btnCancel.setName("btnCancel");
        add(btnCancel, "cell 2 12");

    }
}
