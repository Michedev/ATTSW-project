package edu.mikedev.task_manager.model;

import edu.mikedev.task_manager.Task;
import edu.mikedev.task_manager.User;
import edu.mikedev.task_manager.utils.HibernateDBUtilsInMemory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TestHibernateDBLayer {

    private HibernateDBLayer hibernateDBLayer;
    private HibernateDBUtilsInMemory dbUtils;

    @Before
    public void setUp(){
        dbUtils = new HibernateDBUtilsInMemory();
        dbUtils.initDBTables();
        hibernateDBLayer = new HibernateDBLayer(dbUtils.getSessionFactory());
    }

    @After
    public void closeSession(){
        hibernateDBLayer.closeConnection();
    }

    @Test
    public void testSession(){
        Assert.assertNotNull(hibernateDBLayer.getSession());
        Assert.assertTrue(hibernateDBLayer.getSession().isConnected());
    }

    @Test
    public void testClosedConnection(){
        hibernateDBLayer.closeConnection();

        Assert.assertThrows(Exception.class, () -> hibernateDBLayer.getUsernames());
    }

    @Test
    public void testGetUserIds(){
        List<Integer> expected = Arrays.asList(1, 2);
        Assert.assertArrayEquals(expected.toArray(), hibernateDBLayer.getUserIds().toArray());

    }

    @Test
    public void testGetUserById() {
        User user = hibernateDBLayer.getUserById(1);
        Assert.assertEquals("username1", user.getUsername());
        Assert.assertThrows(IndexOutOfBoundsException.class, () -> hibernateDBLayer.getUserById(0));
    }

    @Test
    public void testGetTaskById() {
        Task task = hibernateDBLayer.getTaskById(4);
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
    public void testGetTasksId(){
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5, 6);
        Assert.assertArrayEquals(expected.toArray(), hibernateDBLayer.getTasksId().toArray());
    }


    @Test
    public void testUpdateTask(){
        List<Task> tasks = hibernateDBLayer.getTasks();
        Task toBeUpdated = tasks.get(3);
        hibernateDBLayer.getSession().evict(toBeUpdated);
        String oldTitle = toBeUpdated.getTitle();
        String newTitle = "updated title1";
        toBeUpdated.setTitle(newTitle);

        hibernateDBLayer.update(toBeUpdated);
        Task updatedTask = hibernateDBLayer.getTaskById(toBeUpdated.getId());

        Assert.assertNotEquals(oldTitle, updatedTask.getTitle());
        Assert.assertEquals(toBeUpdated.getId(), updatedTask.getId());
        Assert.assertEquals(toBeUpdated.getDescription(), updatedTask.getDescription());
        Assert.assertEquals(toBeUpdated.getDeadline(), updatedTask.getDeadline());
        Assert.assertEquals(toBeUpdated.isDone(), updatedTask.isDone());

        List<String> dbTaskTitles = dbUtils.getDBTaskTitles();
        Assert.assertEquals(6, dbTaskTitles.size());
        Assert.assertFalse(dbTaskTitles.contains(oldTitle));
        Assert.assertTrue(dbTaskTitles.contains(newTitle));
    }


    @Test
    public void testDeleteUser(){
        List<User> usersPreDelete = hibernateDBLayer.getUsers();
        Assert.assertEquals(2, usersPreDelete.size());

        User user = hibernateDBLayer.getUserById(1);

        hibernateDBLayer.delete(user);

        List<User> usersAfterDelete = hibernateDBLayer.getUsers();

        Assert.assertEquals(2, usersPreDelete.size());
        Assert.assertEquals(1, usersAfterDelete.size());

        List<String> dbUsernames = dbUtils.getDBUsernames();
        Assert.assertEquals(1, dbUsernames.size());
        Assert.assertFalse(dbUsernames.contains(user.getUsername()));
    }

    @Test
    public void testGetUsers(){
        List<User> usersDB = hibernateDBLayer.getUsers();
        Assert.assertArrayEquals(dbUtils.users.stream().sorted(Comparator.comparingInt(User::getId)).collect(Collectors.toList()).toArray(),
                                 usersDB.stream().sorted(Comparator.comparingInt(User::getId)).collect(Collectors.toList()).toArray());
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

    @Test
    public void testAddNewTask(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = simpleDateFormat.parse("13/04/3022");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Task newTask = new Task("New task 1", "New Descr 1", date, false);
        newTask.setUser(hibernateDBLayer.getUserById(1));
        hibernateDBLayer.add(newTask);

        List<Task> tasksPostAdd = hibernateDBLayer.getTasks();

        Assert.assertEquals(7, tasksPostAdd.size());

        List<String> dbTaskTitlesPostAdd = dbUtils.getDBTaskTitles();
        Assert.assertEquals(7, tasksPostAdd.size());
        Assert.assertTrue(dbTaskTitlesPostAdd.contains(newTask.getTitle()));
    }

    @Test
    public void testAddNewUser(){
        User user = new User("new username", "newpassword", "newemail@email.com");

        hibernateDBLayer.add(user);

        List<User> usersPostAdd = hibernateDBLayer.getUsers();

        Assert.assertEquals(3, usersPostAdd.size());

        List<String> dbUsernamesPostAdd = dbUtils.getDBUsernames();

        Assert.assertEquals(3, dbUsernamesPostAdd.size());
        Assert.assertTrue(dbUsernamesPostAdd.contains(user.getUsername()));
    }
}