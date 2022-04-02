package edu.mikedev.task_manager.ui;

import edu.mikedev.task_manager.data.Task;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UserTasksPage extends JPanel {

	private static final long serialVersionUID = -4905197020820980836L;
	
	private final JButton btnNewTask;
	private List<JPanel> tasksPanel;

	public UserTasksPage(List<Task> userTasks) {
		tasksPanel = new ArrayList<>();
		GridBagLayout gridBagLayout = new GridBagLayout();
		int maxCols = 3;
		setLayout(gridBagLayout);
		List<Task> userTasksSorted = userTasks.stream().sorted((a, b) -> Comparator.comparingInt(Task::getId).compare(a,b)).collect(Collectors.toList());
		btnNewTask = new JButton("+");
		btnNewTask.setName("btnNewTask");
		int i = 0;
		for(Task t: userTasksSorted){
			GridBagConstraints c = new GridBagConstraints();
			c.weightx = 0.9;
			c.weighty = 0.9;
			c.gridx = i % maxCols;
			c.gridy = i / maxCols;
			tasksPanel.add(makeTaskCard(t, c));
			i++;
		}
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 0.9;
		c.weighty = 0.9;

		c.gridx = maxCols-1;
		c.gridy = i / maxCols + 1;
		c.insets = new Insets(0, 0, 20, 0);
		add(btnNewTask, c);
	}

	private JPanel makeTaskCard(Task t, GridBagConstraints c) {
		String fontFamily = "Cantarell";

		JPanel taskPanel = new JPanel();
		Color backgroundColor = AppColors.getColorBackground(t);
		Color backgroundColorWhenMouseOver = backgroundColor.brighter();
		taskPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				taskPanel.setBackground(backgroundColorWhenMouseOver);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				taskPanel.setBackground(backgroundColor);
			}
		});
		taskPanel.setBackground(backgroundColor);
		taskPanel.setBorder(new LineBorder(Color.black, 2, true));
		taskPanel.setName("task" + t.getId());
		c.insets = new Insets(20, 20, 20 , 20);
		add(taskPanel, c);
		taskPanel.setLayout(new GridLayout(2, 1, 5, 5));

		JPanel headerPanelTask = new JPanel();
		headerPanelTask.setBorder(new EmptyBorder(5, 5, 5, 5));
		headerPanelTask.setBackground(new Color(0, 0, 0, 0));
		taskPanel.add(headerPanelTask);
		headerPanelTask.setLayout(new GridLayout(1, 2, 5, 0));

		JLabel lblTitleTask0 = new JLabel(t.getTitle());
		lblTitleTask0.setFont(new Font(fontFamily, Font.BOLD, 18));
		lblTitleTask0.setName("lblTitleTask" + t.getId());
		headerPanelTask.add(lblTitleTask0);

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		JLabel lblDateTask = new JLabel(dateFormat.format(t.getDeadline()));
		lblDateTask.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDateTask.setFont(new Font(fontFamily, Font.BOLD, 16));
		lblDateTask.setName("lblDateTask" + t.getId());
		headerPanelTask.add(lblDateTask);

		JPanel contentPanelTask = new JPanel();
		contentPanelTask.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanelTask.setBackground(new Color(0, 0, 0, 0));
		taskPanel.add(contentPanelTask);
		contentPanelTask.setLayout(new GridLayout(1, 0, 0, 0));

		JLabel lblDescrTask = new JLabel(formatDescription(t.getDescription()));
		lblDescrTask.setBorder(new EmptyBorder(5, 5, 5, 5));
		lblDescrTask.setMaximumSize(new Dimension(150, 1500));
		lblDescrTask.setFont(new Font(fontFamily, Font.PLAIN, 10));
		lblDescrTask.setName("lblDescrTask" + t.getId());
		contentPanelTask.add(lblDescrTask);

		return taskPanel;
	}

	public static String htmlWrappedDescription(String description){
		return "<html><p style=\"width:75px\">" + description + "</p></html>";
	}

	private String formatDescription(String description) {
		if(description.length() > 50){
			return htmlWrappedDescription(description.substring(0, 50) + "...");
		}

		return htmlWrappedDescription(description);
	}

	public List<JPanel> getTasksPanel() {
		return tasksPanel;
	}

	public JButton getBtnNewTask() {
		return btnNewTask;
	}
}

