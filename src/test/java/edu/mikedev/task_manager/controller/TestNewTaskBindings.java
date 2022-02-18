package edu.mikedev.task_manager.ui;

import edu.mikedev.task_manager.controller.TaskManagerController;
import edu.mikedev.task_manager.model.Model;
import edu.mikedev.task_manager.Task;
import edu.mikedev.task_manager.User;
import edu.mikedev.task_manager.utils.UIScenarios;
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
public class TestNewTaskBindings extends AssertJSwingJUnitTestCase{

    private transient LoginWindow window;
    private transient FrameFixture frame;
    TaskManagerController controller;


    @Override
    protected void onSetUp() {
        Triple<Model, User, List<Task>> scenario = UIScenarios.anyLoginUserTasksScenario();
        GuiActionRunner.execute(() ->{
            window = new LoginWindow();
            return window;
        });
        controller = new TaskManagerController(scenario.first, window);
        frame = new FrameFixture(robot(), window);
        frame.show();

        frame.button("btnLogin").click();
        frame.button("btnNewTask").click();
    }

    @SuppressWarnings("java:S2699")
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

    @SuppressWarnings("java:S2699")
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

    @SuppressWarnings("java:S2699")
    @Test
    @GUITest
    public void testMissingTaskName(){
        frame.textBox("tfTaskDescription").enterText("New task description");
        frame.textBox("tfTaskDeadline").enterText("13/10/2022");

        JLabelFixture lblErrorMessageName = frame.label(JLabelMatcher.withName("lblErrorMessageName"));

        lblErrorMessageName.requireNotVisible();

        frame.button("btnSave").click();

        lblErrorMessageName.requireVisible();
        lblErrorMessageName.requireText("Missing task name");
    }


    @SuppressWarnings("java:S2699")
    @Test
    @GUITest
    public void testAnythingMissing(){
        JLabelFixture lblErrorMessageName = frame.label(JLabelMatcher.withName("lblErrorMessageName"));
        JLabelFixture lblErrorMessageDeadline = frame.label(JLabelMatcher.withName("lblErrorMessageDeadline"));

        lblErrorMessageName.requireNotVisible();
        lblErrorMessageDeadline.requireNotVisible();
        frame.button("btnSave").click();

        lblErrorMessageName.requireVisible();
        lblErrorMessageName.requireText("Missing task name");
        lblErrorMessageDeadline.requireVisible();
        lblErrorMessageDeadline.requireText("Date parsing error. It should be in the format (dd/MM/yyyy)");

    }
}
