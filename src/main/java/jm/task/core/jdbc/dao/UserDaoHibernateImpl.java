package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private static final String CREATE_USER_TABLE = """
            CREATE TABLE IF NOT EXISTS users (
            USER_ID bigint GENERATED ALWAYS AS IDENTITY,
            PRIMARY KEY (USER_ID),
            USERNAME VARCHAR(100) NOT NULL,
            LASTNAME VARCHAR(100) NOT NULL,
            AGE int4 NOT NULL)
            """;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery(CREATE_USER_TABLE).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Pivot is created");
        } catch (Exception e) {
            System.out.println("Pivot is not created");
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Pivot has been deleted");
        } catch (Exception e) {
            System.out.println("Pivot has not been created");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            System.out.println("User has been saved");
        } catch (Exception e) {
            System.out.println("User has not been saved");
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("delete from users where USER_ID = :USER_ID")
                            .setParameter("USER_ID", id).executeUpdate();
            session.getTransaction().commit();
        }
    }
    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            userList = session.createQuery("from User").getResultList();
            session.getTransaction().commit();
            System.out.println(userList.toString());
        } catch (Exception e) {
            System.out.println("User has not been saved");
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
