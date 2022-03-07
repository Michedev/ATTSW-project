package edu.mikedev.task_manager;

import edu.mikedev.task_manager.model.HibernateModel;
import edu.mikedev.task_manager.model.Model;
import edu.mikedev.task_manager.utils.HibernateDBUtils;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ModelIT {

    private HibernateDBUtils hibernateDBUtils;
    private Model model;
    private Session session;

    @Before
    public void setUp() {
        this.hibernateDBUtils = new HibernateDBUtils(HibernateDBUtils.buildHBSession());
        try {
            hibernateDBUtils.initRealTestDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        session = hibernateDBUtils.getSession();
        model = new HibernateModel(session);
    }

    @After
    public void closeSession(){
        session.close();
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
        User pulledUser = model.loginUser("johndoe","randompassword");

        Assert.assertEquals(2, pulledUser.getId());
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
        List<Task> tasks = hibernateDBUtils.pullTasks();
        Assert.assertEquals(6, tasks.size());
        Assert.assertEquals("Eat food", tasks.get(0).getTitle());
        Assert.assertEquals("Sample task title 1", tasks.get(2).getTitle());
        Assert.assertEquals("Sample task long description 3", tasks.get(4).getDescription());
    }


    @Test
    public void testAddNewTask(){
        Task toBeAdded = new Task("newtask", "newdescr", new GregorianCalendar(2019, Calendar.FEBRUARY, 11).getTime(), true);
        toBeAdded.setId(0);

        Assert.assertThrows(IllegalAccessError.class, () -> model.addNewTask(toBeAdded));
        User user = model.loginUser("tizio", "caio");
        model.addNewTask(toBeAdded);

        Task toBeAdded2 = new Task("newtask", "newdescr", new GregorianCalendar(2019, Calendar.FEBRUARY, 11).getTime(), true);
        toBeAdded2.setId(6);
        model.addNewTask(toBeAdded2);

        List<Task> tasks = hibernateDBUtils.pullTasks();
        Assert.assertEquals(8, tasks.size());

        Task newTask = tasks.get(6);
        Assert.assertEquals(6, newTask.getId());
        Assert.assertEquals("newtask", newTask.getTitle());
        Assert.assertEquals("newdescr", newTask.getDescription());
        Assert.assertTrue(newTask.isDone());
        Assert.assertEquals(newTask.getUser(), user);

        newTask = tasks.get(7);
        Assert.assertEquals(7, newTask.getId());
        Assert.assertEquals("newtask", newTask.getTitle());
        Assert.assertEquals("newdescr", newTask.getDescription());
        Assert.assertTrue(newTask.isDone());
        Assert.assertEquals(newTask.getUser(), user);

    }

    @Test
    public void testUpdateTask(){
        model.loginUser("tizio", "caio");

        List<Task> tasks = hibernateDBUtils.pullTasks();
        Task toBeUpdated = tasks.get(1);
        String oldTitle = toBeUpdated.getTitle();
        toBeUpdated.setTitle("Updated title");

        model.updateTask(toBeUpdated);

        List<Task> tasksAfterUpdate = hibernateDBUtils.pullTasks();
        Task updatedTask = tasksAfterUpdate.get(tasksAfterUpdate.size()-1);

        Assert.assertEquals(1, updatedTask.getId());
        Assert.assertNotEquals(oldTitle, updatedTask.getTitle());
        Assert.assertEquals("Updated title", updatedTask.getTitle());
        Assert.assertEquals(toBeUpdated.getDescription(), updatedTask.getDescription());
        Assert.assertEquals(toBeUpdated.getDeadline(), updatedTask.getDeadline());
    }

    @Test
    public void testDeleteTask(){
        User pulledUser = model.loginUser("pippo","pluto");

        List<Task> tasks = hibernateDBUtils.pullTasks();
        Task toBeDeleted = tasks.get(2);

        model.deleteTask(toBeDeleted);

        List<Task> tasksAfterDelete = hibernateDBUtils.pullTasks();

        Assert.assertEquals(5, tasksAfterDelete.size());

        Task deletedTaskIndex = tasks.get(3);

        Assert.assertEquals(3, deletedTaskIndex.getId());
        Assert.assertEquals("Sample task title 2", deletedTaskIndex.getTitle());

        Task anotherUserTask = tasks.get(0);

        Assert.assertThrows(IllegalAccessError.class, () -> model.deleteTask(anotherUserTask));
    }

    @Test
    public void testGetTaskById(){
        Assert.assertThrows(IllegalAccessError.class, () -> model.getTaskById(0));
        User user = model.loginUser("tizio", "caio");

        Assert.assertThrows(IllegalAccessError.class, () -> model.getTaskById(5));
        Task task = model.getTaskById(0);

        Assert.assertEquals("Eat food", task.getTitle());
        Assert.assertEquals("Eat food for 15 days", task.getDescription());
    }
}
