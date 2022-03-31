package edu.mikedev.task_manager.utils;

import org.hibernate.SessionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public abstract class HibernateDBUtilsAbs {
    protected SessionFactory sessionFactory;

    public HibernateDBUtilsAbs(){
        this.sessionFactory = buildSessionFactory();
    }

    public HibernateDBUtilsAbs(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    public abstract SessionFactory buildSessionFactory();

    public abstract void initDBTables() throws SQLException;

    public abstract Connection initDBConnection() throws SQLException;

    protected Connection initDBConnection(String url, String username, String password) throws SQLException {
        Properties props = new Properties();
        final String userField = "user";
        final String passwordField = "password";
        props.setProperty(userField, username);
        props.setProperty(passwordField, password);

        return DriverManager.getConnection(url, props);
    }

    public List<String> getDBTaskTitles() {
        return pullListStringFromDB("select * from Tasks", "title");
    }

    public List<String> getDBUsernames() {
        return pullListStringFromDB("select * from Users", "username");
    }

    private List<String> pullListStringFromDB(String query, String fieldName) {
        List<String> resultList = new ArrayList<>();
        Connection connection = null;
        ResultSet valuesDBIterator = null;
        try {
            connection = initDBConnection();
            valuesDBIterator = connection.createStatement().executeQuery(query);
            while (valuesDBIterator.next()) {
                String value = valuesDBIterator.getString(fieldName);
                resultList.add(value);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
