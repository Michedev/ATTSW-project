package edu.mikedev.task_manager.ui;

import edu.mikedev.task_manager.User;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Dimension;

public class UserTasksPage extends JPanel {

	/**
	 * Create the panel.
	 */
	private User user;

	public UserTasksPage(User user) {
		this.user = user;
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel task0 = new JPanel();
		task0.setMaximumSize(new Dimension(300, 1500000));
		add(task0);
		task0.setLayout(new GridLayout(2, 1, 5, 5));
		
		JPanel headerPanelTask0 = new JPanel();
		task0.add(headerPanelTask0);
		headerPanelTask0.setLayout(new GridLayout(1, 2, 5, 0));
		
		JLabel lblTitleTask0 = new JLabel("Task title 1");
		lblTitleTask0.setFont(new Font("Cantarell", Font.BOLD, 18));
		lblTitleTask0.setName("lblTitleTask0");
		headerPanelTask0.add(lblTitleTask0);
		
		JLabel lblDateTask0 = new JLabel("05/11/2022");
		lblDateTask0.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDateTask0.setFont(new Font("Cantarell", Font.BOLD, 14));
		lblDateTask0.setName("lblDateTask0");
		headerPanelTask0.add(lblDateTask0);
		
		JPanel contentPanelTask0 = new JPanel();
		task0.add(contentPanelTask0);
		contentPanelTask0.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblDescrTask0 = new JLabel("A small task description Lorem Inpsum etc etc...");
		lblDescrTask0.setMaximumSize(new Dimension(150, 1500));
		lblDescrTask0.setFont(new Font("Cantarell", Font.BOLD, 12));
		lblDescrTask0.setName("lblDescrTask0");
		contentPanelTask0.add(lblDescrTask0);
	}

}
