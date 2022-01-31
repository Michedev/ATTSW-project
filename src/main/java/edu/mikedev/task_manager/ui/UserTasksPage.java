package edu.mikedev.task_manager.ui;

import edu.mikedev.task_manager.Task;
import edu.mikedev.task_manager.User;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UserTasksPage extends JPanel {

	/**
	 * Create the panel.
	 */
	private User user;

	public UserTasksPage(User user) {
		this.user = user;
		GridBagLayout gridBagLayout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		int maxCols = 3;
		setLayout(gridBagLayout);
		List<Task> userTasksSorted = user.getTasks().stream().sorted((a, b) -> Comparator.comparingInt(Task::getId).compare(a,b)).collect(Collectors.toList());
		JButton btnNewTask = new JButton("+");
		btnNewTask.setName("btnNewTask");
		int i = 0;
		for(Task t: userTasksSorted){
			c.gridx = i % maxCols;
			c.gridy = i / maxCols;
			makeTaskCard(t, c);
			i++;
		}
		c.gridx = 3;
		c.gridy = i / maxCols;
		add(btnNewTask, c);

	}

	private JPanel makeTaskCard(Task t, GridBagConstraints c) {
		JPanel taskPanel = new JPanel();
		colorBackground(taskPanel, t);
		taskPanel.setName("task" + t.getId());
		taskPanel.setMaximumSize(new Dimension(300, 1500000));
		add(taskPanel, c);
		taskPanel.setLayout(new GridLayout(2, 1, 5, 5));

		JPanel headerPanelTask = new JPanel();
		headerPanelTask.setBackground(new Color(0, 0, 0, 0));
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
		contentPanelTask.setBackground(new Color(0, 0, 0, 0));
		contentPanelTask.setMaximumSize(new Dimension(150, 1500));
		taskPanel.add(contentPanelTask);
		contentPanelTask.setLayout(new GridLayout(1, 0, 0, 0));

		JLabel lblDescrTask = new JLabel(t.getDescription());
		lblDescrTask.setMaximumSize(new Dimension(150, 1500));
		lblDescrTask.setFont(new Font("Cantarell", Font.BOLD, 12));
		lblDescrTask.setName("lblDescrTask" + t.getId());
		contentPanelTask.add(lblDescrTask);

		return taskPanel;
	}

	private void colorBackground(JPanel taskPanel, Task task) {
		if(task.isDone()){
			taskPanel.setBackground(Color.GREEN);
		} else {
			if(task.getDeadline().after(Date.from(Instant.now()))){
				taskPanel.setBackground(Color.ORANGE);
			} else {
				taskPanel.setBackground(Color.RED);
			}
		}
	}

}
