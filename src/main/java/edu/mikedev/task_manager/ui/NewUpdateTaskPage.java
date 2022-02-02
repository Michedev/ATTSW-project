package edu.mikedev.task_manager.ui;

import edu.mikedev.task_manager.Model;
import edu.mikedev.task_manager.Task;
import edu.mikedev.task_manager.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.Color;
import java.util.Set;

public class NewUpdateTaskPage extends JPanel {
	private final Model model;
	private JTextField tfTaskName;
	private JTextField tfTaskDescription;
	private JTextField tfTaskDeadline;
	private JLabel lblErrorMessageDeadline;
	private User user;

	private Task toBeUpdatedTask = null;
	private JButton btnSave;
	private JLabel lblErrorMessageName;

	/**
	 * Create the panel.
	 */
	public NewUpdateTaskPage(Model model, User user) {
		this.model = model;
		this.user = user;
		setName("mainPanel");
		int borderSize = 20;
		setBorder(new EmptyBorder(borderSize, borderSize, borderSize, borderSize));
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

		lblErrorMessageName = new JLabel("Error message");
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

		btnSave = new JButton("Save");
		btnSave.setName("btnSave");
		btnSave.addActionListener(this::saveTask);
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.gridx = 0;
		gbc_btnSave.gridy = 13;
		add(btnSave, gbc_btnSave);
	}

	public boolean isUpdateMode(){
		return toBeUpdatedTask != null;
	}

	private void saveTask(ActionEvent e) {
		Task task = parseTask();
		if(task != null){
			if(isUpdateMode()){
				toBeUpdatedTask.setTitle(task.getTitle());
				toBeUpdatedTask.setDescription(task.getDescription());
				toBeUpdatedTask.setDeadline(task.getDeadline());
			} else {
				Set<Task> userTasks = user.getTasks();
				task.setId(userTasks.size());
				userTasks.add(task);
			}
			JFrame windowAncestor = (JFrame) SwingUtilities.getWindowAncestor(this);
			windowAncestor.setTitle(user.getUsername() + " tasks");
			windowAncestor.setContentPane(new UserTasksPage(model, user));
			windowAncestor.pack();
		}
	}

	public NewUpdateTaskPage(Model model, User user, Task task){
		// Update task branch
		this(model, user);
		toBeUpdatedTask = task;
		btnSave.setText("Update");
		tfTaskName.setText(task.getTitle());
		tfTaskDescription.setText(task.getDescription());
	}


	public Task parseTask() {
		String taskTitle = tfTaskName.getText();
		boolean errorMode = false;
		if(taskTitle.isEmpty()){
			lblErrorMessageName.setVisible(true);
			lblErrorMessageName.setText("Missing task name");
			errorMode = true;
		}
		String taskDescription = tfTaskDescription.getText();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date taskDeadline = null;
		try {
			taskDeadline = dateFormat.parse(tfTaskDeadline.getText());
		} catch (ParseException e) {
			lblErrorMessageDeadline.setVisible(true);
			lblErrorMessageDeadline.setText("Date parsing error. It should be in the format (dd/MM/yyyy)");
			errorMode = true;
		}
		if(errorMode){
			return null;
		}
		lblErrorMessageDeadline.setVisible(false);
		return new Task(taskTitle, taskDescription, taskDeadline, false);

	}
}
