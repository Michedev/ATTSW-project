package ui;

import edu.mikedev.task_manager.Model;
import edu.mikedev.task_manager.Task;
import edu.mikedev.task_manager.User;
import edu.mikedev.task_manager.ui.AppColors;
import edu.mikedev.task_manager.ui.LoginWindow;
import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.exception.ComponentLookupException;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.assertj.swing.util.Triple;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(GUITestRunner.class)
public class TestTaskDetailWindow extends AssertJSwingJUnitTestCase {

    LoginWindow window;
    FrameFixture frame;
    List<Task> tasksListSorted;


    @Override
    protected void onSetUp(){
        Triple<Model, User, List<Task>> scenario = TestUtils.anyLoginUserTasksScenario();
        tasksListSorted = scenario.third;
        GuiActionRunner.execute(() ->{
            window = new LoginWindow(scenario.first);
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
        frame.requireTitle("Task Details");
        frame.label("lblTaskTitle").requireText("Task 5");
        String textDescription = frame.label("lblTaskDescription").text();
        Assert.assertTrue(textDescription.startsWith("<html><p style=\"width:300px\">Super Long description"));
        Assert.assertTrue(textDescription.length() > 53);
        frame.button("btnUpdate").requireEnabled();
        frame.button("btnDelete").requireEnabled();
    }

    @Test
    @GUITest
    public void testBackgroundColor(){
        frame.panel("mainPanel").background().requireEqualTo(AppColors.GREEN);
    }

    @Test
    @GUITest
    public void testGoBackButton(){
        frame.button("btnGoBack").click();

        frame.requireTitle("username1 tasks");
        for (int i = 0; i < 5; i++) {
            frame.panel("task" + i).requireEnabled();
        }
        frame.button("btnNewTask").requireEnabled();
    }

    @Test
    @GUITest
    public void testDeleteButton(){
        frame.button("btnDelete").click();

        frame.requireTitle("username1 tasks");
        Assert.assertThrows(ComponentLookupException.class, () -> frame.panel("task4"));
        Assert.assertThrows(ComponentLookupException.class, () -> frame.label("lblTitleTask4"));

    }

}
