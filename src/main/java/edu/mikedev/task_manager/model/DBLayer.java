package edu.mikedev.task_manager.model;

import edu.mikedev.task_manager.Task;
import edu.mikedev.task_manager.User;

import java.util.List;

public interface DBLayer {
    User getUserById(int id);

    Task getTaskById(int id);

    void add(Task task);

    void add(User user);

    List<Task> getTasks();

    List<Task> getTasks(int userId);

    void delete(User user);

    void deleteTask(User taskOwner, Task task);

    List<String> getUsernames();

    List<Integer> getUserIds();

    List<User> getUsers(String username, String password);

    List<User> getUsers(String username);

    List<User> getUsers();

    void update(Task task);

    Task getTaskByIdWithUserId(int id, int userId);

    List<Integer> getTasksId();

    void closeConnection();
}
