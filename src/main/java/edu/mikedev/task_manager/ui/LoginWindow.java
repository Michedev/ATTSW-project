package edu.mikedev.task_manager.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import edu.mikedev.task_manager.model.Model;
import edu.mikedev.task_manager.User;

import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginWindow extends JFrame {

	private final JLabel lblErrorMessage;
	private final JLabel lblPassword;
	private final JButton btnLogin;
	private final JButton btnRegister;
	private final JLabel lblUsername;
	private final transient Model model;
	private JPanel contentPane;
	private JTextField tfUsername;
	private JTextField tfPassword;


	/**
	 * Create the frame.
	 */
	public LoginWindow(Model model) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException|InstantiationException|IllegalAccessException|UnsupportedLookAndFeelException e) {
			// Leave default theme
		}

		this.model = model;
		setTitle("Login page");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		GridBagLayout gridBagLayout = new GridBagLayout();
		contentPane.setLayout(gridBagLayout);

		GridBagConstraints c = new GridBagConstraints();
		
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.WEST;
		c.gridy = 0;
		c.insets = new Insets(10, 0, 5, 0);
		lblUsername = new JLabel("Username");
		lblUsername.setName("lblUsername");
		contentPane.add(lblUsername, c);

		c = new GridBagConstraints();
		c.gridwidth = 2;
		c.gridy = 1;
		c.insets = new Insets(5, 0, 10, 0);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;
		tfUsername = new JTextField();
		tfUsername.setName("tfUsername");
		tfUsername.setText("");
		contentPane.add(tfUsername, c);
		tfUsername.setColumns(10);

		c = new GridBagConstraints();
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.WEST;
		c.gridy = 2;
		c.insets = new Insets(10, 0, 5, 0);
		lblPassword = new JLabel("Password");
		lblPassword.setText("Password");
		contentPane.add(lblPassword, c);

		c = new GridBagConstraints();
		c.gridwidth = 2;
		c.gridy = 3;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 0, 20, 0);
		tfPassword = new JPasswordField();
		tfPassword.setText("");
		tfPassword.setName("tfPassword");
		contentPane.add(tfPassword, c);
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
		contentPane.add(lblErrorMessage, c);

		c = new GridBagConstraints();
		c.gridwidth = 1;
		c.gridx = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 6;
		btnLogin = new JButton("Login");
		btnLogin.addActionListener(this::loginClickBtn);
		btnLogin.setName("btnLogin");
		contentPane.add(btnLogin, c);

		c = new GridBagConstraints();
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 6;
		btnRegister = new JButton("Register");
		btnRegister.addActionListener(this::registrationClickBtn);
		btnRegister.setName("btnRegister");
		contentPane.add(btnRegister, c);
	}

	private void loginClickBtn(ActionEvent e){
		String username = tfUsername.getText();
		String password = tfPassword.getText();
		boolean isCorrect = model.areCredentialCorrect(username, password);
		if(isCorrect){
			setTitle(username + " tasks");
			User loggedUser = model.loginUser(username, password);
			this.setContentPane(new UserTasksPage(model, loggedUser));
			setMinimumSize(new Dimension(400, 300));
			pack();
		} else {
			lblErrorMessage.setText("Username and/or password are wrong");
			lblErrorMessage.setEnabled(true);
			lblErrorMessage.setVisible(true);

		}
	}

	private void registrationClickBtn(ActionEvent e){
		getContentPane().removeAll();
		setTitle("Registration page");
		getContentPane().add(new RegistrationPage(model));
		pack();
	}

}
