package edu.mikedev.task_manager.ui;

import edu.mikedev.task_manager.Task;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.SwingConstants;

public class NewUpdateTask extends JPanel {
	private JTextField tfTaskName;
	private JTextField tfTaskDescription;
	private JTextField tfTaskDeadline;
	private JLabel lblErrorMessageDeadline;

	/**
	 * Create the panel.
	 */
	public NewUpdateTask() {
		setName("mainPanel");
		// New task branch
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblTaskName = new JLabel("Task Name");
		lblTaskName.setName("lblTaskName");
		GridBagConstraints gbc_lblTaskName = new GridBagConstraints();
		gbc_lblTaskName.insets = new Insets(0, 0, 5, 0);
		gbc_lblTaskName.gridx = 0;
		gbc_lblTaskName.gridy = 1;
		add(lblTaskName, gbc_lblTaskName);
		
		tfTaskName = new JTextField();
		tfTaskName.setName("tfTaskName");
		GridBagConstraints gbc_tfTaskName = new GridBagConstraints();
		gbc_tfTaskName.insets = new Insets(0, 0, 5, 0);
		gbc_tfTaskName.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfTaskName.gridx = 0;
		gbc_tfTaskName.gridy = 2;
		add(tfTaskName, gbc_tfTaskName);
		tfTaskName.setColumns(10);
		
		JLabel lblErrorMessageName = new JLabel("Error message");
		lblErrorMessageName.setVisible(false);
		lblErrorMessageName.setName("lblErrorMessageName");
		lblErrorMessageName.setForeground(Color.RED);
		GridBagConstraints gbc_lblErrorMessageName = new GridBagConstraints();
		gbc_lblErrorMessageName.insets = new Insets(0, 0, 5, 0);
		gbc_lblErrorMessageName.gridx = 0;
		gbc_lblErrorMessageName.gridy = 3;
		add(lblErrorMessageName, gbc_lblErrorMessageName);
		
		JLabel lblTaskDescription = new JLabel("Task Description");
		GridBagConstraints gbc_lblTaskDescription = new GridBagConstraints();
		lblTaskDescription.setName("lblTaskDescription");
		gbc_lblTaskDescription.insets = new Insets(0, 0, 5, 0);
		gbc_lblTaskDescription.gridx = 0;
		gbc_lblTaskDescription.gridy = 5;
		add(lblTaskDescription, gbc_lblTaskDescription);
		
		tfTaskDescription = new JTextField();
		tfTaskDescription.setName("tfTaskDescription");
		GridBagConstraints gbc_tfTaskDescription = new GridBagConstraints();
		gbc_tfTaskDescription.insets = new Insets(0, 0, 5, 0);
		gbc_tfTaskDescription.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfTaskDescription.gridx = 0;
		gbc_tfTaskDescription.gridy = 6;
		add(tfTaskDescription, gbc_tfTaskDescription);
		tfTaskDescription.setColumns(10);
		
		JLabel lblErrorMessageDescription = new JLabel("Error message");
		lblErrorMessageDescription.setVisible(false);
		lblErrorMessageDescription.setHorizontalAlignment(SwingConstants.LEFT);
		lblErrorMessageDescription.setForeground(Color.RED);
		lblErrorMessageDescription.setName("lblErrorMessageDescription");
		GridBagConstraints gbc_lblErrorMessageDescription = new GridBagConstraints();
		gbc_lblErrorMessageDescription.insets = new Insets(0, 0, 5, 0);
		gbc_lblErrorMessageDescription.gridx = 0;
		gbc_lblErrorMessageDescription.gridy = 7;
		add(lblErrorMessageDescription, gbc_lblErrorMessageDescription);
		
		JLabel lblDeadline = new JLabel("Deadline (dd/MM/yyyy)");
		lblDeadline.setName("lblTaskDeadline");
		GridBagConstraints gbc_lblDeadline = new GridBagConstraints();
		gbc_lblDeadline.insets = new Insets(0, 0, 5, 0);
		gbc_lblDeadline.gridx = 0;
		gbc_lblDeadline.gridy = 9;
		add(lblDeadline, gbc_lblDeadline);
		
		tfTaskDeadline = new JTextField();
		tfTaskDeadline.setName("tfTaskDeadline");
		GridBagConstraints gbc_tfDeadline = new GridBagConstraints();
		gbc_tfDeadline.insets = new Insets(0, 0, 5, 0);
		gbc_tfDeadline.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfDeadline.gridx = 0;
		gbc_tfDeadline.gridy = 10;
		add(tfTaskDeadline, gbc_tfDeadline);
		tfTaskDeadline.setColumns(10);

		lblErrorMessageDeadline = new JLabel("Error message");
		lblErrorMessageDeadline.setVisible(false);
		lblErrorMessageDeadline.setName("lblErrorMessageDeadline");
		lblErrorMessageDeadline.setForeground(Color.RED);
		GridBagConstraints gbc_lblErrorMessageDeadline = new GridBagConstraints();
		gbc_lblErrorMessageDeadline.insets = new Insets(0, 0, 5, 0);
		gbc_lblErrorMessageDeadline.gridx = 0;
		gbc_lblErrorMessageDeadline.gridy = 11;
		add(lblErrorMessageDeadline, gbc_lblErrorMessageDeadline);
		
		JButton btnSave = new JButton("Save");
		btnSave.setName("btnSave");
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.gridx = 0;
		gbc_btnSave.gridy = 13;
		add(btnSave, gbc_btnSave);
	}

	public NewUpdateTask(Task task){
		// Update task branch
		this();
		tfTaskName.setText(task.getTitle());
		tfTaskDescription.setText(task.getDescription());
		if(task.getDeadline() != null){
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			tfTaskDeadline.setText(dateFormat.format(task.getDeadline()));
		}
	}


	public Task parseTask() {
		String taskTitle = tfTaskName.getText();
		String taskDescription = tfTaskDescription.getText();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date taskDeadline = null;
		try {
			taskDeadline = dateFormat.parse(tfTaskDeadline.getText());
		} catch (ParseException e) {
			lblErrorMessageDeadline.setVisible(true);
			lblErrorMessageDeadline.setText("Date parsing error. It should be in the format (dd/MM/yyyy)");
			return null;
		}
		lblErrorMessageDeadline.setVisible(false);
		return new Task(taskTitle, taskDescription, taskDeadline, false);

	}
}
