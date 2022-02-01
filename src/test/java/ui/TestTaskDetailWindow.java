package ui;

import edu.mikedev.task_manager.Model;
import edu.mikedev.task_manager.Task;
import edu.mikedev.task_manager.User;
import edu.mikedev.task_manager.ui.LoginWindow;
import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;

import java.util.*;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(GUITestRunner.class)
public class TestTaskDetailWindow extends AssertJSwingJUnitTestCase {

    LoginWindow window;
    FrameFixture frame;
    Set<Task> tasksSet;
    List<Task> tasksListSorted;


    @Override
    protected void onSetUp(){
        Model model = mock(Model.class);
        when(model.areCredentialCorrect(anyString(), anyString())).thenReturn(true);
        User dummyuser = new User(
                "username1",
                "password1",
                "email1@email.com"
        );
        tasksSet = new HashSet<>();
        tasksListSorted = new ArrayList<Task>();
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
        when(model.getUser(anyString(), anyString())).thenReturn(dummyuser);

        GuiActionRunner.execute(() ->{
            window = new LoginWindow(model);
            return window;
        });
        frame = new FrameFixture(robot(), window);
        frame.show();

        frame.button("btnLogin").click();
        frame.panel("task4").click();
    }

    @Test
    @GUITest
    public void testInitialState(){
        frame.label("lblTaskTitle").requireText("Task 5");
        String textDescription = frame.label("lblTaskDescription").text();
        Assert.assertTrue(textDescription.startsWith("Super Long description"));
        Assert.assertTrue(textDescription.length() > 53);
    }

}
