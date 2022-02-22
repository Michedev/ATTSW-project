package edu.mikedev.task_manager.controller;

import edu.mikedev.task_manager.Task;
import edu.mikedev.task_manager.User;
import edu.mikedev.task_manager.model.Model;
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

import javax.swing.*;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(GUITestRunner.class)
public class TestNewTaskBindings extends AssertJSwingJUnitTestCase{

    private transient JFrame window;
    Model model;
    private transient FrameFixture frame;
    TaskManagerController controller;


    @Override
    protected void onSetUp() {
        Triple<Model, User, List<Task>> scenario = UIScenarios.anyLoginUserTasksScenario();
        model = scenario.first;
        GuiActionRunner.execute(() ->{
            controller = new TaskManagerController(scenario.first);
            return controller.getWindow();
        });
        window = controller.getWindow();
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

        verify(model, times(1)).addNewTask(any());
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
