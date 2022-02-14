package edu.mikedev.task_manager;

import edu.mikedev.task_manager.utils.HibernateDBUtils;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

public class TestHibernateModel {

    Session session;
    HibernateModel model;


    @Before
    public void setUp() throws SQLException {
        HibernateDBUtils hibernateDBUtils = new HibernateDBUtils(HibernateDBUtils.buildHBSessionInMemory());
        session = hibernateDBUtils.getSession();
        model = new HibernateModel(session);
        hibernateDBUtils.initInMemoryTestDB();
        hibernateDBUtils.addFakeUsers(model.getDBLayer());
    }

    @After
    public void closeSession(){
        session.flush();
        session.close();
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
