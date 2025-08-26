package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        System.out.println("Таблица пользователей создана.");


        userService.saveUser("Роман", "Иванов", (byte) 25);
        System.out.println("User с именем Иван добавлен в базу данных.");
        userService.saveUser("Максим", "Петров", (byte) 33);
        System.out.println("User с именем Пётр добавлен в базу данных.");
        userService.saveUser("Владимир", "Сидоров", (byte) 40);
        System.out.println("User с именем Сидор добавлен в базу данных.");
        userService.saveUser("Анна", "Кузнецова", (byte) 23);
        System.out.println("User с именем Анна добавлена в базу данных.");

        List<User> users = userService.getAllUsers();
        System.out.println("Список пользователей в базе данных:");
        users.forEach(System.out::println);

        userService.cleanUsersTable();
        System.out.println("Таблица пользователей очищена.");

        userService.dropUsersTable();
        System.out.println("Таблица пользователей удалена.");
    }
}