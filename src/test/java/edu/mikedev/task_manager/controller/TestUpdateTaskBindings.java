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
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(GUITestRunner.class)
public class TestUpdateTaskBindings extends AssertJSwingJUnitTestCase{

    JFrame window;
    FrameFixture frame;
    List<Task> tasksListSorted;
    Model model;
    TaskManagerController controller;

    @Override
    protected void onSetUp() {
        Triple<Model, User, List<Task>> scenario = UIScenarios.anyLoginUserTasksScenario();
        model = scenario.first;
        tasksListSorted = scenario.third;
        GuiActionRunner.execute(() ->{
            controller = new TaskManagerController(scenario.first);
            return controller.getWindow();
        });
        window = controller.getWindow();
        frame = new FrameFixture(robot(), window);
        frame.show();

        frame.button("btnLogin").click();
        frame.panel("task0").click();
        frame.button("btnUpdate").click();
    }

    @SuppressWarnings("java:S2699")
    @Test
    @GUITest
    public void testSuccessfulUpdate(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String newTitle = "Task Updated 1";
        String newDescription = "Updated Description task 1";
        String newDeadline = "11/02/2019";

        frame.textBox("tfTaskName").deleteText().setText(newTitle);
        frame.textBox("tfTaskDescription").deleteText().setText(newDescription);
        frame.textBox("tfTaskDeadline").deleteText().setText(newDeadline);

        frame.button("btnSave").click();

        verify(model, times(1)).updateTask(any());

    }

    @SuppressWarnings("java:S2699")
    @Test
    @GUITest
    public void testWrongDateFormat(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String newTitle = "Task Updated 1";
        String newDescription = "Updated Description task 1";
        JLabelFixture lblErrorMessageDeadline = frame.label(JLabelMatcher.withName("lblErrorMessageDeadline"));
        String newDeadline = "wrong date 1";

        lblErrorMessageDeadline.requireNotVisible();

        frame.textBox("tfTaskName").deleteText().setText(newTitle);
        frame.textBox("tfTaskDescription").deleteText().setText(newDescription);
        frame.textBox("tfTaskDeadline").deleteText().setText(newDeadline);
        frame.button("btnSave").click();

        lblErrorMessageDeadline.requireVisible();

        verify(model, times(0)).updateTask(any());

    }


}
