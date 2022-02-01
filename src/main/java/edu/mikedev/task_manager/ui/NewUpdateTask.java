package edu.mikedev.task_manager.ui;

import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JButton;

public class NewUpdateTask extends JPanel {
	private JTextField tfTaskName;
	private JTextField tfTaskDescription;
	private JTextField tfDeadline;

	/**
	 * Create the panel.
	 */
	public NewUpdateTask() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		
		JLabel lblTaskDescription = new JLabel("Task description");
		GridBagConstraints gbc_lblTaskDescription = new GridBagConstraints();
		gbc_lblTaskDescription.insets = new Insets(0, 0, 5, 0);
		gbc_lblTaskDescription.gridx = 0;
		gbc_lblTaskDescription.gridy = 4;
		add(lblTaskDescription, gbc_lblTaskDescription);
		
		tfTaskDescription = new JTextField();
		tfTaskDescription.setName("tfTaskDescription");
		GridBagConstraints gbc_tfTaskDescription = new GridBagConstraints();
		gbc_tfTaskDescription.insets = new Insets(0, 0, 5, 0);
		gbc_tfTaskDescription.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfTaskDescription.gridx = 0;
		gbc_tfTaskDescription.gridy = 5;
		add(tfTaskDescription, gbc_tfTaskDescription);
		tfTaskDescription.setColumns(10);
		
		JLabel lblDeadline = new JLabel("Deadline (dd/MM/yyyy)");
		lblDeadline.setName("lblDeadline");
		GridBagConstraints gbc_lblDeadline = new GridBagConstraints();
		gbc_lblDeadline.insets = new Insets(0, 0, 5, 0);
		gbc_lblDeadline.gridx = 0;
		gbc_lblDeadline.gridy = 7;
		add(lblDeadline, gbc_lblDeadline);
		
		tfDeadline = new JTextField();
		tfDeadline.setName("tfDeadline");
		GridBagConstraints gbc_tfDeadline = new GridBagConstraints();
		gbc_tfDeadline.insets = new Insets(0, 0, 5, 0);
		gbc_tfDeadline.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfDeadline.gridx = 0;
		gbc_tfDeadline.gridy = 8;
		add(tfDeadline, gbc_tfDeadline);
		tfDeadline.setColumns(10);
		
		JButton btnSave = new JButton("Save");
		btnSave.setName("btnSave");
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.gridx = 0;
		gbc_btnSave.gridy = 10;
		add(btnSave, gbc_btnSave);
	}

}
