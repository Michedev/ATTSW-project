package edu.mikedev.task_manager.controller;

import edu.mikedev.task_manager.Task;
import edu.mikedev.task_manager.User;
import edu.mikedev.task_manager.model.HibernateModel;
import edu.mikedev.task_manager.model.Model;
import edu.mikedev.task_manager.ui.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TaskManagerController {

    private final JFrame window;
    private final Model model;
    private JPanel currentPanel = null;
    private LoginWindow loginWindow = null;
    private UserTasksPage userTasksPage = null;
    private TaskDetailPage taskDetailPage = null;
    private NewUpdateTaskPage newUpdateTaskPage = null;
    private RegistrationPage registrationPage = null;

    public TaskManagerController(Model model, LoginWindow window){
        this.window = window;
        this.model = model;
        this.loginWindow = window;
        setGuiBindingsLoginPage();
    }


    public void setGuiBindingsLoginPage(){
        loginWindow.getBtnLogin().addActionListener(this::loginClickBtn);
        loginWindow.getBtnRegister().addActionListener(this::registrationClickBtn);
    }

    public void setGuiBindingsTaskDetailPage(Task task){
        taskDetailPage.getBtnGoBack().addActionListener(this::goBackAction);
        taskDetailPage.getBtnDelete().addActionListener(this::deleteTask);
        taskDetailPage.getCbDone().addActionListener(this::toggleDone);
    }

    public void setGuiBindingsNewUpdateTask(){
        newUpdateTaskPage.getBtnSave().addActionListener(this::saveTask);
    }

    private void deleteTask(ActionEvent e) {
        model.deleteTask(taskDetailPage.getTask());
        goBackAction(e);

    }

    public void setGUIBindingsUsersTasksPage(List<Task> tasks){
        List<JPanel> tasksPanel = userTasksPage.getTasksPanel();
        for (int i = 0; i < tasks.size(); i++) {
            JPanel taskPanel = tasksPanel.get(i);
            final Task task = tasks.get(i);
            taskPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    taskDetailPage = new TaskDetailPage(task);
                    setGuiBindingsTaskDetailPage(task);
                    currentPanel = taskDetailPage;

                }
            });
        }
        userTasksPage.getBtnNewTask().addActionListener(this::goToNewTask);
    }

    private void loginClickBtn(ActionEvent e){
        String username = loginWindow.getTfUsername().getText();
        String password = loginWindow.getTfPassword().getText();
        boolean isCorrect = model.areCredentialCorrect(username, password);
        if(isCorrect){
            window.setTitle(username + " tasks");
            User loggedUser = model.loginUser(username, password);
            List<Task> sortedTasks = getSortedUserTasks();
            userTasksPage = new UserTasksPage(sortedTasks);
            setGUIBindingsUsersTasksPage(sortedTasks);
            currentPanel = userTasksPage;
            loginWindow = null;
            window.setContentPane(userTasksPage);
            window.setMinimumSize(new Dimension(400, 300));
            window.pack();
        } else {
            loginWindow.getLblErrorMessage().setText("Username and/or password are wrong");
            loginWindow.getLblErrorMessage().setEnabled(true);
            loginWindow.getLblErrorMessage().setVisible(true);

        }
    }

    private void registrationClickBtn(ActionEvent e){
        window.getContentPane().removeAll();
        window.setTitle("Registration page");
        registrationPage = new RegistrationPage();
        setGuiBindingsRegistrationPage();
        currentPanel = registrationPage;
        window.getContentPane().add(registrationPage);
        window.pack();
    }

    private void setGuiBindingsRegistrationPage() {
        registrationPage.getBtnRegister().addActionListener(this::registrationEvent);
    }

    private void goBackAction(ActionEvent e){
        userTasksPage = new UserTasksPage(getSortedUserTasks());
        currentPanel = userTasksPage;
        taskDetailPage = null;
        window.setContentPane(userTasksPage);
        window.setTitle(model.getLoggedUser().getUsername() + " tasks");
        window.pack();
    }

    private List<Task> getSortedUserTasks() {
        return model.getTasks().stream().sorted(Comparator.comparingInt(Task::getId)).collect(Collectors.toList());
    }

    private void saveTask(ActionEvent e) {
        Task task = newUpdateTaskPage.parseTask();
        if(task != null){
            if(newUpdateTaskPage.isUpdateMode()){
                Task toBeUpdatedTask = newUpdateTaskPage.getToBeUpdatedTask();
                toBeUpdatedTask.setTitle(task.getTitle());
                toBeUpdatedTask.setDescription(task.getDescription());
                toBeUpdatedTask.setDeadline(task.getDeadline());
                model.updateTask(toBeUpdatedTask);
            } else {
                model.addNewTask(task);
            }
            window.setTitle(model.getLoggedUser().getUsername() + " tasks");
            List<Task> sortedUserTasks = getSortedUserTasks();
            System.out.println(sortedUserTasks);
            userTasksPage = new UserTasksPage(sortedUserTasks);
            window.removeAll();
            setGUIBindingsUsersTasksPage(sortedUserTasks);
            window.setContentPane(userTasksPage);
            window.setTitle(model.getLoggedUser().getUsername() + " tasks");
            window.pack();
        }
    }

    private void goToNewTask(ActionEvent e) {
        newUpdateTaskPage = new NewUpdateTaskPage();
        setGuiBindingsNewUpdateTask();
        window.setContentPane(newUpdateTaskPage);
        window.setTitle("New task");
        window.pack();
    }


    private void toggleDone(ActionEvent e) {
        boolean newValue = taskDetailPage.getCbDone().isSelected();
        Task task = taskDetailPage.getTask();
        task.setDone(newValue);
        Color backgroundColor = AppColors.getColorBackground(task);
        taskDetailPage.setBackground(backgroundColor);
        model.updateTask(task);
    }

    private void registrationEvent(ActionEvent e){
        User newUser = parseUserIfCorrect();
        if(newUser != null){
            model.registerUser(newUser);
            final JOptionPane optionPane = new JOptionPane(
                    String.format("Username %s successfully registered", newUser.getUsername()),
                    JOptionPane.INFORMATION_MESSAGE,
                    JOptionPane.OK_OPTION);

            final JDialog dialog = new JDialog(window,
                    "Registration completed",
                    true);
            dialog.setContentPane(optionPane);
            dialog.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                    loginWindow = new LoginWindow();
                    setGuiBindingsLoginPage();
                    window.setContentPane(loginWindow);
                }
            });
            dialog.pack();
            dialog.setVisible(true);
            //todo implement test about this
        }
    }

    private User parseUserIfCorrect() {
        boolean correct = true;
        JLabel lblErrorMessageUsername = registrationPage.getLblErrorMessageUsername();
        if(registrationPage.getTfUsername().getText().isEmpty()){
            lblErrorMessageUsername.setVisible(true);
            lblErrorMessageUsername.setText("Missing username");
            correct = false;
        } else {
            if(model.userExists(registrationPage.getTfUsername().getText())){
                lblErrorMessageUsername.setVisible(true);
                lblErrorMessageUsername.setText("Username exists");
                correct = false;
            }
        }
        char[] password = registrationPage.getTfPassword().getPassword();
        JLabel lblErrorMessagePassword = registrationPage.getLblErrorMessagePassword();
        if(password.length == 0){
            lblErrorMessagePassword.setVisible(true);
            lblErrorMessagePassword.setText("Missing password");
            correct = false;
        }
        JLabel lblErrorMessageEmail = registrationPage.getLblErrorMessageEmail();
        if(registrationPage.getTfEmail().getText().isEmpty()){
            lblErrorMessageEmail.setVisible(true);
            lblErrorMessageEmail.setText("Missing e-mail");
            correct = false;
        } else {
            String regexEmail = "[\\w]+@[\\w]+\\.[\\w]{1,5}";
            if(!registrationPage.getTfEmail().getText().matches(regexEmail)){
                lblErrorMessageEmail.setVisible(true);
                lblErrorMessageEmail.setText("The input prompted above is not an e-mail");
                correct = false;
            }
        }
        if(!correct){
            return null;
        }
        return new User(registrationPage.getTfUsername().getText(), new String(password), registrationPage.getTfEmail().getText());
    }




}
