package edu.mikedev.task_manager.ui;

import edu.mikedev.task_manager.data.Task;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class TaskDetailPage extends JPanel {

	private static final long serialVersionUID = -995281578385483415L;

	private final JButton btnGoBack;
	private final JLabel lblTaskTitle;
	private final JLabel lblTaskDescription;
	private final JLabel lblDone;
	private final JButton btnUpdate;
	private final JButton btnDelete;
	/**
	 * Create the panel.
	 */
	private transient Task task;
	private transient JCheckBox cbDone;

	public TaskDetailPage(Task task) {
		setName("mainPanel");
		String fontFamily = "Cantarell";

		this.task = task;

		Color backgroundColor = AppColors.getColorBackground(task);
		setBackground(backgroundColor);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		btnGoBack = new JButton("<");
		btnGoBack.setName("btnGoBack");
		GridBagConstraints btnBackConstraints = new GridBagConstraints();
		btnBackConstraints.insets = new Insets(0, 0, 5, 5);
		btnBackConstraints.gridx = 0;
		btnBackConstraints.gridy = 0;
		add(btnGoBack, btnBackConstraints);

		lblTaskTitle = new JLabel(task.getTitle());
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

		lblTaskDescription = new JLabel("<html><p style=\"width:300px\">"+task.getDescription()+"</p></html>");
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

		GridBagConstraints cbDoneConstrains = new GridBagConstraints();
		cbDoneConstrains.gridx = 0;
		cbDoneConstrains.gridy = 3;
		add(cbDone, cbDoneConstrains);

		lblDone = new JLabel("Done");
		lblDone.setName("lblDone");

		GridBagConstraints lblDoneConstraints = new GridBagConstraints();
		lblDoneConstraints.gridx = 1;
		lblDoneConstraints.gridy = 3;
		add(lblDone, lblDoneConstraints);

		btnUpdate = new JButton("Update");
		btnUpdate.setName("btnUpdate");
		GridBagConstraints btnUpdateConstraints = new GridBagConstraints();
		btnUpdateConstraints.gridx = 2;
		btnUpdateConstraints.gridy = 3;
		btnUpdateConstraints.anchor = GridBagConstraints.LINE_END;
		add(btnUpdate, btnUpdateConstraints);

		btnDelete = new JButton("Delete");
		btnDelete.setName("btnDelete");
		btnDelete.setBackground(Color.RED);

		GridBagConstraints btnDeleteContraints = new GridBagConstraints();
		btnDeleteContraints.gridx = 3;
		btnDeleteContraints.gridy = 3;
		add(btnDelete, btnDeleteContraints);

	}


	public JButton getBtnGoBack() {
		return btnGoBack;
	}

	public JButton getBtnUpdate() {
		return btnUpdate;
	}

	public JButton getBtnDelete() {
		return btnDelete;
	}

	public JCheckBox getCbDone() {
		return cbDone;
	}

	public Task getTask() {
		return task;
	}
}
