package edu.mikedev.task_manager.utils;

import edu.mikedev.task_manager.data.Task;
import edu.mikedev.task_manager.data.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class HibernateDBUtilsInMemory extends HibernateDBUtilsAbs{

    public List<User> users = null;

    @Override
    public SessionFactory buildSessionFactory() {
        Path testResourceDirectory = Paths.get("src", "test", "resources");
        File hibernateConfigFile = new File(testResourceDirectory.resolve("hibernate.inmemory.cfg.xml").toAbsolutePath().toString());

        Configuration cfg = new Configuration();
        return cfg.configure(hibernateConfigFile).buildSessionFactory();
    }

    @Override
    public void initDBTables() {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = initDBConnection(
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

        task1.setId(1);
        task2.setId(2);
        task3.setId(3);
        task4.setId(4);
        task5.setId(5);
        task6.setId(6);

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
        user1.setId(1);
        insertUser(statement, user1);

        User user2 = new User(username2, password2, email);
        user2.setTasks(new HashSet<>());
        for(Task t: taskSet2){
            t.setUser(user2);
        }
        user2.setId(2);

        insertUser(statement, user2);

        insertTask(statement, task1);
        insertTask(statement, task2);
        insertTask(statement, task3);
        insertTask(statement, task4);
        insertTask(statement, task5);
        insertTask(statement, task6);

        users = Arrays.asList(user1, user2);

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public Connection initDBConnection() throws SQLException {
        try {
            return initDBConnection(
                    "jdbc:hsqldb:mem:inmemorydb",
                    "sa",
                    ""
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
