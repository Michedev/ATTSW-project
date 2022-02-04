import edu.mikedev.task_manager.HibernateModel;
import edu.mikedev.task_manager.Model;
import edu.mikedev.task_manager.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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

        when(mockedSession.createQuery(ArgumentMatchers.matches("SELECT a FROM User a where a.username = " + expected.getUsername() + " and a.password = " + expected.getPassword()), any())).thenReturn(mockedQuery);
        when(mockedSession.createQuery(ArgumentMatchers.matches("SELECT a FROM User a where a.username = aaa and a.password = bbb"), any())).thenReturn(emptyQuery);

        User actual = model.getUser("username", "password");

        Assert.assertEquals(actual, expected);

        Assert.assertThrows(IllegalArgumentException.class, () -> model.getUser("aaa", "bbb"));
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
}
