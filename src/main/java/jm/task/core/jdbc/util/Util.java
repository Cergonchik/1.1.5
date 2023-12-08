package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД

    private static final String URL = "jdbc:mysql://localhost:3306/userdb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "cergon@.15071988";

    public static Connection getConnect() {
        Connection connection = null;
        try {

            connection = DriverManager.getConnection(URL, USERNAME
                    , PASSWORD);
        } catch (SQLException  e) {
            System.out.println("No connect!");
        }
        return connection;
    }

}
