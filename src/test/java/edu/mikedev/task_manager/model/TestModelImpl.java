package edu.mikedev.task_manager.model;

import edu.mikedev.task_manager.data.Task;
import edu.mikedev.task_manager.data.User;
import edu.mikedev.task_manager.utils.HibernateDBUtilsInMemory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class TestModelImpl {

    ModelImpl model;
    private HibernateDBUtilsInMemory hibernateDBUtils;


    @Before
    public void setUp() throws SQLException {
        hibernateDBUtils = new HibernateDBUtilsInMemory();
        model = new ModelImpl(new HibernateDBLayer(hibernateDBUtils.getSessionFactory()));
        hibernateDBUtils.initDBTables();
    }

    @After
    public void closeSession(){
        model.getDBLayer().closeConnection();
    }

    @Test
    public void testInitialDBConditions(){
        List<User> users = model.getDBLayer().getUsers();
        List<Task> tasks = model.getDBLayer().getTasks();
        Assert.assertEquals(2, users.size());
        Assert.assertEquals(6, tasks.size());
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
    public void testLoginUserDoubleUsername(){
        User duplicateUser = new User("username1", "password1", "email1");
        duplicateUser.setId(1000);
        duplicateUser.setTasks(new HashSet<>());
        hibernateDBUtils.insert(duplicateUser);

        Assert.assertThrows(IllegalArgumentException.class, () -> model.loginUser("username1", "password1"));
    }


    @Test
    public void testRegisterUser(){
        User newUser = model.registerUser("tt4tu84", "b", "c");
        Assert.assertEquals(3, newUser.getId());

        User newUser1 = model.registerUser("t489u89t48t", "re345r435t3", "c@email.com");
        Assert.assertEquals(4, newUser1.getId());
        Assert.assertEquals(0, newUser1.getTasks().size());

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

        List<String> dbTaskTitlesPreUpdate = hibernateDBUtils.getDBTaskTitles();

        model.loginUser("username1", "password1");

        Task taskToBeUpdated = model.getTaskById(1);

        String oldTaskTitle = taskToBeUpdated.getTitle();
        String newTaskTitle = "Updated task 1";

        taskToBeUpdated.setTitle(newTaskTitle);

        model.updateTask(taskToBeUpdated);

        Optional<Task> first = model.getUserTasks().stream().filter(t -> t.getId() == taskToBeUpdated.getId()).findFirst();
        Assert.assertTrue(first.isPresent());
        Task updatedTask = first.get();

        Assert.assertNotEquals(oldTaskTitle, updatedTask.getTitle());
        Assert.assertEquals(taskToBeUpdated.getId(), updatedTask.getId());
        Assert.assertEquals(taskToBeUpdated.getDescription(), updatedTask.getDescription());
        Assert.assertEquals(taskToBeUpdated.getDeadline(), updatedTask.getDeadline());

        List<String> dbTaskTitlesPostUpdate = hibernateDBUtils.getDBTaskTitles();

        Assert.assertTrue(dbTaskTitlesPreUpdate.contains(oldTaskTitle));
        Assert.assertFalse(dbTaskTitlesPreUpdate.contains(newTaskTitle));

        Assert.assertFalse(dbTaskTitlesPostUpdate.contains(oldTaskTitle));
        Assert.assertTrue(dbTaskTitlesPostUpdate.contains(newTaskTitle));
    }

    @Test
    public void testAddNewTask() {
        List<String> dbTaskTitlesPreAdd = hibernateDBUtils.getDBTaskTitles();


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = dateFormat.parse("10/09/2021");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Task newTask = new Task("new task1", "new description 1", date, true);
        Assert.assertThrows(IllegalAccessError.class, () -> model.addNewTask(newTask));

        User user = model.loginUser("username", "password");
        model.addNewTask(newTask);

        List<Task> tasks = model.getUserTasks();
        Assert.assertEquals(4, tasks.size());
        Task newTaskFromHB = tasks.get(tasks.size()-1);
        User userTask = newTaskFromHB.getUser();

        Assert.assertNotNull(userTask);
        Assert.assertEquals(newTask.getTitle(), newTaskFromHB.getTitle());
        Assert.assertEquals(newTask.getDescription(), newTaskFromHB.getDescription());
        Assert.assertEquals(newTask.getDeadline(), newTaskFromHB.getDeadline());
        Assert.assertEquals(newTask.getId(), newTaskFromHB.getId());
        Assert.assertEquals(user.getId(), newTask.getUser().getId());


        User loggedUser = model.getLoggedUser();
        Assert.assertEquals(loggedUser.getId(), userTask.getId());
        Assert.assertEquals(loggedUser.getUsername(), userTask.getUsername());
        Assert.assertEquals(loggedUser.getPassword(), userTask.getPassword());
        Assert.assertEquals(loggedUser.getEmail(), userTask.getEmail());

        List<String> dbTaskTitlesPostAdd = hibernateDBUtils.getDBTaskTitles();

        Assert.assertEquals(6, dbTaskTitlesPreAdd.size());
        Assert.assertFalse(dbTaskTitlesPreAdd.contains(newTask.getTitle()));
        Assert.assertEquals(7, dbTaskTitlesPostAdd.size());
        Assert.assertTrue(dbTaskTitlesPostAdd.contains(newTask.getTitle()));
    }

    @Test
    public void testDeleteTask() {
        Task taskToDelete = hibernateDBUtils.getTaskById(2);
        Task otherUserTask = hibernateDBUtils.getTaskById(5);

        Assert.assertThrows(IllegalAccessError.class, () -> model.deleteTask(taskToDelete));

        model.loginUser("username1", "password1");

        model.deleteTask(taskToDelete);

        List<Task> userTasksAfterDelete = model.getUserTasks();
        Assert.assertEquals(2, userTasksAfterDelete.size());
        Assert.assertFalse(userTasksAfterDelete.stream().anyMatch((x) -> x.getId() == taskToDelete.getId()));
        Assert.assertThrows(IllegalAccessError.class, () -> model.deleteTask(otherUserTask));

        List<String> dbTaskTitles = hibernateDBUtils.getDBTaskTitles();
        Assert.assertEquals(5, dbTaskTitles.size());
        Assert.assertFalse(userTasksAfterDelete.stream().anyMatch((x) -> Objects.equals(x.getTitle(), taskToDelete.getTitle())));
    }

    @Test
    public void testGetTaskById() {
        Assert.assertThrows(IllegalAccessError.class, () -> model.getTaskById(1));

        model.loginUser("username", "password");

        Task task = model.getTaskById(4);
        Assert.assertEquals("title4", task.getTitle());
        Assert.assertEquals("description4", task.getDescription());

        Assert.assertThrows(IllegalAccessError.class, () -> model.getTaskById(1));
        Assert.assertThrows(IllegalAccessError.class, () -> model.getTaskById(443420));
    }


    @Test
    public void testGetTasks() {
        Assert.assertThrows(IllegalAccessError.class, () -> model.getUserTasks());

        List<Task> expectedTasks = hibernateDBUtils.getUsersTask(1).stream().sorted(Comparator.comparingInt(Task::getId)).collect(Collectors.toList());

        model.loginUser("username1", "password1");

        List<Task> actualTasks = model.getUserTasks().stream().sorted(Comparator.comparingInt(Task::getId)).collect(Collectors.toList());

        Assert.assertArrayEquals(expectedTasks.toArray(), actualTasks.toArray());

        List<String> dbTaskTitlesUser1 = hibernateDBUtils.getDBTaskTitlesOfUser(1);
        Assert.assertArrayEquals(expectedTasks.stream().map(Task::getTitle).collect(Collectors.toList()).toArray(), dbTaskTitlesUser1.toArray());
    }

    @Test
    public void testLogout() {
        Assert.assertThrows(IllegalAccessError.class, () -> model.logout());

        User loggedUser = model.loginUser("username1", "password1");
        Assert.assertTrue(model.isUserLogged());
        Assert.assertEquals(loggedUser, model.getLoggedUser());
        model.logout();
        Assert.assertNull(model.getLoggedUser());
        Assert.assertFalse(model.isUserLogged());
        Assert.assertThrows(IllegalAccessError.class, () -> model.logout());
    }
}
