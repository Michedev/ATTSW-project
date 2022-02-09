package edu.mikedev.task_manager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TestCRUDUser {

    Session mockedSession;
    Model model;


    @Before
    public void setUp(){
        mockedSession = mock(Session.class);

        model = new HibernateModel(mockedSession);

        Query mockedQueryUserId = mock(Query.class);
        List<Integer> userIds = Arrays.asList(0, 1, 2, 3);
        Query mockedQueryUsernames = mock(Query.class);
        List<String> usernames = Arrays.asList("a", "tizio", "caio");

        when(mockedQueryUsernames.getResultList()).thenReturn(usernames);
        when(mockedQueryUserId.getResultList()).thenReturn(userIds);

        when(mockedSession.createQuery(ArgumentMatchers.matches("SELECT id from User"), any())).thenReturn(mockedQueryUserId);
        when(mockedSession.createQuery(ArgumentMatchers.matches("SELECT username from User"), any())).thenReturn(mockedQueryUsernames);

    }

    @Test
    public void testGetUser(){
        User expected = new User("username", "password", "email@email.it");
        List<User> expctedQueryList = Arrays.asList(expected);
        Query mockedQuery = mock(Query.class);
        when(mockedQuery.getResultList()).thenReturn(expctedQueryList);

        Query emptyQuery = mock(Query.class);
        when(emptyQuery.getResultList()).thenReturn(new ArrayList());

        when(mockedSession.createQuery(ArgumentMatchers.matches(String.format("SELECT a from User a where a.username = '%s' and a.password = '%s'", expected.getUsername(), expected.getPassword())), any())).thenReturn(mockedQuery);
        when(mockedSession.createQuery(ArgumentMatchers.matches("SELECT a from User a where a.username = 'aaa' and a.password = 'bbb'"), any())).thenReturn(emptyQuery);

        User actual = model.loginUser("username", "password");

        Assert.assertEquals(actual, expected);

        Assert.assertThrows(IllegalArgumentException.class, () -> model.loginUser("aaa", "bbb"));
    }

    @Test
    public void testAddUser(){
        User newUser = new User("9t499t04", "b", "c");
        newUser.setId(50);
        model.addUser(newUser);

        newUser.setId(1);

        Assert.assertThrows(IllegalArgumentException.class, () -> model.addUser(newUser));

        verify(mockedSession, times(1)).persist(any(User.class));
    }

    @Test
    public void testRegistrationUser(){
        User newUser = model.registerUser("tt4tu84", "b", "c");
        Assert.assertEquals(4, newUser.getId());

        Assert.assertThrows(IllegalArgumentException.class, () -> model.registerUser("a", "b", "c"));

        verify(mockedSession, times(1)).persist(newUser);

    }

    @Test
    public void testUserExists(){
        User expected = new User("username", "password", "email@email.it");
        List<User> expctedQueryList = Arrays.asList(expected);
        Query mockedQuery = mock(Query.class);
        when(mockedQuery.getResultList()).thenReturn(expctedQueryList);

        Query emptyQuery = mock(Query.class);
        when(emptyQuery.getResultList()).thenReturn(new ArrayList());

        when(mockedSession.createQuery(ArgumentMatchers.matches(String.format("SELECT a from User a where a.username = '%s'", expected.getUsername(), expected.getPassword())), any())).thenReturn(mockedQuery);
        when(mockedSession.createQuery(ArgumentMatchers.matches("SELECT a from User a where a.username = 'fakeuser'"), any())).thenReturn(emptyQuery);

        Assert.assertTrue(model.userExists("username"));
        Assert.assertFalse(model.userExists("fakeuser"));
    }

    @Test
    public void testAreCredentialCorrect(){
        User expected = new User("username", "password", "email@email.it");
        List<User> expectedQueryList = Arrays.asList(expected);
        Query mockedQuery = mock(Query.class);
        when(mockedQuery.getResultList()).thenReturn(expectedQueryList);

        Query emptyQuery = mock(Query.class);
        when(emptyQuery.getResultList()).thenReturn(new ArrayList());

        when(mockedSession.createQuery(ArgumentMatchers.matches(String.format("SELECT a from User a where a.username = '%s' and a.password = '%s'", expected.getUsername(), expected.getPassword())), any())).thenReturn(mockedQuery);
        when(mockedSession.createQuery(ArgumentMatchers.matches("SELECT a from User a where a.username = 'fakeuser' and a.password = 'fakepassword'"), any())).thenReturn(emptyQuery);

        Assert.assertTrue(model.areCredentialCorrect(expected.getUsername(), expected.getPassword()));
        Assert.assertFalse(model.areCredentialCorrect("fakeuser", "fakepassword"));
    }

    @Test
    public void testUnallowedTaskAccessOnceLogged(){
        Set<Task> taskSet = new HashSet<Task>();
        Task task1 = new Task("title1", "description1", null, false);
        task1.setId(5);
        Task task2 = new Task("title2", "description2", null, false);
        task2.setId(9);
        Task task3 = new Task("title3", "description3", null, false);
        task3.setId(12);

        taskSet.add(task1);
        taskSet.add(task2);
        taskSet.add(task3);

        User user = new User("username1", "password1", "email@email.com");
        user.setTasks(taskSet);
        when(model.loginUser(ArgumentMatchers.matches("username1"), ArgumentMatchers.matches("password1"))).thenReturn(user);
        model.loginUser("username1", "password1");

        Assert.assertThrows(IllegalAccessException.class, () -> model.getTaskById(0));
        Assert.assertThrows(IllegalAccessException.class, () -> model.getTaskById(30));
        Assert.assertEquals(task1, model.getTaskById(5));
        Assert.assertEquals(task2, model.getTaskById(9));
        Assert.assertEquals(task2, model.getTaskById(12));


    }
}
