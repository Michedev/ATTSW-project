package edu.mikedev.task_manager.ui;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class RegistrationPage extends JPanel {

 	private static final long serialVersionUID = -1490287536998843323L;
 	
	private JLabel lblErrorMessagePassword;
    private JLabel lblErrorMessageEmail;
    private JButton btnRegister;
    private JButton btnCancel;
    private JLabel lblErrorMessageUsername;
    private JTextField tfUsername;
    private JPasswordField tfPassword;
    private JTextField tfEmail;

    public RegistrationPage() {
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
        add(btnRegister, "cell 1 14");

        btnCancel = new JButton("Cancel");
        btnCancel.setName("btnCancel");
        add(btnCancel, "cell 2 14");

    }

    public JLabel getLblErrorMessagePassword() {
        return lblErrorMessagePassword;
    }

    public JLabel getLblErrorMessageEmail() {
        return lblErrorMessageEmail;
    }

    public JButton getBtnRegister() {
        return btnRegister;
    }

    public JButton getBtnCancel() {
        return btnCancel;
    }

    public JLabel getLblErrorMessageUsername() {
        return lblErrorMessageUsername;
    }

    public JTextField getTfUsername() {
        return tfUsername;
    }

    public JPasswordField getTfPassword() {
        return tfPassword;
    }

    public JTextField getTfEmail() {
        return tfEmail;
    }
}
