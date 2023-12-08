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
        String creatTable = "CREATE TABLE `userdb`.`users` (\n" +
                "  `id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NOT NULL,\n" +
                "  `lastname` VARCHAR(45) NOT NULL,\n" +
                "  `age` TINYINT NOT NULL,\n" +
                "  PRIMARY KEY (`id`))\n" +
                "ENGINE = InnoDB\n" +
                "DEFAULT CHARACTER SET = utf8;";
        try {
            Statement statement = Util.getConnect().createStatement();
            statement.execute(creatTable);
            statement.close();
        } catch (SQLException e) {
            System.out.println("Такая таблица уже существует!");
        }

    }

    public void dropUsersTable() {
        String dropTable = "drop table if exists users";
        try {
            Statement statement = Util.getConnect().createStatement();
            statement.execute(dropTable);
            statement.close();
        } catch (SQLException e) {
            System.out.println("Не получилось удалить таблицу!");
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        String saveUser = "insert into users ( name, lastname, age) values (?,?,?)";
        try(PreparedStatement preparedStatement = Util.getConnect().prepareStatement(saveUser)) {
            preparedStatement.setString(1, name );
            preparedStatement.setString(2, lastName );
            preparedStatement.setByte(3, age );
            preparedStatement.executeUpdate();
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String removeUser = "DELETE from tablename WHERE id = ?;";
        try(PreparedStatement preparedStatement = Util.getConnect().prepareStatement(removeUser)) {
            preparedStatement.setLong(1, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try {


            String allUsers = "SELECT id, name, lastname, age FROM users";
            Statement statement = Util.getConnect().createStatement();
            ResultSet resultSet = statement.executeQuery(allUsers);

            while (resultSet.next()) {
                User user = new User();
                user.setId( resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                list.add(user);
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Не получилось найти всех юзеров" + e);
        }

        return list;
    }

    public void cleanUsersTable() {
        String cleanTable = "delete from users";
        try {
            Statement statement = Util.getConnect().createStatement();
            statement.execute(cleanTable);
            statement.close();
        } catch (SQLException e) {
            System.out.println("Не получилось очистить таблицу!");
        }
    }
}
