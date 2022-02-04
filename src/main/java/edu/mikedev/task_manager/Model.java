package edu.mikedev.task_manager;

import java.util.List;

public interface Model {

    boolean areCredentialCorrect(String username, String password);

    boolean userExists(String username);

    User registerUser(String username, String password, String email);

    User getUser(String username, String password);

    boolean updateTask(Task task);

    void addNewTask(Task task);

    boolean deleteTask(Task task);

    boolean existsTaskId(int id);

    Task getTaskById(int id);

    List<Task> getTasks();
}
