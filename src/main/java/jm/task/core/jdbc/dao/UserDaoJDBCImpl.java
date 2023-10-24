package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final String CREATE_USER_TABLE = """
            CREATE TABLE IF NOT EXISTS users (
            USER_ID SERIAL PRIMARY KEY,
            USERNAME VARCHAR(100) NOT NULL,
            LASTNAME VARCHAR(100) NOT NULL,
            AGE int4 NOT NULL)
            """;
    private static final String DROP_USER_TABLE = """
            DROP TABLE IF EXISTS users
            """;

    private static final String INSERT_USER_INFO = """
            INSERT INTO users (USERNAME, LASTNAME, AGE) VALUES(?, ?, ?)
            """;

    private static final String DELETE_USER_ID = """
            DELETE FROM users WHERE USER_ID=?
            """;

    private static final String GET_USER_INFO = """
            SELECT USER_ID, USERNAME, LASTNAME, AGE FROM users
            """;

    private static final String CLEAR_TABLE = """
            DELETE FROM users
            """;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection()) {
            Statement statement = Util.getStatement(connection);
            statement.execute(CREATE_USER_TABLE);
            System.out.println("Pivot has been created");
        } catch (SQLException e) {
            System.out.println("Pivot hasn't been created");
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection()) {
            Statement statement = Util.getStatement(connection);
            statement.execute(DROP_USER_TABLE);
            System.out.println("Pivot has been dropped");
        } catch (SQLException e) {
            System.out.println("Pivot hasn't been dropped");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection();
             PreparedStatement prepStmt = connection.prepareStatement(INSERT_USER_INFO)) {

            prepStmt.setString(1, name);
            prepStmt.setString(2, lastName);
            prepStmt.setByte(3, age);
            prepStmt.executeUpdate();
            System.out.println("User was added");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
             PreparedStatement prepStmt = connection.prepareStatement(DELETE_USER_ID)) {

            prepStmt.setLong(1, id);
            prepStmt.executeUpdate();
            System.out.println("User was removed!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             Statement stat = connection.createStatement()) {

            ResultSet resultSet = stat.executeQuery(GET_USER_INFO);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("USER_ID"));
                user.setName(resultSet.getString("USERNAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));
                userList.add(user);
            }
            System.out.println("List of users is ready!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;

    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement stmt = connection.createStatement()) {

            stmt.executeUpdate(CLEAR_TABLE);
            System.out.println("Table was cleaned!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}