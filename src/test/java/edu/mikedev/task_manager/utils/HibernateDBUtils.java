package edu.mikedev.task_manager.utils;

import edu.mikedev.task_manager.DBLayer;
import edu.mikedev.task_manager.HibernateDBLayer;
import edu.mikedev.task_manager.Task;
import edu.mikedev.task_manager.User;
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
    private Session session;

    public static Session buildHBSession(){
        Path testResourceDirectory = Paths.get("src", "main", "resources");
        File hibernateConfigFile = new File(testResourceDirectory.resolve("hibernate.cfg.xml").toAbsolutePath().toString());

        Configuration cfg = new Configuration();
        SessionFactory factory = cfg.configure(hibernateConfigFile).buildSessionFactory();

        return factory.openSession();
    }

    public static Session buildHBSessionInMemory(){
        Path testResourceDirectory = Paths.get("src", "test", "resources");
        File hibernateConfigFile = new File(testResourceDirectory.resolve("hibernate.inmemory.cfg.xml").toAbsolutePath().toString());

        Configuration cfg = new Configuration();
        SessionFactory factory = cfg.configure(hibernateConfigFile).buildSessionFactory();

        return factory.openSession();
    }


    public HibernateDBUtils(){
        this.session = buildHBSession();
    }

    public HibernateDBUtils(Session session) {
        this.session = session;
    }

    public void initRealTestDB() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/root";
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "root");

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
        props.setProperty("password", "");

        Connection conn = DriverManager.getConnection(url, props);
    }

    public void addFakeUsers(){
        addFakeUsers(new HibernateDBLayer(session));
    }

    public List<User> addFakeUsers(DBLayer dbLayer){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Set<Task> taskSet = new HashSet<Task>();
        Task task1 = null;
        Task task2 = null;
        Task task3 = null;
        Task task4 = null;
        Task task5 = null;
        Task task6 = null;
        Set<Task> taskSet1 = new HashSet<>();
        try {
            task1 = new Task("title1", "description1", formatter.parse("13/05/2015"), false);
            task1.setId(5);
            task2 = new Task("title2", "description2", formatter.parse("23/05/2016"), false);
            task2.setId(9);
            task3 = new Task("title3", "description3", formatter.parse("21/10/2020"), true);
            task3.setId(12);

            task4 = new Task("title4", "description4", formatter.parse("01/02/2022"), false);
            task4.setId(0);
            task5 = new Task("title5", "description5", formatter.parse("22/05/2015"), true);
            task5.setId(1);
            task6 = new Task("title6", "description6", formatter.parse("15/12/2018"), false);
            task6.setId(2);

        } catch (ParseException e) {

        }

        taskSet.add(task1);
        taskSet.add(task2);
        taskSet.add(task3);

        User user1 = new User("username1", "password1", "email@email.com");
        user1.setId(50);
        user1.setTasks(taskSet);
        for(Task t: taskSet){
            t.setUser(user1);
        }

        dbLayer.add(user1);


        taskSet1.add(task4);
        taskSet1.add(task5);
        taskSet1.add(task6);

        User user2 = new User("username", "password", "email@email.com");
        user2.setId(1);
        user2.setTasks(taskSet1);
        for(Task t: taskSet1){
            t.setUser(user2);
        }

        dbLayer.add(user2);
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
}