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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class TestHibernateModel {

    Session session;
    HibernateModel model;
    List<User> users;


    @Before
    public void setUp() throws SQLException {
        HibernateDBUtils hibernateDBUtils = new HibernateDBUtils(HibernateDBUtils.buildHBSessionInMemory());
        session = hibernateDBUtils.getSession();
        model = new HibernateModel(session);
        hibernateDBUtils.initInMemoryTestDB();
        users = hibernateDBUtils.addFakeUsers(model.getDBLayer());
    }

    @After
    public void closeSession(){
        session.close();
    }

    @Test
    public void testLoginUser(){
        User actual = model.loginUser("username1", "password1");

        Assert.assertEquals("username1", actual.getUsername());
        Assert.assertEquals("password1", actual.getPassword());
        Assert.assertEquals("email@email.com", actual.getEmail());

        Assert.assertThrows(IllegalArgumentException.class, () -> model.loginUser("aaa", "bbb"));

        User duplicateUser = new User("username1", "password1", "email1");
        duplicateUser.setId(1000);
        duplicateUser.setTasks(new HashSet<>());
        session.persist(duplicateUser);

        Assert.assertThrows(IllegalArgumentException.class, () -> model.loginUser("username1", "password1"));
    }


    @Test
    public void testRegisterUser(){
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


    @Test
    public void testAreCredentialCorrect() {
        Assert.assertTrue(model.areCredentialCorrect("username", "password"));
        Assert.assertFalse(model.areCredentialCorrect("username", "password1"));
        Assert.assertTrue(model.areCredentialCorrect("username1", "password1"));
        Assert.assertFalse(model.areCredentialCorrect("tizio", "caio"));
    }

    @Test
    public void testUpdateTask() {
        User user = users.get(0);
        Task task1 = user.getTasks().iterator().next();
        String oldTitleValue = task1.getTitle();
        Assert.assertThrows(IllegalAccessError.class, () -> model.updateTask(task1));

        model.loginUser(user.getUsername(), user.getPassword());

        task1.setTitle("Updated task 1");
        model.updateTask(task1);

        Task updatedTask = model.getDBLayer().getTaskByIdWithUserId(task1.getId(), user.getId());

        Assert.assertNotEquals(oldTitleValue, updatedTask.getTitle());
        Assert.assertEquals(task1.getId(), updatedTask.getId());
        Assert.assertEquals(task1.getDescription(), updatedTask.getDescription());
        Assert.assertEquals(task1.getDeadline(), updatedTask.getDeadline());
    }

    @Test
    public void testAddNewTask() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = dateFormat.parse("10/09/2021");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Task newTask = new Task("new task1", "new description 1", date, true);
        newTask.setId(3);
        User user = users.get(1);
        newTask.setUser(user);
        Assert.assertThrows(IllegalAccessError.class, () -> model.addNewTask(newTask));

        model.loginUser(user.getUsername(), user.getPassword());
        model.addNewTask(newTask);

        List<Task> tasks = model.getUserTasks();
        Task actual = tasks.get(tasks.size()-1);

        Assert.assertEquals(newTask.getTitle(), actual.getTitle());
        Assert.assertEquals(newTask.getDescription(), actual.getDescription());
        Assert.assertEquals(newTask.getDeadline(), actual.getDeadline());
        Assert.assertEquals(newTask.getId(), actual.getId());

    }

    @Test
    public void testDeleteTask() {
        User user = users.get(0);
        Task taskToDelete = user.getTasks().iterator().next();

        User wrongUser = users.get(1);
        Task wrongTaskToDelete = wrongUser.getTasks().iterator().next();

        Assert.assertThrows(IllegalAccessError.class, () -> model.deleteTask(taskToDelete));

        model.loginUser(user.getUsername(), user.getPassword());

        model.deleteTask(taskToDelete);

        List<Task> userTasksAfterDelete = model.getUserTasks();
        Assert.assertFalse(userTasksAfterDelete.stream().anyMatch((x) -> x.getId() == taskToDelete.getId()));
        Assert.assertThrows(IllegalAccessError.class, () -> model.deleteTask(wrongTaskToDelete));
    }

    @Test
    public void testGetTaskById() {
        Assert.assertThrows(IllegalAccessError.class, () -> model.getTaskById(0));
        User user = users.get(1);

        model.loginUser(user.getUsername(), user.getPassword());

        Task task = model.getTaskById(0);
        Assert.assertEquals("title4", task.getTitle());
        Assert.assertEquals("description4", task.getDescription());
    }


    @Test
    public void testGetTasks() {
        Assert.assertThrows(IllegalAccessError.class, () -> model.getUserTasks());

        User user = users.get(0);
        List<Task> expectedTasks = user.getTasks().stream().sorted(Comparator.comparingInt(Task::getId)).collect(Collectors.toList());

        model.loginUser(user.getUsername(), user.getPassword());

        List<Task> actualTasks = model.getUserTasks().stream().sorted(Comparator.comparingInt(Task::getId)).collect(Collectors.toList());;

        Assert.assertArrayEquals(expectedTasks.toArray(), actualTasks.toArray());
    }

    @Test
    public void testLogout() {
        Assert.assertThrows(IllegalAccessError.class, () -> model.logout());

        User user = users.get(0);
        model.loginUser(user.getUsername(), user.getPassword());
        Assert.assertTrue(model.isUserLogged());
        model.logout();
        Assert.assertFalse(model.isUserLogged());
        Assert.assertThrows(IllegalAccessError.class, () -> model.logout());
    }
}
