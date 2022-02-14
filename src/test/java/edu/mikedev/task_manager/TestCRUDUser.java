package edu.mikedev.task_manager;

import edu.mikedev.task_manager.utils.HibernateDBUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

import java.sql.SQLException;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TestCRUDUser {

    Session session;
    HibernateModel model;
    Transaction t;


    @Before
    public void setUp() throws SQLException {
        HibernateDBUtils hibernateDBUtils = new HibernateDBUtils(HibernateDBUtils.buildHBSessionInMemory());
        session = hibernateDBUtils.getSession();
        model = new HibernateModel(session);
        hibernateDBUtils.initInMemoryTestDB();
        hibernateDBUtils.addFakeUsers();
        t = model.getTransaction();
    }

    @After
    public void commitTransaction(){
        model.getTransaction().commit();
    }

    @Test
    public void testLoginUser(){
        User actual = model.loginUser("username1", "password1");

        Assert.assertEquals("username1", actual.getUsername());
        Assert.assertEquals("password1", actual.getPassword());
        Assert.assertEquals("email@email.com", actual.getEmail());

        Assert.assertThrows(IllegalArgumentException.class, () -> model.loginUser("aaa", "bbb"));
    }

    @Test
    public void testAddUser(){
        User newUser = new User("9t499t04", "b", "c");
        newUser.setId(43);
        newUser.setTasks(new HashSet<>());
        model.addUser(newUser);

        User newUser2 = new User("fefemkfe", "bfe49e894", "cfeji");
        newUser2.setId(1);

        Assert.assertThrows(IllegalArgumentException.class, () -> model.addUser(newUser2));
    }

    @Test
    public void testRegistrationUser(){
        User newUser = model.registerUser("tt4tu84", "b", "c");
        Assert.assertEquals(0, newUser.getId());

        User newUser1 = model.registerUser("t489u89t48t", "re345r435t3", "c@email.com");
        Assert.assertEquals(2, newUser1.getId());

        Assert.assertThrows(IllegalArgumentException.class, () -> model.registerUser("t489u89t48t", "b", "c"));
    }

    @Test
    public void testUserExists(){
        Assert.assertTrue(model.userExists("username"));
        Assert.assertFalse(model.userExists("fakeuser"));
        Assert.assertTrue(model.userExists("username1"));
    }

}
