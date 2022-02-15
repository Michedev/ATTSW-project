package edu.mikedev.task_manager;

import java.util.List;

public interface DBLayer {
    User getUserByID(int id);

    Task getTaskByID(int id);

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

    void update(Task task);

    Task getTaskByIdWithUserId(int id, int id_user);

    List<Integer> getTasksId();
}
