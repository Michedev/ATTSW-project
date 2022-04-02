package edu.mikedev.task_manager.controller;

import edu.mikedev.task_manager.data.Task;
import edu.mikedev.task_manager.ui.LoginPage;
import edu.mikedev.task_manager.ui.RegistrationPage;
import edu.mikedev.task_manager.ui.UserTasksPage;

import java.awt.event.ActionEvent;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LoginPageController extends TaskPageController<LoginPage> {

    public LoginPageController(LoginPage panel, TaskManagerController controller) {
        super(panel, controller);
    }

    private List<Task> getSortedUserTasks() {
        return model.getUserTasks().stream().sorted(Comparator.comparingInt(Task::getId)).collect(Collectors.toList());
    }


    public void loginEvent(ActionEvent e) {
        String username = panel.getTfUsername().getText();
        String password = panel.getTfPassword().getText();
        boolean isCorrect = model.areCredentialCorrect(username, password);
        if(isCorrect){
            model.loginUser(username, password);
            List<Task> sortedTasks = getSortedUserTasks();
            UserTasksPage userTasksPage = new UserTasksPage(sortedTasks);
            UserTasksPageController pageController = new UserTasksPageController(userTasksPage, controller);
            controller.setContentPane(pageController, String.format("%s tasks", username));
        } else {
            panel.getLblErrorMessage().setText("Username and/or password are wrong");
            panel.getLblErrorMessage().setEnabled(true);
            panel.getLblErrorMessage().setVisible(true);
            controller.getWindow().pack();
        }

    }

    @Override
    public void setGUIBindings() {
        panel.getBtnLogin().addActionListener(this::loginEvent);
        panel.getBtnRegister().addActionListener(this::goToRegistraionPage);
    }

    public void goToRegistraionPage(ActionEvent actionEvent) {
        RegistrationPage registrationPage = new RegistrationPage();
        RegistrationPageController pageController = new RegistrationPageController(registrationPage, controller);
        controller.setContentPane(pageController, "Registration page");
    }

}
