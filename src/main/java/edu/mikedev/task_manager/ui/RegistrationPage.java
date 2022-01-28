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
        setLayout(new MigLayout("", "[][][][][grow][]", "[][][][][][][][][][][][]"));
        
        JLabel lblUsername = new JLabel("Username");
        lblUsername.setName("lblUsername");
        add(lblUsername, "cell 4 2");
        
        tfUsername = new JTextField();
        tfUsername.setName("tfUsername");
        add(tfUsername, "cell 4 3 2 1,growx");
        tfUsername.setColumns(10);
        
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setName("lblPassword");
        add(lblPassword, "cell 4 5");
        
        tfPassword = new JPasswordField();
        tfPassword.setName("tfPassword");
        add(tfPassword, "cell 4 6 2 1,growx");
        
        JLabel lblEmail = new JLabel("E-mail");
        lblEmail.setName("lblEmail");
        add(lblEmail, "cell 4 8");
        
        tfEmail = new JTextField();
        tfEmail.setName("tfEmail");
        add(tfEmail, "cell 4 9 2 1,growx");
        tfEmail.setColumns(10);
        
        JButton btnRegister = new JButton("Register");
        btnRegister.setName("btnRegister");
        add(btnRegister, "cell 4 11");
        
        JButton btnCancel = new JButton("Cancel");
        btnCancel.setName("btnCancel");
        add(btnCancel, "cell 5 11");

    }
}
