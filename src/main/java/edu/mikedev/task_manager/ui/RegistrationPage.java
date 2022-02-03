package edu.mikedev.task_manager.ui;

import edu.mikedev.task_manager.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;

import net.miginfocom.swing.MigLayout;

public class RegistrationPage extends JPanel {
    private final transient Model model;
    private JLabel lblErrorMessagePassword;
    private JLabel lblErrorMessageEmail;
    private JButton btnRegister;
    private JButton btnCancel;
    private JLabel lblErrorMessageUsername;
    private JTextField tfUsername;
    private JPasswordField tfPassword;
    private JTextField tfEmail;

    public RegistrationPage(Model model) {
        this.model = model;
        setLayout(new MigLayout("", "[][][][][]", "[][][][][][][][][][][][][][][]"));

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setName("lblUsername");
        add(lblUsername, "cell 1 1");
        
        tfUsername = new JTextField();
        tfUsername.setName("tfUsername");
        add(tfUsername, "cell 1 3 2 1,growx");
        tfUsername.setColumns(10);

        lblErrorMessageUsername = new JLabel("Error message");
        lblErrorMessageUsername.setName("lblErrorMessageUsername");
        lblErrorMessageUsername.setVisible(false);
        lblErrorMessageUsername.setForeground(AppColors.RED);
        add(lblErrorMessageUsername, "cell 1 4 2 1");
        
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setName("lblPassword");
        add(lblPassword, "cell 1 6");
        
        tfPassword = new JPasswordField();
        tfPassword.setName("tfPassword");
        add(tfPassword, "cell 1 7 2 1,growx");

        lblErrorMessagePassword = new JLabel("Error Message");
        lblErrorMessagePassword.setVisible(false);
        lblErrorMessagePassword.setForeground(AppColors.RED);
        lblErrorMessagePassword.setName("lblErrorMessagePassword");
        add(lblErrorMessagePassword, "cell 1 8 2 1");
        
        JLabel lblEmail = new JLabel("E-mail");
        lblEmail.setName("lblEmail");
        add(lblEmail, "cell 1 10");
        
        tfEmail = new JTextField();
        tfEmail.setName("tfEmail");
        add(tfEmail, "cell 1 11 2 1,growx");
        tfEmail.setColumns(10);

        lblErrorMessageEmail = new JLabel("Error message");
        lblErrorMessageEmail.setVisible(false);
        lblErrorMessageEmail.setName("lblErrorMessageEmail");
        lblErrorMessageEmail.setForeground(AppColors.RED);
        add(lblErrorMessageEmail, "cell 1 12 2 1");

        btnRegister = new JButton("Register");
        btnRegister.setName("btnConfirmRegister");
        btnRegister.addActionListener(this::registrationEvent);
        add(btnRegister, "cell 1 14");

        btnCancel = new JButton("Cancel");
        btnCancel.setName("btnCancel");
        add(btnCancel, "cell 2 14");

    }

    private void registrationEvent(ActionEvent e){
        if(areFormsCorrects()){
            JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Username " + tfUsername.getText() + " successfully registered", "Registration completed", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private boolean areFormsCorrects() {
        boolean correct = true;
        if(tfUsername.getText().isEmpty()){
            lblErrorMessageUsername.setVisible(true);
            lblErrorMessageUsername.setText("Missing username");
            correct = false;
        } else {
            if(model.userExists(tfUsername.getText())){
                lblErrorMessageUsername.setVisible(true);
                lblErrorMessageUsername.setText("Username exists");
                correct = false;
            }
        }
        char[] password = tfPassword.getPassword();
        if(password.length == 0){
            lblErrorMessagePassword.setVisible(true);
            lblErrorMessagePassword.setText("Missing password");
            correct = false;
        }
        if(tfEmail.getText().isEmpty()){
            lblErrorMessageEmail.setVisible(true);
            lblErrorMessageEmail.setText("Missing e-mail");
            correct = false;
        } else {
            String regexEmail = "[\\w]+@[\\w]+\\.[\\w]{1,5}";
            if(!tfEmail.getText().matches(regexEmail)){
                lblErrorMessageEmail.setVisible(true);
                lblErrorMessageEmail.setText("The input prompted above is not an e-mail");
                correct = false;
            }
        }
        return correct;
    }

}
