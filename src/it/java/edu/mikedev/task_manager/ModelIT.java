package edu.mikedev.task_manager;

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

public class ModelIT {

    private HibernateDBUtils hibernateDBUtils;
    private Session session;
    private Transaction t;
    private Model model;

    @Before
    public void setUp() throws Exception {
        Path testResourceDirectory = Paths.get("src", "main", "resources");
        File hibernateConfigFile = new File(testResourceDirectory.resolve("hibernate.cfg.xml").toAbsolutePath().toString());

        System.out.println(testResourceDirectory.toAbsolutePath().toString());

        Configuration cfg = new Configuration();
        SessionFactory factory = cfg.configure(hibernateConfigFile).buildSessionFactory();

        session = factory.openSession();
        t = session.beginTransaction();
        this.hibernateDBUtils = new HibernateDBUtils(session);
        hibernateDBUtils.initTestDB();

        model = new HibernateModel(session);
    }

    @Test
    public void testRegisterUser(){

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

    @Test
    public void testAreCredentialCorrect(){
        Assert.assertTrue(model.areCredentialCorrect("tizio", "caio"));
        Assert.assertFalse(model.areCredentialCorrect("nonexistentusername", "nonexistentpassword"));
        Assert.assertFalse(model.areCredentialCorrect("pippo", "fakepassword1"));
        Assert.assertFalse(model.areCredentialCorrect("fakeusername1", "pluto"));
    }

    @Test
    public void testGetUser(){
        User pulledUser = model.getUser("johndoe","randompassword");

        Assert.assertEquals(2, pulledUser.getId());
        Assert.assertEquals("johndoe", pulledUser.getUsername());
        Assert.assertEquals("randompassword", pulledUser.getPassword());
        Assert.assertEquals("johndoe@gmail.com", pulledUser.getEmail());

        Assert.assertThrows(IllegalArgumentException.class, () -> model.getUser("notexistentuser", "notexistentpassword"));
    }

    @Test
    public void testUserExists(){
        Assert.assertTrue(model.userExists("tizio"));
        Assert.assertFalse(model.userExists("fakeuser"));

        Assert.assertTrue(model.userExists("johndoe"));
        Assert.assertFalse(model.userExists("caio"));
    }

}
