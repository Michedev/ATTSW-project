package edu.mikedev.task_manager;

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
import java.util.List;
import java.util.Properties;

public class HibernateDBUtils {
    private Session session;

    public static Session buildHBSession(){
        Path testResourceDirectory = Paths.get("src", "main", "resources");
        File hibernateConfigFile = new File(testResourceDirectory.resolve("hibernate.cfg.xml").toAbsolutePath().toString());

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

    public void initTestDB() throws SQLException {
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

    public List<User> pullUsers() {
        return session.createQuery("SELECT a FROM User a", User.class).getResultList();
    }

    public List<Task> pullTasks() {
        return session.createQuery("SELECT a FROM Task a", Task.class).getResultList();
    }

    public List<String> pullTaskTitles(){
        return session.createQuery("SELECT title from Task", String.class).getResultList();
    }

    public Session getSession() {
        return session;
    }
}