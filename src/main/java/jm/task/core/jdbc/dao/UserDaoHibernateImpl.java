package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.connectHibernate();
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = sessionFactory.openSession();
           try {
              session.beginTransaction();
              session.createSQLQuery("CREATE TABLE `userdb`.`users` (\n" +
                      "  `id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
                      "  `name` VARCHAR(45) NOT NULL,\n" +
                      "  `lastName` VARCHAR(45) NOT NULL,\n" +
                      "  `age` TINYINT NOT NULL,\n" +
                      "  PRIMARY KEY (`id`))\n" +
                      "ENGINE = InnoDB\n" +
                      "DEFAULT CHARACTER SET = utf8;").executeUpdate();
              session.getTransaction().commit();
           } catch (Exception e) {
               System.out.println("No create table");
               session.getTransaction().rollback();
           }
    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.createSQLQuery("drop table if exists users").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No drop table");
            session.getTransaction().rollback();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No save user");
            session.getTransaction().rollback();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No delete user");
            session.getTransaction().rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList;
        try (Session session = sessionFactory.openSession()) {
            userList = session.createQuery("from User", User.class).getResultList();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.getCurrentSession();
        try  {
            session.beginTransaction();
            session.createSQLQuery("delete from users").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No delete");
            session.getTransaction().rollback();
        }
    }
}
