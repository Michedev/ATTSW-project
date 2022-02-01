package edu.mikedev.task_manager.ui;

import edu.mikedev.task_manager.Task;
import edu.mikedev.task_manager.User;

import javax.swing.JPanel;
import java.awt.*;
import javax.swing.JLabel;
import java.text.SimpleDateFormat;
import javax.swing.JButton;

public class TaskDetailPage extends JPanel {

	/**
	 * Create the panel.
	 */
	private Task task;
	private User user;

	public TaskDetailPage(Task task, User user) {
		this.task = task;
		this.user = user;

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JButton btnBack = new JButton("<");
		btnBack.setName("btnBack");
		GridBagConstraints gbc_btnBack = new GridBagConstraints();
		gbc_btnBack.insets = new Insets(0, 0, 5, 5);
		gbc_btnBack.gridx = 0;
		gbc_btnBack.gridy = 0;
		add(btnBack, gbc_btnBack);
		
		JLabel lblTaskTitle = new JLabel(task.getTitle());
		lblTaskTitle.setName("lblTaskTitle");
		lblTaskTitle.setFont(new Font("Cantarell", Font.BOLD, 20));
		GridBagConstraints gbc_lblTaskTitle = new GridBagConstraints();
		gbc_lblTaskTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblTaskTitle.gridx = 1;
		gbc_lblTaskTitle.gridy = 1;
		add(lblTaskTitle, gbc_lblTaskTitle);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		JLabel lblTaskDeadline = new JLabel(dateFormat.format(task.getDeadline()));
		lblTaskDeadline.setFont(new Font("Cantarell", Font.BOLD, 18));

		lblTaskDeadline.setName("lblTaskDeadline");
		GridBagConstraints gbc_lblTaskDeadline = new GridBagConstraints();
		gbc_lblTaskDeadline.insets = new Insets(0, 0, 5, 0);
		gbc_lblTaskDeadline.gridx = 2;
		gbc_lblTaskDeadline.gridy = 1;
		add(lblTaskDeadline, gbc_lblTaskDeadline);
		
		JLabel lblTaskDescription = new JLabel("<html><p style=\"width:300px\">"+task.getDescription()+"</p></html>");
		lblTaskDescription.setName("lblTaskDescription");
		lblTaskDescription.setFont(new Font("Cantarell", Font.PLAIN, 12));

		GridBagConstraints gbc_lblTaskDescription = new GridBagConstraints();
		gbc_lblTaskDescription.gridwidth = 2;
		gbc_lblTaskDescription.gridx = 1;
		gbc_lblTaskDescription.gridy = 2;
		add(lblTaskDescription, gbc_lblTaskDescription);

	}

}
