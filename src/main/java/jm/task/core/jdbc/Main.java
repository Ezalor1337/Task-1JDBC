package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;

public class Main {
    private final static UserService userService = new UserServiceImpl();
    public static void main(String[] args) throws SQLException {
        userService.createUsersTable();
        userService.saveUser("Bugs", "Bunny", (byte) 78);
        userService.saveUser("Ramil", "Salimov", (byte) 74);
        userService.saveUser("Maksim", "Komissarov", (byte) 59);
        userService.saveUser("Julius", "Smirnov", (byte) 74);
        userService.removeUserById(2);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}

