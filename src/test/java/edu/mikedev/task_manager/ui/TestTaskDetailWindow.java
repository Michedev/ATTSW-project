package edu.mikedev.task_manager.ui;

import edu.mikedev.task_manager.model.Model;
import edu.mikedev.task_manager.Task;
import edu.mikedev.task_manager.User;
import edu.mikedev.task_manager.utils.UIScenarios;
import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.exception.ComponentLookupException;
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
        task = scenario.third.get(0);
        GuiActionRunner.execute(() ->{
            window = new JFrame();
            window.setContentPane(new TaskDetailPage(task));
            return window;
        });
        frame = new FrameFixture(robot(), window);
        frame.show();

    }

    @SuppressWarnings("java:S2699")
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

    @SuppressWarnings("java:S2699")
    @Test
    @GUITest
    public void testBackgroundColor(){
        frame.panel("mainPanel").background().requireEqualTo(AppColors.GREEN);
    }

    @SuppressWarnings("java:S2699")
    @Test
    @GUITest
    public void testGoBackButton(){
        frame.button("btnGoBack").click();

        frame.requireTitle("username1 tasks");
        for (int i = 0; i < 5; i++) {
            frame.panel("task" + i).requireEnabled();
        }
        frame.button("btnNewTask").requireEnabled();
    }

    @SuppressWarnings("java:S2699")
    @Test
    @GUITest
    public void testDeleteButton(){
        frame.button("btnDelete").click();

        frame.requireTitle("username1 tasks");
        Assert.assertThrows(ComponentLookupException.class, () -> frame.panel("task4"));
        Assert.assertThrows(ComponentLookupException.class, () -> frame.label("lblTitleTask4"));
    }
}
