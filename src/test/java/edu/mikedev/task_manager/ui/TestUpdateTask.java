package edu.mikedev.task_manager.ui;

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

import static edu.mikedev.task_manager.ui.UserTasksPage.htmlWrappedDescription;

@RunWith(GUITestRunner.class)
public class TestUpdateTask extends AssertJSwingJUnitTestCase{

    LoginWindow window;
    FrameFixture frame;
    List<Task> tasksListSorted;

    @Override
    protected void onSetUp() {
        Triple<Model, User, List<Task>> scenario = UIScenarios.anyLoginUserTasksScenario();
        tasksListSorted = scenario.third;
        GuiActionRunner.execute(() ->{
            window = new LoginWindow(scenario.first);
            return window;
        });
        frame = new FrameFixture(robot(), window);
        frame.show();

        frame.button("btnLogin").click();
        frame.panel("task0").click();
        frame.button("btnUpdate").click();
    }

    @SuppressWarnings("java:S2699")
    @Test
    @GUITest
    public void testInitialState(){
        frame.requireTitle("Update task \"Task 1\"");
        frame.label("lblTaskName").requireText("Task Name");
        frame.label("lblTaskDescription").requireText("Task Description");
        frame.label("lblTaskDeadline").requireText("Deadline (dd/MM/yyyy)");
        frame.textBox("tfTaskName").requireText("Task 1");
        frame.textBox("tfTaskDescription").requireText("Description task 1");
        frame.textBox("tfTaskDeadline").requireText("11/02/2014");
        frame.button("btnSave").requireText("Update");
    }

    @SuppressWarnings("java:S2699")
    @Test
    @GUITest
    public void testCorrectUpdateTask(){
        frame.textBox("tfTaskName").deleteText().enterText("Updated task abc");
        frame.textBox("tfTaskDescription").deleteText().enterText("Updated description");
        frame.textBox("tfTaskDeadline").deleteText().enterText("19/04/2022");

        frame.button("btnSave").click();

        frame.label("lblTitleTask0").requireText("Updated task abc");
        frame.label("lblDescrTask0").requireText(htmlWrappedDescription("Updated description"));
        frame.label("lblDateTask0").requireText("19/04/2022");
    }

    @SuppressWarnings("java:S2699")
    @Test
    @GUITest
    public void testWrongDateFormat(){
        frame.textBox("tfTaskDeadline").deleteText().enterText("wrong date format");

        JLabelFixture lblErrorMessageDeadline = frame.label(JLabelMatcher.withName("lblErrorMessageDeadline"));

        lblErrorMessageDeadline.requireNotVisible();

        frame.button("btnSave").click();

        frame.requireTitle("Update task \"Task 1\"");

        lblErrorMessageDeadline.requireVisible();
        lblErrorMessageDeadline.requireText("Date parsing error. It should be in the format (dd/MM/yyyy)");
    }

    @SuppressWarnings("java:S2699")
    @Test
    @GUITest
    public void testMissingTaskName(){
        frame.textBox("tfTaskName").deleteText();
        JLabelFixture lblErrorMessageName = frame.label(JLabelMatcher.withName("lblErrorMessageName"));

        lblErrorMessageName.requireNotVisible();

        frame.button("btnSave").click();

        frame.requireTitle("Update task \"Task 1\"");

        lblErrorMessageName.requireVisible();
        lblErrorMessageName.requireText("Missing task name");
    }




}
