package edu.mikedev.task_manager.ui;

import edu.mikedev.task_manager.data.Task;
import edu.mikedev.task_manager.data.User;
import edu.mikedev.task_manager.model.Model;
import edu.mikedev.task_manager.utils.UIScenarios;
import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.assertj.swing.util.Triple;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.swing.*;
import java.util.List;

@RunWith(GUITestRunner.class)
public class TestTaskDetailWindow extends AssertJSwingJUnitTestCase {

    JFrame window;
    FrameFixture frame;
    Task task;

    @Override
    protected void onSetUp(){
        Triple<Model, User, List<Task>> scenario = UIScenarios.anyLoginUserTasksScenario();
        task = scenario.third.get(4);
        GuiActionRunner.execute(() ->{
            window = new JFrame();
            window.setContentPane(new TaskDetailPage(task));
            return window;
        });
        frame = new FrameFixture(robot(), window);
        frame.show();

    }

    
    @Test
    @GUITest
    public void testInitialState(){
        frame.label("lblTaskTitle").requireText("Task 5");
        String textDescription = frame.label("lblTaskDescription").text();
        Assert.assertTrue(textDescription.startsWith("<html><p style=\"width:300px\">Super Long description"));
        Assert.assertTrue(textDescription.length() > 53);
        frame.button("btnUpdate").requireEnabled();
        frame.button("btnDelete").requireEnabled();
        frame.checkBox("cbDone").requireSelected();
        frame.label("lblDone").requireText("Done");

    }

    
    @Test
    @GUITest
    public void testBackgroundColor(){
        frame.panel("mainPanel").background().requireEqualTo(AppColors.GREEN);
    }

}
