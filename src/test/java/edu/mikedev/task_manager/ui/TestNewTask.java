package edu.mikedev.task_manager.ui;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.swing.*;

@RunWith(GUITestRunner.class)
public class TestNewTask extends AssertJSwingJUnitTestCase{

    private JFrame window;
    private FrameFixture frame;

    @Override
    protected void onSetUp() {
        GuiActionRunner.execute(() ->{
            window = new JFrame();
            window.setContentPane(new NewUpdateTaskPage());
            return window;
        });
        frame = new FrameFixture(robot(), window);
        frame.show();

    }

    
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
