package edu.mikedev.task_manager.utils;

import edu.mikedev.task_manager.Task;
import edu.mikedev.task_manager.User;
import org.hibernate.Session;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class InMemoryDBConnectionHandler implements DBConnectionHandler{
    @Override
    public void initDB() {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = initConnection(
                    "jdbc:hsqldb:mem:inmemorydb",
                    "sa",
                    ""
            );
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Set<Task> taskSet1 = new HashSet<>();
        Task task1 = null;
        Task task2 = null;
        Task task3 = null;
        Task task4 = null;
        Task task5 = null;
        Task task6 = null;
        String username1 = "username1";
        String password1 = "password1";
        String username2 = "username";
        String password2 = "password";
        String email = "email@email.com";
        Set<Task> taskSet2 = new HashSet<>();
        try {
            task1 = new Task("title1", "description1", formatter.parse("13/05/2015"), false);
            task2 = new Task("title2", "description2", formatter.parse("23/05/2016"), false);
            task3 = new Task("title3", "description3", formatter.parse("21/10/2020"), true);
            task4 = new Task("title4", "description4", formatter.parse("01/02/2022"), false);
            task5 = new Task("title5", "description5", formatter.parse("22/05/2015"), true);
            task6 = new Task("title6", "description6", formatter.parse("15/12/2018"), false);
        } catch (ParseException e) {
            // Parse catch that should never happen
        }

        taskSet1.add(task1);
        taskSet1.add(task2);
        taskSet1.add(task3);
        taskSet2.add(task4);
        taskSet2.add(task5);
        taskSet2.add(task6);

        User user1 = new User(username1, password1, email);
        user1.setTasks(new HashSet<>());
        for(Task t: taskSet1){
            t.setUser(user1);
        }
        insertUser(statement, user1);

        User user2 = new User(username2, password2, email);
        user2.setTasks(new HashSet<>());
        for(Task t: taskSet2){
            t.setUser(user2);
        }

        insertUser(statement, user2);

        insertTask(statement, task1);
        insertTask(statement, task2);
        insertTask(statement, task3);
        insertTask(statement, task4);
        insertTask(statement, task5);
        insertTask(statement, task6);

        closeConnection(connection);
    }

    private void insertUser(Statement statement, User u){
        try {
            statement.execute("INSERT INTO users (username, password, email) " +
                    String.format("VALUES (%s, %s, %s)", u.getUsername(), u.getPassword(), u.getEmail()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertTask(Statement statement, Task t){
        try {
            statement.execute("INSERT INTO tasks (title, description, deadline, done, ID_USER) " +
                    String.format("VALUES (%s, %s, %tF, %b, %d); ", t.getTitle(), t.getDescription(), t.getDeadline(), t.isDone(), t.getUser().getId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
