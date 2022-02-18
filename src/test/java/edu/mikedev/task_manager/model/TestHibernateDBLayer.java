package edu.mikedev.task_manager.model;

import edu.mikedev.task_manager.Task;
import edu.mikedev.task_manager.User;
import edu.mikedev.task_manager.utils.HibernateDBUtils;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TestHibernateDBLayer {

    private HibernateDBLayer hibernateDBLayer;
    private Session session;
    private List<User> users;

    @Before
    public void setUp(){
        HibernateDBUtils dbUtils = new HibernateDBUtils(HibernateDBUtils.buildHBSessionInMemory());
        try {
            dbUtils.initInMemoryTestDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        session = dbUtils.getSession();
        hibernateDBLayer = new HibernateDBLayer(session);
        users = dbUtils.addFakeUsers(hibernateDBLayer);
    }

    @After
    public void closeSession(){
        session.close();
    }

    @Test
    public void testGetUserById() {
        User user = hibernateDBLayer.getUserById(50);
        Assert.assertEquals("username1", user.getUsername());
        Assert.assertThrows(IndexOutOfBoundsException.class, () -> hibernateDBLayer.getUserById(0));
    }

    @Test
    public void testGetTaskById() {
        Task task = hibernateDBLayer.getTaskById(0);
        Assert.assertEquals("title4", task.getTitle());
        Assert.assertThrows(IndexOutOfBoundsException.class, () -> hibernateDBLayer.getTaskById(100));

    }

    @Test
    public void testGetTasks(){
        List<Task> tasks = hibernateDBLayer.getTasks();
        Assert.assertEquals(6, tasks.size());
        for(int i: IntStream.range(1, 7).toArray()){
            Assert.assertTrue(tasks.stream().anyMatch((t) -> t.getTitle().equals("title" + i)));
            Assert.assertTrue(tasks.stream().anyMatch((t) -> t.getDescription().equals("description" + i)));
        }
    }

    @Test
    public void testDeleteUser(){
        List<User> usersPreDelete = hibernateDBLayer.getUsers();
        Assert.assertEquals(2, usersPreDelete.size());

        hibernateDBLayer.delete(users.get(0));

        List<User> usersAfterDelete = hibernateDBLayer.getUsers();

        Assert.assertEquals(2, usersPreDelete.size());
        Assert.assertEquals(1, usersAfterDelete.size());
    }

    @Test
    public void testGetUsers(){
        List<User> usersDB = hibernateDBLayer.getUsers();
        for(User user: usersDB){
            Assert.assertTrue(users.contains(user));
        }
    }

    @Test
    public void testGetUsernames(){
        List<String> expected = Stream.of("username", "username1").sorted().collect(Collectors.toList());
        List<String> actual = hibernateDBLayer.getUsernames().stream().sorted().collect(Collectors.toList());
        Assert.assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            Assert.assertEquals(expected.get(i), actual.get(i));
        }

    }
}