package edu.mikedev.task_manager.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;

public class LoginWindow extends JFrame {

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
	public LoginWindow() {
		setTitle("Login page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][][][grow][grow]", "[][][][][][][][][][]"));
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setName("lblUsername");
		contentPane.add(lblUsername, "cell 3 2");
		
		tfUsername = new JTextField();
		tfUsername.setName("tfUsername");
		contentPane.add(tfUsername, "cell 3 3 2 1,growx");
		tfUsername.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		contentPane.add(lblPassword, "cell 3 5");
		
		tfPassword = new JTextField();
		tfPassword.setName("tfPassword");
		contentPane.add(tfPassword, "cell 3 6 2 1,growx");
		tfPassword.setColumns(10);
		
		JLabel lblErrorMessage = new JLabel("Error message");
		lblErrorMessage.setName("lblErrorMessage");
		lblErrorMessage.setForeground(Color.RED);
		lblErrorMessage.setEnabled(false);
		contentPane.add(lblErrorMessage, "cell 3 7 2 1");
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setName("btnLogin");
		contentPane.add(btnLogin, "cell 3 9");
		
		JButton btnRegister = new JButton("Register");
		btnRegister.setName("btnRegister");
		contentPane.add(btnRegister, "cell 4 9");
	}

}
