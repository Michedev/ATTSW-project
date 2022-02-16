package edu.mikedev.task_manager.ui;

import edu.mikedev.task_manager.model.Model;
import edu.mikedev.task_manager.Task;
import edu.mikedev.task_manager.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;

public class TaskDetailPage extends JPanel {

	/**
	 * Create the panel.
	 */
	private transient Model model;
	private transient Task task;
	private transient User user;
	private transient JCheckBox cbDone;

	public TaskDetailPage(Model model, Task task, User user) {
		setName("mainPanel");
		String fontFamily = "Cantarell";

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
		GridBagConstraints btnBackConstraints = new GridBagConstraints();
		btnBackConstraints.insets = new Insets(0, 0, 5, 5);
		btnBackConstraints.gridx = 0;
		btnBackConstraints.gridy = 0;
		add(btnGoBack, btnBackConstraints);

		JLabel lblTaskTitle = new JLabel(task.getTitle());
		lblTaskTitle.setName("lblTaskTitle");
		lblTaskTitle.setFont(new Font(fontFamily, Font.BOLD, 20));
		GridBagConstraints lblTaskTitleConstraints = new GridBagConstraints();
		lblTaskTitleConstraints.insets = new Insets(0, 0, 5, 5);
		lblTaskTitleConstraints.gridx = 1;
		lblTaskTitleConstraints.gridy = 1;
		add(lblTaskTitle, lblTaskTitleConstraints);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		JLabel lblTaskDeadline = new JLabel(dateFormat.format(task.getDeadline()));
		lblTaskDeadline.setFont(new Font(fontFamily, Font.BOLD, 18));

		lblTaskDeadline.setName("lblTaskDeadline");
		GridBagConstraints lblTaskDeadlineConstraints = new GridBagConstraints();
		lblTaskDeadlineConstraints.insets = new Insets(0, 0, 5, 0);
		lblTaskDeadlineConstraints.gridx = 2;
		lblTaskDeadlineConstraints.gridy = 1;
		add(lblTaskDeadline, lblTaskDeadlineConstraints);
		
		JLabel lblTaskDescription = new JLabel("<html><p style=\"width:300px\">"+task.getDescription()+"</p></html>");
		lblTaskDescription.setName("lblTaskDescription");
		lblTaskDescription.setFont(new Font(fontFamily, Font.PLAIN, 11));

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
