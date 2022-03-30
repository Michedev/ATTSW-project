package edu.mikedev.task_manager.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public interface DBConnectionHandler {



    default Connection initConnection(String url, String username, String password) throws SQLException {
        final String userField = "user";
        final String passwordField = "password";
        Properties props = new Properties();
        props.setProperty(userField, username);
        props.setProperty(passwordField, password);

        return DriverManager.getConnection(url, props);
    }

    void initDB();

    default void closeConnection(Connection connection){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
