package ui;

import edu.mikedev.task_manager.Model;
import edu.mikedev.task_manager.Task;
import edu.mikedev.task_manager.User;
import edu.mikedev.task_manager.ui.LoginWindow;
import edu.mikedev.task_manager.ui.NewUpdateTask;
import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JLabelFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.assertj.swing.util.Triple;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.List;

@RunWith(GUITestRunner.class)
public class TestNewUpdateTaskWindow extends AssertJSwingJUnitTestCase{

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
    public void parseNewTask(){
        frame.textBox("tfTaskName").enterText("New task name");
        frame.textBox("tfTaskDescription").enterText("New task description");
        frame.textBox("tfTaskDeadline").enterText("13/10/2022");

        Task parsedTask = ((NewUpdateTask) frame.panel("mainPanel").target()).parseTask();
        Assert.assertEquals("New task name", parsedTask.getTitle());
        Assert.assertEquals("New task description", parsedTask.getDescription());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Assert.assertEquals("13/10/2022", dateFormat.format(parsedTask.getDeadline()));
    }

    @Test
    @GUITest
    public void parseNewTaskWithDateError(){
        frame.textBox("tfTaskName").enterText("New task name");
        frame.textBox("tfTaskDescription").enterText("New task description");
        frame.textBox("tfTaskDeadline").enterText("wrong date");

        JLabelFixture lblErrorMessageDeadline = frame.label(JLabelMatcher.withName("lblErrorMessageDeadline"));
        lblErrorMessageDeadline.requireText("Date parsing error. It should be in the format (dd/MM/yyyy)");

        frame.button("btnSave").click();


    }

}
