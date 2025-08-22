package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
   private static final String URL = "jdbc:mysql://localhost:3306/project_schema ";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Соединение с базой данных установлено!");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Ошибка подключения к базе данных: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }
}


