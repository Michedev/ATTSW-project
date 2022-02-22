package edu.mikedev.task_manager.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginPage extends JPanel {

	private final JLabel lblErrorMessage;
	private final JLabel lblPassword;

	private final JButton btnLogin;
	private final JButton btnRegister;
	private final JLabel lblUsername;
	private JTextField tfUsername;
	private JTextField tfPassword;


	/**
	 * Create the frame.
	 */
	public LoginPage() {
		setBorder(new EmptyBorder(10, 10, 10, 10));
		GridBagLayout gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);

		GridBagConstraints c = new GridBagConstraints();
		
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.WEST;
		c.gridy = 0;
		c.insets = new Insets(10, 0, 5, 0);
		lblUsername = new JLabel("Username");
		lblUsername.setName("lblUsername");
		add(lblUsername, c);

		c = new GridBagConstraints();
		c.gridwidth = 2;
		c.gridy = 1;
		c.insets = new Insets(5, 0, 10, 0);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;
		tfUsername = new JTextField();
		tfUsername.setName("tfUsername");
		tfUsername.setText("");
		add(tfUsername, c);
		tfUsername.setColumns(10);

		c = new GridBagConstraints();
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.WEST;
		c.gridy = 2;
		c.insets = new Insets(10, 0, 5, 0);
		lblPassword = new JLabel("Password");
		lblPassword.setText("Password");
		add(lblPassword, c);

		c = new GridBagConstraints();
		c.gridwidth = 2;
		c.gridy = 3;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 0, 20, 0);
		tfPassword = new JPasswordField();
		tfPassword.setText("");
		tfPassword.setName("tfPassword");
		add(tfPassword, c);
		tfPassword.setColumns(10);

		c = new GridBagConstraints();
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.WEST;
		c.gridy = 4;
		c.insets = new Insets(10, 0, 20, 0);
		lblErrorMessage = new JLabel("Error message");
		lblErrorMessage.setName("lblErrorMessage");
		lblErrorMessage.setForeground(AppColors.RED);
		lblErrorMessage.setEnabled(false);
		lblErrorMessage.setVisible(false);
		add(lblErrorMessage, c);

		c = new GridBagConstraints();
		c.gridwidth = 1;
		c.gridx = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 6;
		btnLogin = new JButton("Login");
		btnLogin.setName("btnLogin");
		add(btnLogin, c);

		c = new GridBagConstraints();
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 6;
		btnRegister = new JButton("Register");
		btnRegister.setName("btnRegister");
		add(btnRegister, c);
	}


	public JLabel getLblErrorMessage() {
		return lblErrorMessage;
	}

	public JLabel getLblPassword() {
		return lblPassword;
	}

	public JButton getBtnLogin() {
		return btnLogin;
	}

	public JButton getBtnRegister() {
		return btnRegister;
	}

	public JLabel getLblUsername() {
		return lblUsername;
	}

	public JTextField getTfUsername() {
		return tfUsername;
	}

	public JTextField getTfPassword() {
		return tfPassword;
	}

}
