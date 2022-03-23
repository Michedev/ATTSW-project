package edu.mikedev.task_manager.ui;

import edu.mikedev.task_manager.utils.UIScenarios;
import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.swing.*;

@RunWith(GUITestRunner.class)
public class TestUserTasksWindow extends AssertJSwingJUnitTestCase {

    JFrame window;
    FrameFixture frame;

    @Override
    protected void onSetUp(){
        GuiActionRunner.execute(() ->{
            window = new JFrame();
            window.setContentPane(new UserTasksPage(UIScenarios.anyLoginUserTasksScenario().third));
            return window;
        });
        frame = new FrameFixture(robot(), window);
        frame.show();
    }

    @SuppressWarnings("java:S2699")
    @Test
    @GUITest
    public void testInitialState(){
        for (int i = 0; i < 5; i++) {
            frame.panel("task" + i).requireEnabled();

        }
        frame.button("btnNewTask").requireEnabled();
        Assert.assertEquals(AppColors.RED, frame.panel("task0").target().getBackground());
        Assert.assertEquals(AppColors.ORANGE, frame.panel("task1").target().getBackground());
        Assert.assertEquals(AppColors.RED, frame.panel("task2").target().getBackground());
        Assert.assertEquals(AppColors.GREEN, frame.panel("task3").target().getBackground());
        Assert.assertEquals(AppColors.GREEN, frame.panel("task4").target().getBackground());

    }

    @SuppressWarnings("java:S2699")
    @Test
    @GUITest
    public void testLightenColorOnMouseOverTask(){
        frame.moveTo(frame.panel("task0").target().getLocation());
        Assert.assertEquals(AppColors.RED.brighter(), frame.panel("task0").target().getBackground());
        Assert.assertEquals(AppColors.ORANGE, frame.panel("task1").target().getBackground());
        Assert.assertEquals(AppColors.RED, frame.panel("task2").target().getBackground());
        Assert.assertEquals(AppColors.GREEN, frame.panel("task3").target().getBackground());
        Assert.assertEquals(AppColors.GREEN, frame.panel("task4").target().getBackground());

        frame.moveTo(frame.panel("task1").target().getLocation());
        Assert.assertEquals(AppColors.RED, frame.panel("task0").target().getBackground());
        Assert.assertEquals(AppColors.ORANGE.brighter(), frame.panel("task1").target().getBackground());
        Assert.assertEquals(AppColors.RED, frame.panel("task2").target().getBackground());
        Assert.assertEquals(AppColors.GREEN, frame.panel("task3").target().getBackground());
        Assert.assertEquals(AppColors.GREEN, frame.panel("task4").target().getBackground());

        frame.moveTo(frame.panel("task2").target().getLocation());
        Assert.assertEquals(AppColors.RED, frame.panel("task0").target().getBackground());
        Assert.assertEquals(AppColors.ORANGE, frame.panel("task1").target().getBackground());
        Assert.assertEquals(AppColors.RED.brighter(), frame.panel("task2").target().getBackground());
        Assert.assertEquals(AppColors.GREEN, frame.panel("task3").target().getBackground());
        Assert.assertEquals(AppColors.GREEN, frame.panel("task4").target().getBackground());

        frame.moveTo(frame.panel("task3").target().getLocation());
        Assert.assertEquals(AppColors.RED, frame.panel("task0").target().getBackground());
        Assert.assertEquals(AppColors.ORANGE, frame.panel("task1").target().getBackground());
        Assert.assertEquals(AppColors.RED, frame.panel("task2").target().getBackground());
        Assert.assertEquals(AppColors.GREEN.brighter(), frame.panel("task3").target().getBackground());
        Assert.assertEquals(AppColors.GREEN, frame.panel("task4").target().getBackground());

        frame.moveTo(frame.panel("task4").target().getLocation());
        Assert.assertEquals(AppColors.RED, frame.panel("task0").target().getBackground());
        Assert.assertEquals(AppColors.ORANGE, frame.panel("task1").target().getBackground());
        Assert.assertEquals(AppColors.RED, frame.panel("task2").target().getBackground());
        Assert.assertEquals(AppColors.GREEN, frame.panel("task3").target().getBackground());
        Assert.assertEquals(AppColors.GREEN.brighter(), frame.panel("task4").target().getBackground());
    }

    @After
    public void after() {
        frame.cleanUp();
    }

}
