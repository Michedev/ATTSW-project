package edu.mikedev.task_manager.ui;

import edu.mikedev.task_manager.Task;
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
		for(Task t: user.getTasks()){
			makeTaskCard(t);
		}
	}

	private JPanel makeTaskCard(Task t) {
		JPanel taskPanel = new JPanel();
		taskPanel.setMaximumSize(new Dimension(300, 1500000));
		add(taskPanel);
		taskPanel.setLayout(new GridLayout(2, 1, 5, 5));

		JPanel headerPanelTask = new JPanel();
		taskPanel.add(headerPanelTask);
		headerPanelTask.setLayout(new GridLayout(1, 2, 5, 0));

		JLabel lblTitleTask0 = new JLabel(t.getTitle());
		lblTitleTask0.setFont(new Font("Cantarell", Font.BOLD, 18));
		lblTitleTask0.setName("lblTitleTask" + t.getId());
		headerPanelTask.add(lblTitleTask0);

		JLabel lblDateTask = new JLabel(t.getDeadline().toString());
		lblDateTask.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDateTask.setFont(new Font("Cantarell", Font.BOLD, 14));
		lblDateTask.setName("lblDateTask" + t.getId());
		headerPanelTask.add(lblDateTask);

		JPanel contentPanelTask = new JPanel();
		taskPanel.add(contentPanelTask);
		contentPanelTask.setLayout(new GridLayout(1, 0, 0, 0));

		JLabel lblDescrTask = new JLabel(t.getDescription());
		lblDescrTask.setMaximumSize(new Dimension(150, 1500));
		lblDescrTask.setFont(new Font("Cantarell", Font.BOLD, 12));
		lblDescrTask.setName("lblDescrTask" + t.getId());
		contentPanelTask.add(lblDescrTask);

		return taskPanel;
	}

}
