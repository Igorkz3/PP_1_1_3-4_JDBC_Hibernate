package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
  String sql = "CREATE TABLE IF NOT EXISTS user (" +  // <-- Добавлена открывающая скобка
            "id INT PRIMARY KEY AUTO_INCREMENT," +
            "name VARCHAR(20)," +
            "lastName VARCHAR(20)," +
            "age INT" +
            ")";
    try (Connection connection = Util.getConnection();
         Statement statement = connection.createStatement()) {
        statement.executeUpdate(sql);
    } catch (SQLException e) {
        throw new RuntimeException("Создать таблицу не удалось", e);
    }
}


    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS user";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Удаление таблицы пользователец не удалось" + e);
        }
    }


    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO user (name, lastName, age) VALUES (?, ?, ?)";
        try (Connection connection = Util.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.setString(1, name);
            prepareStatement.setString(2, lastName);
            prepareStatement.setByte(3, age);
            prepareStatement.executeUpdate();
            System.out.printf("Пользователь добавлен: name = %s, lastName = %s, age = %d%n", name, lastName, age);
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось сохранить пользователя: " + e.getMessage(), e);
        }
    }


    public void removeUserById(long id) {
        String sql = "DELETE FROM user WHERE id = ?";
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось удалить пользователя с id = " + id + ": " + e.getMessage(), e);
        }
    }


    public List<User> getAllUsers() {
        String sql = "SELECT id, name, lastName, age FROM user";
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)
        ) {

            while (resultSet.next()) {
                User user = new User(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getString("lastName"), resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return users;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM user";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Очистка таблицы не удалась: " + e.getMessage(), e);
        }
    }
}