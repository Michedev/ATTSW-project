package ui;

import edu.mikedev.task_manager.Model;
import edu.mikedev.task_manager.Task;
import edu.mikedev.task_manager.User;
import edu.mikedev.task_manager.ui.LoginWindow;
import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JLabelFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.assertj.swing.util.Triple;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(GUITestRunner.class)
public class TestNewTask extends AssertJSwingJUnitTestCase{

    LoginWindow window;
    FrameFixture frame;
    List<Task> tasksListSorted;

    @Override
    protected void onSetUp() {
        Triple<Model, User, List<Task>> scenario = TestUtils.anyLoginUserTasksScenario();
        tasksListSorted = scenario.third;
        GuiActionRunner.execute(() ->{
            window = new LoginWindow(scenario.first);
            return window;
        });
        frame = new FrameFixture(robot(), window);
        window.pack();
        frame.show();

        frame.button("btnLogin").click();
        frame.button("btnNewTask").click();
    }

    @Test
    @GUITest
    public void testInitialState(){
        frame.requireTitle("New task");
        frame.label("lblTaskName").requireText("Task Name");
        frame.label("lblTaskDescription").requireText("Task Description");
        frame.label("lblTaskDeadline").requireText("Deadline (dd/MM/yyyy)");
        frame.button("btnSave").requireText("Save");
    }

    @Test
    @GUITest
    public void testAddNewTask(){
        frame.textBox("tfTaskName").enterText("New task name");
        frame.textBox("tfTaskDescription").enterText("New task description");
        frame.textBox("tfTaskDeadline").enterText("13/10/2022");

        frame.button("btnSave").click();

        frame.requireTitle("username1 tasks");
        frame.panel("task5").requireEnabled();
        frame.label("lblTitleTask5").requireText("New task name");
    }

    @Test
    @GUITest
    public void testParseNewTaskWithDateError(){
        frame.textBox("tfTaskName").enterText("New task name");
        frame.textBox("tfTaskDescription").enterText("New task description");
        frame.textBox("tfTaskDeadline").enterText("wrong date");

        JLabelFixture lblErrorMessageDeadline = frame.label(JLabelMatcher.withName("lblErrorMessageDeadline"));

        lblErrorMessageDeadline.requireNotVisible();

        frame.button("btnSave").click();
        lblErrorMessageDeadline.requireVisible();
        lblErrorMessageDeadline.requireText("Date parsing error. It should be in the format (dd/MM/yyyy)");

    }

}
