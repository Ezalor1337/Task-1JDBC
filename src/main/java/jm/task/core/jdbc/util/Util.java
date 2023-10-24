package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    private static final String URL = "jdbc:postgresql://localhost:5432/TestProject";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    public static Statement getStatement(Connection connection) throws SQLException {
        return connection.createStatement();
    }
}
