package edu.mikedev.task_manager.controller;

import edu.mikedev.task_manager.data.Task;
import edu.mikedev.task_manager.ui.AppColors;
import edu.mikedev.task_manager.ui.NewUpdateTaskPage;
import edu.mikedev.task_manager.ui.TaskDetailPage;
import edu.mikedev.task_manager.ui.UserTasksPage;

import java.awt.*;
import java.awt.event.ActionEvent;

public class TaskDetailPageController extends TaskPageController<TaskDetailPage> {
    private final Task task;

    public TaskDetailPageController(TaskDetailPage panel, TaskManagerController controller, Task task) {
        super(panel, controller);
        this.task = task;
    }

    @Override
    public void setGUIBindings(){
        panel.getBtnUpdate().addActionListener(this::goToUpdateTaskPage);
        panel.getBtnGoBack().addActionListener(this::goBackAction);
        panel.getBtnDelete().addActionListener(this::deleteTask);
        panel.getCbDone().addActionListener(this::toggleDone);

    }

    private void toggleDone(ActionEvent e) {
        boolean newValue = panel.getCbDone().isSelected();
        task.setDone(newValue);
        Color backgroundColor = AppColors.getColorBackground(task);
        panel.setBackground(backgroundColor);
        model.updateTask(task);
    }

    private void deleteTask(ActionEvent e) {
        model.deleteTask(panel.getTask());
        goBackAction(e);
    }

    private void goBackAction(ActionEvent e){
        UserTasksPage userTasksPage = new UserTasksPage(model.getUserTasks());
        UserTasksPageController pageController = new UserTasksPageController(userTasksPage, controller);
        controller.setContentPane(pageController, model.getLoggedUser().getUsername() + " tasks");
    }

    private void goToUpdateTaskPage(ActionEvent e) {
        NewUpdateTaskPage newUpdateTaskPage = new NewUpdateTaskPage(task);
        NewUpdateTaskPageController pageController = new NewUpdateTaskPageController(newUpdateTaskPage, controller);
        controller.setContentPane(pageController, String.format("Update task \"%s\"", task.getTitle()));
    }

}
