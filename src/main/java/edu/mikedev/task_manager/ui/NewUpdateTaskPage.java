package edu.mikedev.task_manager.ui;

import edu.mikedev.task_manager.Task;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewUpdateTaskPage extends JPanel {
	private static final long serialVersionUID = 7607002546456900258L;

	private JTextField tfTaskName;
	private JTextField tfTaskDescription;
	private JTextField tfTaskDeadline;
	private JLabel lblErrorMessageDeadline;

	private transient Task toBeUpdatedTask = null;
	private JButton btnSave;
	private JLabel lblErrorMessageName;

	/**
	 * Create the panel.
	 */
	public NewUpdateTaskPage() {
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
		GridBagConstraints lblTaskNameConstraints = new GridBagConstraints();
		lblTaskNameConstraints.insets = new Insets(0, 0, 5, 0);
		lblTaskNameConstraints.gridx = 0;
		lblTaskNameConstraints.gridy = 1;
		add(lblTaskName, lblTaskNameConstraints);
		
		tfTaskName = new JTextField();
		tfTaskName.setName("tfTaskName");
		GridBagConstraints tfTaskNameConstraints = new GridBagConstraints();
		tfTaskNameConstraints.insets = new Insets(0, 0, 5, 0);
		tfTaskNameConstraints.fill = GridBagConstraints.HORIZONTAL;
		tfTaskNameConstraints.gridx = 0;
		tfTaskNameConstraints.gridy = 2;
		add(tfTaskName, tfTaskNameConstraints);
		tfTaskName.setColumns(10);

		String defaultErrorMessage = "Error message";
		lblErrorMessageName = new JLabel(defaultErrorMessage);
		lblErrorMessageName.setVisible(false);
		lblErrorMessageName.setName("lblErrorMessageName");
		lblErrorMessageName.setForeground(Color.RED);
		GridBagConstraints lblErrorMessageNameConstraints = new GridBagConstraints();
		lblErrorMessageNameConstraints.insets = new Insets(0, 0, 5, 0);
		lblErrorMessageNameConstraints.gridx = 0;
		lblErrorMessageNameConstraints.gridy = 3;
		add(lblErrorMessageName, lblErrorMessageNameConstraints);
		
		JLabel lblTaskDescription = new JLabel("Task Description");
		GridBagConstraints lblTaskDescriptionConstraints = new GridBagConstraints();
		lblTaskDescription.setName("lblTaskDescription");
		lblTaskDescriptionConstraints.insets = new Insets(0, 0, 5, 0);
		lblTaskDescriptionConstraints.gridx = 0;
		lblTaskDescriptionConstraints.gridy = 5;
		add(lblTaskDescription, lblTaskDescriptionConstraints);
		
		tfTaskDescription = new JTextField();
		tfTaskDescription.setName("tfTaskDescription");
		GridBagConstraints tfTaskDescriptionConstraints = new GridBagConstraints();
		tfTaskDescriptionConstraints.insets = new Insets(0, 0, 5, 0);
		tfTaskDescriptionConstraints.fill = GridBagConstraints.HORIZONTAL;
		tfTaskDescriptionConstraints.gridx = 0;
		tfTaskDescriptionConstraints.gridy = 6;
		add(tfTaskDescription, tfTaskDescriptionConstraints);
		tfTaskDescription.setColumns(10);
		
		JLabel lblErrorMessageDescription = new JLabel(defaultErrorMessage);
		lblErrorMessageDescription.setVisible(false);
		lblErrorMessageDescription.setHorizontalAlignment(SwingConstants.LEFT);
		lblErrorMessageDescription.setForeground(Color.RED);
		lblErrorMessageDescription.setName("lblErrorMessageDescription");
		GridBagConstraints lblErrorMessageDescriptionConstraints = new GridBagConstraints();
		lblErrorMessageDescriptionConstraints.insets = new Insets(0, 0, 5, 0);
		lblErrorMessageDescriptionConstraints.gridx = 0;
		lblErrorMessageDescriptionConstraints.gridy = 7;
		add(lblErrorMessageDescription, lblErrorMessageDescriptionConstraints);
		
		JLabel lblDeadline = new JLabel("Deadline (dd/MM/yyyy)");
		lblDeadline.setName("lblTaskDeadline");
		GridBagConstraints lblDeadlineConstraints = new GridBagConstraints();
		lblDeadlineConstraints.insets = new Insets(0, 0, 5, 0);
		lblDeadlineConstraints.gridx = 0;
		lblDeadlineConstraints.gridy = 9;
		add(lblDeadline, lblDeadlineConstraints);
		
		tfTaskDeadline = new JTextField();
		tfTaskDeadline.setName("tfTaskDeadline");
		GridBagConstraints tfDeadlineConstraints = new GridBagConstraints();
		tfDeadlineConstraints.insets = new Insets(0, 0, 5, 0);
		tfDeadlineConstraints.fill = GridBagConstraints.HORIZONTAL;
		tfDeadlineConstraints.gridx = 0;
		tfDeadlineConstraints.gridy = 10;
		add(tfTaskDeadline, tfDeadlineConstraints);
		tfTaskDeadline.setColumns(10);

		lblErrorMessageDeadline = new JLabel(defaultErrorMessage);
		lblErrorMessageDeadline.setVisible(false);
		lblErrorMessageDeadline.setName("lblErrorMessageDeadline");
		lblErrorMessageDeadline.setForeground(Color.RED);
		GridBagConstraints lblErrorMessageDeadlineConstraints = new GridBagConstraints();
		lblErrorMessageDeadlineConstraints.insets = new Insets(0, 0, 5, 0);
		lblErrorMessageDeadlineConstraints.gridx = 0;
		lblErrorMessageDeadlineConstraints.gridy = 11;
		add(lblErrorMessageDeadline, lblErrorMessageDeadlineConstraints);

		btnSave = new JButton("Save");
		btnSave.setName("btnSave");
		GridBagConstraints btnSaveConstraints = new GridBagConstraints();
		btnSaveConstraints.gridx = 0;
		btnSaveConstraints.gridy = 13;
		add(btnSave, btnSaveConstraints);
	}

	public boolean isUpdateMode(){
		return toBeUpdatedTask != null;
	}

	public NewUpdateTaskPage(Task task){
		// Update task branch
		this();
		toBeUpdatedTask = task;
		btnSave.setText("Update");
		tfTaskName.setText(task.getTitle());
		tfTaskDescription.setText(task.getDescription());
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		tfTaskDeadline.setText(dateFormat.format(task.getDeadline()));
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

	public Task getToBeUpdatedTask() {
		return toBeUpdatedTask;
	}

	public JButton getBtnSave() {
		return btnSave;
	}
}
