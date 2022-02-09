package edu.mikedev.task_manager.ui;

import edu.mikedev.task_manager.Model;
import edu.mikedev.task_manager.Task;
import edu.mikedev.task_manager.User;
import org.assertj.swing.util.Triple;

import java.util.*;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestUtils {


    public static Triple<Model, User, List<Task>> anyLoginUserTasksScenario(){
        Model mockedModel = mock(Model.class);
        when(mockedModel.areCredentialCorrect(anyString(), anyString())).thenReturn(true);
        User dummyuser = new User(
                "username1",
                "password1",
                "email1@email.com"
        );
        Set<Task> tasksSet = new HashSet<>();
        List<Task> tasksListSorted = new ArrayList<Task>();
        List<Date> taskDates = Arrays.asList(
                new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(),
                new GregorianCalendar(2025, Calendar.FEBRUARY, 11).getTime(),
                new GregorianCalendar(2019, Calendar.FEBRUARY, 11).getTime(),
                new GregorianCalendar(2026, Calendar.FEBRUARY, 11).getTime(),
                new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime()
        );
        for (int i = 0; i < 5; i++) {
            Task t = new Task("Task " + (i + 1), "Description task " + (i + 1), taskDates.get(i), i >= 3);
            t.setId(i);
            tasksSet.add(t);
            tasksListSorted.add(t);
        }
        StringBuilder longDescription = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            longDescription.append("Super Long description ");
        }
        tasksListSorted.get(4).setDescription(longDescription.toString());
        dummyuser.setTasks(tasksSet);
        when(mockedModel.loginUser(anyString(), anyString())).thenReturn(dummyuser);

        return Triple.of(mockedModel, dummyuser, tasksListSorted);
    }
}
