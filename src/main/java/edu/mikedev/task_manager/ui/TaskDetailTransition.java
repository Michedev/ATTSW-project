package edu.mikedev.task_manager.ui;

import edu.mikedev.task_manager.model.Model;
import edu.mikedev.task_manager.Task;
import edu.mikedev.task_manager.User;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class TaskDetailTransition extends MouseAdapter {

    private JPanel panel;
    private Task task;
    private User user;
    private Model model;

    public TaskDetailTransition(JPanel panel, Model model, Task task, User user) {
        this.model = model;
        this.panel = panel;
        this.task = task;
        this.user = user;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JFrame window = (JFrame) SwingUtilities.getWindowAncestor(panel);
        window.setTitle("Task Details");
        window.setContentPane(new TaskDetailPage(model, task, user));
        window.pack();
    }

}
