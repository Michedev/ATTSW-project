package edu.mikedev.task_manager.utils;

import edu.mikedev.task_manager.Task;
import edu.mikedev.task_manager.User;
import edu.mikedev.task_manager.model.DBLayer;
import edu.mikedev.task_manager.model.HibernateDBLayer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class HibernateDBUtils {
    private SessionFactory sessionFactory;
    private Session session;

    private final String userField = "user";
    private final String passwordField = "password";

    public static SessionFactory buildHBSession(){
        Path testResourceDirectory = Paths.get("src", "test", "resources");
        File hibernateConfigFile = new File(testResourceDirectory.resolve("hibernate.cfg.xml").toAbsolutePath().toString());

        Configuration cfg = new Configuration();
        return cfg.configure(hibernateConfigFile).buildSessionFactory();
    }

    public static SessionFactory buildHBSessionInMemory(){
        Path testResourceDirectory = Paths.get("src", "test", "resources");
        File hibernateConfigFile = new File(testResourceDirectory.resolve("hibernate.inmemory.cfg.xml").toAbsolutePath().toString());

        Configuration cfg = new Configuration();
        return cfg.configure(hibernateConfigFile).buildSessionFactory();
    }


    public HibernateDBUtils(){
        this.sessionFactory = buildHBSession();
        session = sessionFactory.openSession();
    }

    public HibernateDBUtils(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
        this.session = sessionFactory.openSession();

    }

    public void initRealTestDB() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/";
        Properties props = new Properties();
        props.setProperty(userField, "root");
        props.setProperty(passwordField, "root");

        Connection conn = DriverManager.getConnection(url, props);
        Statement statement = conn.createStatement();

        statement.execute("DELETE FROM tasks;");
        statement.execute("DELETE FROM users;");
        statement.execute("COPY Users FROM '/db/fake-data/sample_user.csv' DELIMITER ',' CSV HEADER;");
        statement.execute("COPY Tasks FROM '/db/fake-data/sample_task.csv' DELIMITER ',' CSV HEADER;");
    }

    public void initInMemoryTestDB() throws SQLException {
        String url = "jdbc:hsqldb:mem:inmemorydb";
        Properties props = new Properties();
        props.setProperty("user", "sa");
        props.setProperty(passwordField, "");

        DriverManager.getConnection(url, props);
    }

    public void addFakeUsers(){
        addFakeUsers(new HibernateDBLayer(session));
    }

    public List<User> addFakeUsers(DBLayer dbLayer){
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
        dbLayer.add(user1);

        User user2 = new User(username2, password2, email);
        user2.setTasks(new HashSet<>());
        for(Task t: taskSet2){
            t.setUser(user2);
        }
        dbLayer.add(user2);

        dbLayer.add(task1);
        dbLayer.add(task2);
        dbLayer.add(task3);
        dbLayer.add(task4);
        dbLayer.add(task5);
        dbLayer.add(task6);

        user1.setTasks(taskSet1);
        user2.setTasks(taskSet2);

        return Arrays.asList(user1, user2);
    }

    public List<User> pullUsers() {
        return session.createQuery("SELECT a FROM User a", User.class).getResultList();
    }

    public List<Task> pullTasks() {
        return session.createQuery("SELECT a FROM Task a", Task.class).getResultList();
    }

    public List<String> pullTaskTitles(){
        return session.createQuery("SELECT title from Task", String.class).getResultList();
    }
    
    public User getUserByID(int id){
        return session.createQuery(String.format("SELECT a from User a where id = %d", id), User.class).getResultList().get(0);
    }

    public Task getTaskByID(int id){
        return session.createQuery(String.format("SELECT a from Task a where id = %d", id), Task.class).getResultList().get(0);
    }

    public Session getSession() {
        return session;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}