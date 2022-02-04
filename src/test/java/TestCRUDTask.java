import edu.mikedev.task_manager.HibernateModel;
import edu.mikedev.task_manager.Model;
import edu.mikedev.task_manager.Task;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

import java.sql.Date;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TestCRUDTask {

    Session mockedSession;
    private Model model;
    private Query mockedQuery;

    @Before
    public void setUp(){
        mockedSession = mock(Session.class);
        mockedQuery = mock(Query.class);

        model = new HibernateModel(mockedSession);

    }


    @Test
    public void tesAddNewTask(){
        Query mockedQuery = mock(Query.class);
        List<Integer> taskIds = Arrays.asList(0, 1, 2, 3);

        when(mockedQuery.getResultList()).thenReturn(taskIds);
        when(mockedSession.createQuery(ArgumentMatchers.matches("SELECT id from Task"), any())).thenReturn(mockedQuery);


        Task task = new Task();
        task.setId(4);

        model.addNewTask(task);

        task.setId(3);
        Assert.assertThrows(IllegalArgumentException.class, () -> model.addNewTask(task));

        verify(mockedSession, times(1)).persist(any());
    }

    @Test
    public void testUpdateTask(){
        Query mockedQuery = mock(Query.class);
        List<Integer> taskIds = Arrays.asList(0, 1, 2, 3);

        when(mockedQuery.getResultList()).thenReturn(taskIds);
        when(mockedSession.createQuery(ArgumentMatchers.matches("SELECT id from Task"), any())).thenReturn(mockedQuery);

        Task task = new Task("Title", "Description", Date.from(Instant.now()), false);
        task.setId(0);

        model.updateTask(task);

        task.setId(4);
        Assert.assertThrows(IllegalArgumentException.class, () -> model.updateTask(task));

        verify(mockedSession, times(1)).update(task);

    }

    @Test
    public void testDeleteTask(){
        Task task = new Task("Title", "Description", Date.from(Instant.now()), false);
        Query mockedQuery = mock(Query.class);
        List<Integer> taskIds = Arrays.asList(0, 1, 2, 3);

        when(mockedQuery.getResultList()).thenReturn(taskIds);
        when(mockedSession.createQuery(ArgumentMatchers.matches("SELECT id from Task"), any())).thenReturn(mockedQuery);

        model.deleteTask(task);

        verify(mockedSession, times(1)).delete(task);
    }



}
