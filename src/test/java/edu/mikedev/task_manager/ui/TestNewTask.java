package edu.mikedev.task_manager.ui;

import edu.mikedev.task_manager.Task;
import edu.mikedev.task_manager.User;
import edu.mikedev.task_manager.model.Model;
import edu.mikedev.task_manager.utils.UIScenarios;
import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.assertj.swing.util.Triple;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.swing.*;
import java.util.List;

@RunWith(GUITestRunner.class)
public class TestNewTask extends AssertJSwingJUnitTestCase{

    private transient JFrame window;
    private transient FrameFixture frame;

    @Override
    protected void onSetUp() {
        Triple<Model, User, List<Task>> scenario = UIScenarios.anyLoginUserTasksScenario();
        GuiActionRunner.execute(() ->{
            window = new JFrame();
            window.setContentPane(new NewUpdateTaskPage());
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
        frame.textBox("tfTaskName").requireText("");
        frame.textBox("tfTaskDescription").requireText("");
        frame.textBox("tfTaskDeadline").requireText("");
        frame.button("btnSave").requireText("Save");
    }
}
