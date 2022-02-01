package edu.mikedev.task_manager.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import edu.mikedev.task_manager.Model;
import edu.mikedev.task_manager.User;
import net.miginfocom.swing.MigLayout;

import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginWindow extends JFrame {

	private final JLabel lblErrorMessage;
	private final JLabel lblPassword;
	private final JButton btnLogin;
	private final JButton btnRegister;
	private final JLabel lblUsername;
	private final Model model;
	private JPanel contentPane;
	private JTextField tfUsername;
	private JTextField tfPassword;


	/**
	 * Create the frame.
	 */
	public LoginWindow(Model model) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		this.model = model;
		setTitle("Login page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][][][grow][grow]", "[][][][][][][][][][]"));

		lblUsername = new JLabel("Username");
		lblUsername.setName("lblUsername");
		contentPane.add(lblUsername, "cell 3 2");
		
		tfUsername = new JTextField();
		tfUsername.setName("tfUsername");
		tfUsername.setText("");
		contentPane.add(tfUsername, "cell 3 3 2 1,growx");
		tfUsername.setColumns(10);

		lblPassword = new JLabel("Password");
		lblPassword.setText("Password");
		contentPane.add(lblPassword, "cell 3 5");
		
		tfPassword = new JPasswordField();
		tfPassword.setText("");
		tfPassword.setName("tfPassword");
		contentPane.add(tfPassword, "cell 3 6 2 1,growx");
		tfPassword.setColumns(10);

		lblErrorMessage = new JLabel("Error message");
		lblErrorMessage.setName("lblErrorMessage");
		lblErrorMessage.setForeground(AppColors.RED);
		lblErrorMessage.setEnabled(false);
		lblErrorMessage.setVisible(false);
		contentPane.add(lblErrorMessage, "cell 3 7 2 1");

		btnLogin = new JButton("Login");
		btnLogin.addActionListener(this::loginClickBtn);
		btnLogin.setName("btnLogin");
		contentPane.add(btnLogin, "cell 3 9");

		btnRegister = new JButton("Register");
		btnRegister.addActionListener(this::registrationClickBtn);
		btnRegister.setName("btnRegister");
		contentPane.add(btnRegister, "cell 4 9");
	}

	private void loginClickBtn(ActionEvent e){
		String username = tfUsername.getText();
		String password = tfPassword.getText();
		boolean isCorrect = model.areCredentialCorrect(username, password);
		System.out.println("is correct " + isCorrect);
		if(isCorrect){
			setTitle("Main page");
			User loggedUser = model.getUser(username, password);
			setTitle(username + " tasks");
			this.setContentPane(new UserTasksPage(loggedUser));
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
