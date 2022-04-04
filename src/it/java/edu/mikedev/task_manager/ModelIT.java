package edu.mikedev.task_manager;

import edu.mikedev.task_manager.data.Task;
import edu.mikedev.task_manager.data.User;
import edu.mikedev.task_manager.model.DBLayer;
import edu.mikedev.task_manager.model.HibernateDBLayer;
import edu.mikedev.task_manager.model.ModelImpl;
import edu.mikedev.task_manager.utils.HibernateDBUtils;
import edu.mikedev.task_manager.utils.HibernateDBUtilsAbs;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ModelIT {

    private HibernateDBUtilsAbs hibernateDBUtils;
    private ModelImpl model;
    private DBLayer dbLayer;

    @Before
    public void setUp() {
        this.hibernateDBUtils = new HibernateDBUtils();
        try {
            hibernateDBUtils.initDBTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        model = new ModelImpl(new HibernateDBLayer(hibernateDBUtils.getSessionFactory()));
        dbLayer = model.getDBLayer();
    }

    @After
    public void closeSession(){
        model.getDBLayer().closeConnection();
    }

    @Test
    public void testRegisterUser(){

        List<User> usersPreRegister = dbLayer.getUsers();

        String username = "newusername";
        String password = "password";
        String email = "email@email.com";

        model.registerUser(username, password, email);

        List<User> usersPostRegister = dbLayer.getUsers();
        Assert.assertEquals(4, usersPreRegister.size());
        Assert.assertEquals(5, usersPostRegister.size());
        User newUser = usersPostRegister.get(4);

        Assert.assertEquals(5, newUser.getId());
        Assert.assertEquals(username, newUser.getUsername());
        Assert.assertEquals(password, newUser.getPassword());
        Assert.assertEquals(email, newUser.getEmail());

        List<String> dbUsernames = hibernateDBUtils.getDBUsernames();
        Assert.assertEquals(5, dbUsernames.size());
        Assert.assertEquals(username, dbUsernames.get(dbUsernames.size()-1));
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
        User pulledUser = model.loginUser("johndoe","randompassword");

        Assert.assertEquals(3, pulledUser.getId());
        Assert.assertEquals("johndoe", pulledUser.getUsername());
        Assert.assertEquals("randompassword", pulledUser.getPassword());
        Assert.assertEquals("johndoe@gmail.com", pulledUser.getEmail());

        Assert.assertThrows(IllegalArgumentException.class, () -> model.loginUser("notexistentuser", "notexistentpassword"));
    }

    @Test
    public void testUserExists(){
        Assert.assertTrue(model.userExists("tizio"));
        Assert.assertFalse(model.userExists("fakeuser"));

        Assert.assertTrue(model.userExists("johndoe"));
        Assert.assertFalse(model.userExists("caio"));
    }

    @Test
    public void testGetTasks(){
        List<Task> tasks = dbLayer.getTasks();
        Assert.assertEquals(6, tasks.size());
        Assert.assertEquals("Eat food", tasks.get(0).getTitle());
        Assert.assertEquals("Sample task title 1", tasks.get(2).getTitle());
        Assert.assertEquals("Sample task long description 3", tasks.get(4).getDescription());
    }


    @Test
    public void testAddNewTask(){
        Task toBeAdded = new Task("newtask", "newdescr", new GregorianCalendar(2019, Calendar.FEBRUARY, 11).getTime(), true);

        Assert.assertThrows(IllegalAccessError.class, () -> model.addNewTask(toBeAdded));
        User user = model.loginUser("tizio", "caio");
        model.addNewTask(toBeAdded);

        Task toBeAdded2 = new Task("newtask2", "newdescr2", new GregorianCalendar(2020, Calendar.MARCH, 28).getTime(), true);
        model.addNewTask(toBeAdded2);

        List<Task> tasks = dbLayer.getTasks();
        Assert.assertEquals(8, tasks.size());

        Task newTask = tasks.get(6);
        Assert.assertEquals(7, newTask.getId());
        Assert.assertEquals("newtask", newTask.getTitle());
        Assert.assertEquals("newdescr", newTask.getDescription());
        Assert.assertTrue(newTask.isDone());
        Assert.assertEquals(newTask.getUser().getId(), user.getId());

        newTask = tasks.get(7);
        Assert.assertEquals(8, newTask.getId());
        Assert.assertEquals("newtask2", newTask.getTitle());
        Assert.assertEquals("newdescr2", newTask.getDescription());
        Assert.assertTrue(newTask.isDone());
        Assert.assertEquals(newTask.getUser().getId(), user.getId());

        List<String> dbTaskTitles = hibernateDBUtils.getDBTaskTitles();
        Assert.assertEquals(8, dbTaskTitles.size());
        Assert.assertEquals("newtask", dbTaskTitles.get(6));
        Assert.assertEquals("newtask2", dbTaskTitles.get(7));
    }

    @Test
    public void testUpdateTask(){
        model.loginUser("tizio", "caio");

        List<Task> tasks = dbLayer.getTasks();
        Task toBeUpdated = tasks.get(1);
        String oldTitle = toBeUpdated.getTitle();
        String newTitle = "Updated title";
        toBeUpdated.setTitle(newTitle);

        model.updateTask(toBeUpdated);

        List<Task> tasksAfterUpdate = dbLayer.getTasks();
        Task updatedTask = tasksAfterUpdate.stream().filter(t -> t.getId() == 2).findFirst().get();

        Assert.assertEquals(2, updatedTask.getId());
        Assert.assertNotEquals(oldTitle, updatedTask.getTitle());
        Assert.assertEquals(newTitle, updatedTask.getTitle());
        Assert.assertEquals(toBeUpdated.getDescription(), updatedTask.getDescription());
        Assert.assertEquals(toBeUpdated.getDeadline(), updatedTask.getDeadline());

        List<String> dbTaskTitles = hibernateDBUtils.getDBTaskTitles();
        Assert.assertFalse(dbTaskTitles.contains(oldTitle));
        Assert.assertTrue(dbTaskTitles.contains(newTitle));
    }

    @Test
    public void testDeleteTask(){
        User pulledUser = model.loginUser("pippo","pluto");

        List<Task> tasks = dbLayer.getTasks();
        Task toBeDeleted = tasks.get(2);

        List<String> dbTaskTitlesPreDelete = hibernateDBUtils.getDBTaskTitles();

        model.deleteTask(toBeDeleted);

        List<Task> tasksAfterDelete = dbLayer.getTasks();

        Assert.assertEquals(5, tasksAfterDelete.size());

        Task deletedTaskIndex = tasks.get(3);

        Assert.assertEquals(4, deletedTaskIndex.getId());
        Assert.assertEquals("Sample task title 2", deletedTaskIndex.getTitle());

        Task anotherUserTask = tasks.get(0);

        Assert.assertThrows(IllegalAccessError.class, () -> model.deleteTask(anotherUserTask));

        List<String> dbTaskTitlesPostDelete = hibernateDBUtils.getDBTaskTitles();

        Assert.assertTrue(dbTaskTitlesPreDelete.contains(toBeDeleted.getTitle()));
        Assert.assertEquals(6, dbTaskTitlesPreDelete.size());
        Assert.assertEquals(5, dbTaskTitlesPostDelete.size());
        Assert.assertFalse(dbTaskTitlesPostDelete.contains(toBeDeleted.getTitle()));
    }

    @Test
    public void testGetTaskById(){
        Assert.assertThrows(IllegalAccessError.class, () -> model.getTaskById(0));
        User user = model.loginUser("tizio", "caio");

        Assert.assertThrows(IllegalAccessError.class, () -> model.getTaskById(5));
        Task task = model.getTaskById(1);

        Assert.assertEquals("Eat food", task.getTitle());
        Assert.assertEquals("Eat food for 15 days", task.getDescription());
    }
}
