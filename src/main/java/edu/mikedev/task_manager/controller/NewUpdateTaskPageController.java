package edu.mikedev.task_manager.controller;

import edu.mikedev.task_manager.Task;
import edu.mikedev.task_manager.ui.NewUpdateTaskPage;
import edu.mikedev.task_manager.ui.UserTasksPage;

import java.awt.event.ActionEvent;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class NewUpdateTaskPageController extends TaskPageController<NewUpdateTaskPage> {

    public NewUpdateTaskPageController(NewUpdateTaskPage panel, TaskManagerController controller) {
        super(panel, controller);
    }

    @Override
    public void setGUIBindings() {
        panel.getBtnSave().addActionListener(this::saveTask);
    }


    private void saveTask(ActionEvent e) {
        Task task = panel.parseTask();
        if(task != null){
            if(panel.isUpdateMode()){
                Task toBeUpdatedTask = panel.getToBeUpdatedTask();
                toBeUpdatedTask.setTitle(task.getTitle());
                toBeUpdatedTask.setDescription(task.getDescription());
                toBeUpdatedTask.setDeadline(task.getDeadline());
                model.updateTask(toBeUpdatedTask);
            } else {
                model.addNewTask(task);
            }
            List<Task> sortedUserTasks = controller.getModel().getUserTasks().stream().sorted(Comparator.comparing(Task::getId)).collect(Collectors.toList());
            UserTasksPage userTasksPage = new UserTasksPage(sortedUserTasks);
            UserTasksPageController pageController = new UserTasksPageController(userTasksPage, controller);
            controller.setContentPane(pageController, model.getLoggedUser().getUsername() + " tasks");
        } else {
            controller.getWindow().pack();
        }
    }

}
