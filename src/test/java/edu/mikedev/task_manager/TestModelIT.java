package edu.mikedev.task_manager;

import edu.mikedev.task_manager.HibernateDBUtils;
import edu.mikedev.task_manager.HibernateModel;
import edu.mikedev.task_manager.Model;
import edu.mikedev.task_manager.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class TestModelIT {

    private HibernateDBUtils hibernateDBUtils;
    private Session session;
    private Transaction t;
    private Model model;

    @Before
    public void setUp() throws Exception {
        Path testResourceDirectory = Paths.get("src", "main", "resources");
        File hibernateConfigFile = new File(testResourceDirectory.resolve("hibernate.cfg.xml").toAbsolutePath().toString());


        Configuration cfg = new Configuration();
        SessionFactory factory = cfg.configure(hibernateConfigFile).buildSessionFactory();

        session = factory.openSession();
        t = session.beginTransaction();
        this.hibernateDBUtils = new HibernateDBUtils(session);
        hibernateDBUtils.initTestDB();

        model = new HibernateModel(session);
    }

    @Test
    public void failingTest(){
        Assert.assertEquals(1, 2);
    }

    @Test
    public void testAddNewUser(){

        List<User> usersPreRegister = hibernateDBUtils.pullUsers();


        model.registerUser("newusername", "password", "email@email.com");

        List<User> usersPostRegister = hibernateDBUtils.pullUsers();
        Assert.assertEquals(4, usersPreRegister.size());
        Assert.assertEquals(5, usersPostRegister.size());
        User newUser = usersPostRegister.get(4);

        Assert.assertEquals(4, newUser.getId());
        Assert.assertEquals("newusername", newUser.getUsername());
        Assert.assertEquals("password", newUser.getPassword());
        Assert.assertEquals("email@email.com", newUser.getEmail());

    }
}
