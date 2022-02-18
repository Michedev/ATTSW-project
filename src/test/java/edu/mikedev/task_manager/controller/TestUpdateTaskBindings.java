package edu.mikedev.task_manager.controller;

import edu.mikedev.task_manager.Task;
import edu.mikedev.task_manager.User;
import edu.mikedev.task_manager.model.Model;
import edu.mikedev.task_manager.ui.LoginWindow;
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
public class TestUpdateTaskBindings extends AssertJSwingJUnitTestCase{

    LoginWindow window;
    FrameFixture frame;
    List<Task> tasksListSorted;
    TaskManagerController controller;

    @Override
    protected void onSetUp() {
        Triple<Model, User, List<Task>> scenario = UIScenarios.anyLoginUserTasksScenario();
        tasksListSorted = scenario.third;
        GuiActionRunner.execute(() ->{
            window = new LoginWindow();
            return window;
        });
        controller = new TaskManagerController(scenario.first, window);
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
}
