package edu.mikedev.task_manager.controller;

import edu.mikedev.task_manager.data.Task;
import edu.mikedev.task_manager.ui.NewUpdateTaskPage;
import edu.mikedev.task_manager.ui.TaskDetailPage;
import edu.mikedev.task_manager.ui.UserTasksPage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class UserTasksPageController extends TaskPageController<UserTasksPage> {

    public UserTasksPageController(UserTasksPage panel, TaskManagerController controller) {
        super(panel, controller);
    }


    @Override
    public void setGUIBindings() {
        List<Task> tasks = controller.getModel().getUserTasks();
        List<JPanel> tasksPanel = panel.getTasksPanel();
        for (int i = 0; i < tasks.size(); i++) {
            JPanel taskPanel = tasksPanel.get(i);
            final Task task = tasks.get(i);
            taskPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    TaskDetailPage taskDetailPage = new TaskDetailPage(task);
                    TaskDetailPageController taskPageController = new TaskDetailPageController(taskDetailPage, controller, task);
                    controller.setContentPane(taskPageController, task.getTitle());
                }
            });
        }
        panel.getBtnNewTask().addActionListener(this::goToNewTask);

    }

    private void goToNewTask(ActionEvent e) {
        NewUpdateTaskPage newUpdateTaskPage = new NewUpdateTaskPage();
        NewUpdateTaskPageController pageController = new NewUpdateTaskPageController(newUpdateTaskPage, controller);
        controller.setContentPane(pageController, "New task");
    }

}
