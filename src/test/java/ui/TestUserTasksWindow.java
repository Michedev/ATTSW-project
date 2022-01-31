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
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(GUITestRunner.class)
public class TestUserTasksWindow extends AssertJSwingJUnitTestCase {

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
        dummyuser.setTasks(tasksSet);
        when(model.getUser(anyString(), anyString())).thenReturn(dummyuser);

        GuiActionRunner.execute(() ->{
            window = new LoginWindow(model);
            return window;
        });
        frame = new FrameFixture(robot(), window);
        frame.show();

        frame.button("btnLogin").click();
    }

    @Test
    @GUITest
    public void testInitialState(){
        for (int i = 0; i < 5; i++) {
            frame.panel("task" + i).requireEnabled();
        }
        frame.button("btnNewTask").requireEnabled();
    }

    @Test
    @GUITest
    public void testCorrectOrderTasks(){
        for (int i = 0; i < 5; i++) {
            frame.label("lblTitleTask" + i).requireText(tasksListSorted.get(i).getTitle());
            frame.label("lblDescrTask" + i).requireText(tasksListSorted.get(i).getDescription());
        }
    }

    @Test
    @GUITest
    public void testCorrectColorCards(){
        List<Color> expectedColors = Arrays.asList(Color.RED, Color.ORANGE, Color.RED, Color.GREEN, Color.GREEN);
        for(int i = 0; i < 5; i++){
            JPanel taskPanel = frame.panel("task"+i).target();
            Assert.assertEquals(expectedColors.get(i), taskPanel.getBackground());
        }
    }

    @After
    public void after() {
        frame.cleanUp();
    }

}
