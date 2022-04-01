package edu.mikedev.task_manager.ui;

import edu.mikedev.task_manager.data.Task;
import edu.mikedev.task_manager.data.User;
import edu.mikedev.task_manager.model.Model;
import edu.mikedev.task_manager.utils.UIScenarios;
import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.assertj.swing.util.Triple;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.List;

@RunWith(GUITestRunner.class)
public class TestUpdateTask extends AssertJSwingJUnitTestCase{

    JFrame window;
    FrameFixture frame;
    Task task;

    @Override
    protected void onSetUp() {
        Triple<Model, User, List<Task>> scenario = UIScenarios.anyLoginUserTasksScenario();
        task = scenario.third.get(0);
        GuiActionRunner.execute(() ->{
            window = new JFrame();
            window.setContentPane(new NewUpdateTaskPage(task));
            return window;
        });
        frame = new FrameFixture(robot(), window);
        frame.show();
    }

    @SuppressWarnings("java:S2699")
    @Test
    @GUITest
    public void testInitialState(){
        frame.label("lblTaskName").requireText("Task Name");
        frame.label("lblTaskDescription").requireText("Task Description");
        frame.label("lblTaskDeadline").requireText("Deadline (dd/MM/yyyy)");
        frame.textBox("tfTaskName").requireText(task.getTitle());
        frame.textBox("tfTaskDescription").requireText(task.getDescription());
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        frame.textBox("tfTaskName").requireText(task.getTitle());
        frame.textBox("tfTaskDescription").requireText(task.getDescription());
        frame.textBox("tfTaskDeadline").requireText(dateFormatter.format(task.getDeadline()));
        frame.button("btnSave").requireText("Update");
        frame.label(JLabelMatcher.withName("lblErrorMessageName")).requireNotVisible();
        frame.label(JLabelMatcher.withName("lblErrorMessageDescription")).requireNotVisible();
        frame.label(JLabelMatcher.withName("lblErrorMessageDeadline")).requireNotVisible();

    }
}
