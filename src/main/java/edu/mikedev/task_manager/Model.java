package edu.mikedev.task_manager;

import javax.security.auth.login.LoginException;
import java.util.List;

public interface Model {

    boolean areCredentialCorrect(String username, String password);

    boolean userExists(String username);

    User registerUser(String username, String password, String email);

    void addUser(User user);

    User loginUser(String username, String password);

    boolean existsUsername(String username);

    void updateTask(Task task);

    void addNewTask(Task task);

    void deleteTask(Task task);

    boolean existsTaskId(int id);

    Task getTaskById(int id);

    List<Task> getTasks();

    void logout();
}
