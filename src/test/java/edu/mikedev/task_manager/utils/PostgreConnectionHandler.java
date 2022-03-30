package edu.mikedev.task_manager.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgreConnectionHandler implements DBConnectionHandler{

    @Override
    public void initDB() {
        Connection conn = null;
        try {
            conn = initConnection(
                    "jdbc:postgresql://localhost:5432/",
                    "root",
                    "root"
            );
            Statement statement = conn.createStatement();

            statement.execute("DELETE FROM tasks;");
            statement.execute("DELETE FROM users;");
            statement.execute("COPY Users FROM '/db/fake-data/sample_user.csv' DELIMITER ',' CSV HEADER;");
            statement.execute("COPY Tasks FROM '/db/fake-data/sample_task.csv' DELIMITER ',' CSV HEADER;");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert conn != null;
        closeConnection(conn);

    }
}
