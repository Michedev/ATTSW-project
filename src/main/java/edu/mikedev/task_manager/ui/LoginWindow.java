package edu.mikedev.task_manager.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import edu.mikedev.task_manager.Model;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;

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
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow frame = new LoginWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginWindow(Model model) {
		this.model = model;
		setTitle("Login page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][][][grow][grow]", "[][][][][][][][][][]"));

		lblUsername = new JLabel("Username");
		lblUsername.setName("lblUsername");
		contentPane.add(lblUsername, "cell 3 2");
		
		tfUsername = new JTextField();
		tfUsername.setName("tfUsername");
		contentPane.add(tfUsername, "cell 3 3 2 1,growx");
		tfUsername.setColumns(10);

		lblPassword = new JLabel("Password");
		contentPane.add(lblPassword, "cell 3 5");
		
		tfPassword = new JPasswordField();
		tfPassword.setName("tfPassword");
		contentPane.add(tfPassword, "cell 3 6 2 1,growx");
		tfPassword.setColumns(10);

		lblErrorMessage = new JLabel("Error message");
		lblErrorMessage.setName("lblErrorMessage");
		lblErrorMessage.setForeground(Color.RED);
		lblErrorMessage.setEnabled(false);
		contentPane.add(lblErrorMessage, "cell 3 7 2 1");

		btnLogin = new JButton("Login");
		btnLogin.addActionListener(this::loginClickBtn);
		btnLogin.setName("btnLogin");
		contentPane.add(btnLogin, "cell 3 9");

		btnRegister = new JButton("Register");
		btnRegister.setName("btnRegister");
		contentPane.add(btnRegister, "cell 4 9");
	}

	private void loginClickBtn(ActionEvent e){
		boolean isCorrect = model.areCredentialCorrect(lblUsername.getText(), lblPassword.getText());
		if(isCorrect){
			this.setContentPane(new MainPage());
		} else {
			lblErrorMessage.setText("Username and/or password are wrong");
			lblErrorMessage.setEnabled(true);
		}

	}

}
