package edu.mikedev.task_manager.ui;

import edu.mikedev.task_manager.Model;
import edu.mikedev.task_manager.Task;
import edu.mikedev.task_manager.User;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UserTasksPage extends JPanel {

	private Model model;
	/**
	 * Create the panel.
	 */
	private User user;

	public UserTasksPage(Model model, User user) {
		this.model = model;
		this.user = user;
		GridBagLayout gridBagLayout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 0.9;
		c.weighty = 0.9;
		int maxCols = 3;
		setLayout(gridBagLayout);
		List<Task> userTasksSorted = user.getTasks().stream().sorted((a, b) -> Comparator.comparingInt(Task::getId).compare(a,b)).collect(Collectors.toList());
		JButton btnNewTask = new JButton("+");
		btnNewTask.addActionListener(this::goToNewTask);
		btnNewTask.setName("btnNewTask");
		int i = 0;
		for(Task t: userTasksSorted){
			c.gridx = i % maxCols;
			c.gridy = i / maxCols;
			makeTaskCard(t, c);
			i++;
		}
		c.gridx = maxCols-1;
		c.gridy = i / maxCols;
		add(btnNewTask, c);
	}

	private void goToNewTask(ActionEvent e) {
		JFrame windowAncestor = (JFrame) SwingUtilities.getWindowAncestor(this);
		windowAncestor.setContentPane(new NewUpdateTask(model, user));
		windowAncestor.setTitle("New task");
		windowAncestor.pack();
	}

	private JPanel makeTaskCard(Task t, GridBagConstraints c) {
		JPanel taskPanel = new JPanel();
		taskPanel.addMouseListener(new TaskDetailTransition(this, model, t, user));
		Color backgroundColor = AppColors.getColorBackground(t);
		taskPanel.setBackground(backgroundColor);
		taskPanel.setBorder(new LineBorder(backgroundColor, 5, true));
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

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		JLabel lblDateTask = new JLabel(dateFormat.format(t.getDeadline()));
		lblDateTask.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDateTask.setFont(new Font("Cantarell", Font.BOLD, 14));
		lblDateTask.setName("lblDateTask" + t.getId());
		headerPanelTask.add(lblDateTask);

		JPanel contentPanelTask = new JPanel();
		contentPanelTask.setBackground(new Color(0, 0, 0, 0));
		taskPanel.add(contentPanelTask);
		contentPanelTask.setLayout(new GridLayout(1, 0, 0, 0));

		JLabel lblDescrTask = new JLabel(formatDescription(t.getDescription()));
		lblDescrTask.setMaximumSize(new Dimension(150, 1500));
		lblDescrTask.setFont(new Font("Cantarell", Font.PLAIN, 10));
		lblDescrTask.setName("lblDescrTask" + t.getId());
		contentPanelTask.add(lblDescrTask);

		return taskPanel;
	}

	private String formatDescription(String description) {
		if(description.length() > 50){
			return description.substring(0, 50) + "...";
		}
		return description;
	}

}

