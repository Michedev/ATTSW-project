package edu.mikedev.task_manager;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

import java.sql.Date;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TestCRUDTask {

    Session mockedSession;
    private Model model;
    private Query mockedQuery;
    private Task task1;
    private Task task2;
    private Task task3;
    private List<Task> tasks;
    private User user;

    @Before
    public void setUp(){
        mockedSession = mock(Session.class);
        when(mockedSession.beginTransaction()).thenReturn(mock(Transaction.class));
        mockedQuery = mock(Query.class);

        model = new HibernateModel(mockedSession);


        Set<Task> taskSet = new HashSet<Task>();
        task1 = new Task("title1", "description1", null, false);
        task1.setId(5);
        task2 = new Task("title2", "description2", null, false);
        task2.setId(9);
        task3 = new Task("title3", "description3", null, false);
        task3.setId(12);

        taskSet.add(task1);
        taskSet.add(task2);
        taskSet.add(task3);

        tasks = Arrays.asList(task1, task2, task3);

        user = new User("username1", "password1", "email@email.com");
        user.setId(50);
        user.setTasks(taskSet);
        List<User> queryResult = Arrays.asList(user);

        Query mockedQuery = mock(Query.class);
        when(mockedQuery.getResultList()).thenReturn(queryResult);

        when(mockedSession.createQuery(ArgumentMatchers.matches(String.format(String.format("SELECT a from User a where a.username = '%s' and a.password = '%s'", user.getUsername(), user.getPassword()))), any())).thenReturn(mockedQuery);

        Query mockedQueryTaskIds = mock(Query.class);
        when(mockedQueryTaskIds.getResultList()).thenReturn(getTaskIds());
        when(mockedSession.createQuery(ArgumentMatchers.matches("SELECT id from Task"), any())).thenReturn(mockedQueryTaskIds);

        Query mockedQueryUserTaskIds = mock(Query.class);
        when(mockedQueryUserTaskIds.getResultList()).thenReturn(Arrays.asList(5, 9, 12));
        when(mockedSession.createQuery(ArgumentMatchers.matches(String.format("SELECT id from Task where id_user = %d", user.getId())), any())).thenReturn(mockedQueryUserTaskIds);

        for(Task task: taskSet){
            Query mockedQueryTaskGetById = mock(Query.class);
            when(mockedQueryTaskGetById.getResultList()).thenReturn(Arrays.asList(task));
            when(mockedSession.createQuery(ArgumentMatchers.matches(String.format("SELECT a from Task a where id_user = %d and id = %d", user.getId(), task.getId())), any())).thenReturn(mockedQueryTaskGetById);

        }
    }


    @Test
    public void tesAddNewTask(){

        Task task = new Task();
        task.setId(40);

        Assert.assertThrows(IllegalAccessError.class, () -> model.addNewTask(task));

        model.loginUser("username1", "password1");

        model.addNewTask(task);

        task.setId(3);
        Assert.assertThrows(IllegalArgumentException.class, () -> model.addNewTask(task));

        verify(mockedSession, times(1)).persist(any());
    }

    @Test
    public void testUpdateTask(){

        task2.setTitle("updated task 1");

        Assert.assertThrows(IllegalAccessError.class, () -> model.updateTask(task2));

        model.loginUser("username1", "password1");

        model.updateTask(task2);

        task2.setId(40);
        Assert.assertThrows(IllegalArgumentException.class, () -> model.updateTask(task2));

        verify(mockedSession, times(1)).update(task2);

    }

    private List<Integer> getTaskIds() {
        return IntStream.range(0, 13).boxed().collect(Collectors.toList());
    }

    @Test
    public void testDeleteTask(){

        Task task = new Task("Title", "Description", Date.from(Instant.now()), false);

        Assert.assertThrows(IllegalAccessError.class, () -> model.deleteTask(task));

        model.loginUser("username1", "password1");

        model.deleteTask(task1);

        verify(mockedSession, times(1)).remove(task1);
    }

    @Test
    public void testGetTaskByID() {
        Assert.assertThrows(IllegalAccessError.class, () -> model.getTaskById(5));

        model.loginUser("username1", "password1");

        Assert.assertThrows(IllegalAccessError.class, () -> model.getTaskById(0));
        Assert.assertThrows(IllegalAccessError.class, () -> model.getTaskById(30));
        Assert.assertEquals(task1, model.getTaskById(5));
        Assert.assertEquals(task2, model.getTaskById(9));
        Assert.assertEquals(task3, model.getTaskById(12));
    }


}
