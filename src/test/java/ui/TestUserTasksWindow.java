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
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(GUITestRunner.class)
public class TestUserTasksWindow extends AssertJSwingJUnitTestCase {

    LoginWindow window;
    FrameFixture frame;
    Set<Task> tasksSet;

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
        for (int i = 0; i < 5; i++) {
            Task t = new Task("Task " + (i + 1), "Description task " + (i + 1), new Date(8328432848L), i == 3);
            t.setId(i);
            tasksSet.add(t);
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
        frame.button("newTask").requireEnabled();
    }

    @Test
    @GUITest
    public void testCorrectOrderTasks(){
        List<Task> sortedTasks = tasksSet.stream().sorted((a,b) -> Comparator.comparingInt(Task::getId).compare(a,b)).collect(Collectors.toList());
        for (int i = 0; i < 5; i++) {
            frame.label("lblTitleTask" + i).requireText(sortedTasks.get(i).getTitle());
            frame.label("lblDescrTask" + i).requireText(sortedTasks.get(i).getDescription());
        }
        frame.button("newTask").requireEnabled();
    }

    @After
    public void after() {
        frame.cleanUp();
    }

}
