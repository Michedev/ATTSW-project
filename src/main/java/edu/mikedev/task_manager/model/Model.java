package edu.mikedev.task_manager.model;

import edu.mikedev.task_manager.Task;
import edu.mikedev.task_manager.User;

import java.util.List;

public interface Model {

    boolean areCredentialCorrect(String username, String password);

    boolean userExists(String username);

    User registerUser(String username, String password, String email);

    User loginUser(String username, String password);

    void updateTask(Task task);

    void addNewTask(Task task);

    void deleteTask(Task task);

    Task getTaskById(int id);

    List<Task> getTasks();

    void logout();

    boolean isUserLogged();
}
