package edu.mikedev.task_manager.ui;

import edu.mikedev.task_manager.Model;
import edu.mikedev.task_manager.Task;
import edu.mikedev.task_manager.User;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;

public class TaskDetailPage extends JPanel {

	/**
	 * Create the panel.
	 */
	private Model model;
	private Task task;
	private User user;
	private JCheckBox cbDone;

	public TaskDetailPage(Model model, Task task, User user) {
		setName("mainPanel");

		this.model = model;
		this.task = task;
		this.user = user;

		Color backgroundColor = AppColors.getColorBackground(task);
		setBackground(backgroundColor);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JButton btnGoBack = new JButton("<");
		btnGoBack.setName("btnGoBack");
		btnGoBack.addActionListener(this::goBackAction);
		GridBagConstraints gbc_btnBack = new GridBagConstraints();
		gbc_btnBack.insets = new Insets(0, 0, 5, 5);
		gbc_btnBack.gridx = 0;
		gbc_btnBack.gridy = 0;
		add(btnGoBack, gbc_btnBack);
		
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
		lblTaskDescription.setFont(new Font("Cantarell", Font.PLAIN, 11));

		GridBagConstraints lblTaskDescriptionContraints = new GridBagConstraints();
		lblTaskDescriptionContraints.gridwidth = 2;
		lblTaskDescriptionContraints.gridx = 1;
		lblTaskDescriptionContraints.gridy = 2;
		add(lblTaskDescription, lblTaskDescriptionContraints);

		cbDone = new JCheckBox();
		cbDone.setName("cbDone");
		cbDone.setSelected(task.isDone());
		cbDone.addActionListener(this::toggleDone);

		GridBagConstraints cbDoneConstrains = new GridBagConstraints();
		cbDoneConstrains.gridx = 0;
		cbDoneConstrains.gridy = 3;
		add(cbDone, cbDoneConstrains);

		JLabel lblDone = new JLabel("Done");
		lblDone.setName("lblDone");

		GridBagConstraints lblDoneConstraints = new GridBagConstraints();
		lblDoneConstraints.gridx = 1;
		lblDoneConstraints.gridy = 3;
		add(lblDone, lblDoneConstraints);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.setName("btnUpdate");
		btnUpdate.addActionListener(this::updateEvent);
		GridBagConstraints btnUpdateConstraints = new GridBagConstraints();
		btnUpdateConstraints.gridx = 2;
		btnUpdateConstraints.gridy = 3;
		btnUpdateConstraints.anchor = GridBagConstraints.LINE_END;
		add(btnUpdate, btnUpdateConstraints);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setName("btnDelete");
		btnDelete.addActionListener(this::deleteEvent);
		btnDelete.setBackground(Color.RED);

		GridBagConstraints btnDeleteContraints = new GridBagConstraints();
		btnDeleteContraints.gridx = 3;
		btnDeleteContraints.gridy = 3;
		add(btnDelete, btnDeleteContraints);

	}

	private void toggleDone(ActionEvent e) {
		task.setDone(cbDone.isSelected());
		Color backgroundColor = AppColors.getColorBackground(task);
		setBackground(backgroundColor);
	}

	private void updateEvent(ActionEvent e) {
		JFrame windowAncestor = (JFrame) SwingUtilities.getWindowAncestor(this);
		windowAncestor.setContentPane(new NewUpdateTaskPage(model, user, task));
		windowAncestor.setTitle("Update task \"" + task.getTitle() + "\"");
		windowAncestor.pack();
	}

	private void deleteEvent(ActionEvent e) {
		user.getTasks().remove(task);
		goBackAction(e);
	}

	private void goBackAction(ActionEvent e){
		JFrame windowAncestor = (JFrame) SwingUtilities.getWindowAncestor(this);
		windowAncestor.setContentPane(new UserTasksPage(model, user));
		windowAncestor.setTitle(user.getUsername() + " tasks");
		windowAncestor.pack();
	}

}
