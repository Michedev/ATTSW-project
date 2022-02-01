package edu.mikedev.task_manager.ui;

import edu.mikedev.task_manager.Task;
import edu.mikedev.task_manager.User;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class TaskDetailTransition extends MouseAdapter {

    private JPanel panel;
    private Task task;
    private User user;

    public TaskDetailTransition(JPanel panel, Task task, User user) {

        this.panel = panel;
        this.task = task;
        this.user = user;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JFrame window = (JFrame) SwingUtilities.getWindowAncestor(panel);
        window.setTitle("Task Details");
        window.setContentPane(new TaskDetailPage(task, user));
        window.pack();
    }

}
