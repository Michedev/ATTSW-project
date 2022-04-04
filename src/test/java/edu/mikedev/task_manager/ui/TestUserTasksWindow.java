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

    String task0Name = "task0";
    String task1Name = "task1";
    String task2Name = "task2";
    String task3Name = "task3";
    String task4Name = "task4";


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

    
    @Test
    @GUITest
    public void testInitialState(){
        for (int i = 0; i < 5; i++) {
            frame.panel("task" + i).requireEnabled();

        }
        frame.button("btnNewTask").requireEnabled();
        frame.robot().moveMouse(frame.button("btnNewTask").target());
        Assert.assertEquals(AppColors.RED, frame.panel(task0Name).target().getBackground());
        Assert.assertEquals(AppColors.ORANGE, frame.panel(task1Name).target().getBackground());
        Assert.assertEquals(AppColors.RED, frame.panel(task2Name).target().getBackground());
        Assert.assertEquals(AppColors.GREEN, frame.panel(task3Name).target().getBackground());
        Assert.assertEquals(AppColors.GREEN, frame.panel(task4Name).target().getBackground());

    }

    
    @Test
    @GUITest
    public void testLightenColorOnMouseOverTask(){

        frame.robot().moveMouse(frame.panel(task0Name).target());
        Assert.assertEquals(AppColors.RED.brighter(), frame.panel(task0Name).target().getBackground());
        Assert.assertEquals(AppColors.ORANGE, frame.panel(task1Name).target().getBackground());
        Assert.assertEquals(AppColors.RED, frame.panel(task2Name).target().getBackground());
        Assert.assertEquals(AppColors.GREEN, frame.panel(task3Name).target().getBackground());
        Assert.assertEquals(AppColors.GREEN, frame.panel(task4Name).target().getBackground());

        frame.robot().moveMouse(frame.panel(task1Name).target());
        Assert.assertEquals(AppColors.RED, frame.panel(task0Name).target().getBackground());
        Assert.assertEquals(AppColors.ORANGE.brighter(), frame.panel(task1Name).target().getBackground());
        Assert.assertEquals(AppColors.RED, frame.panel(task2Name).target().getBackground());
        Assert.assertEquals(AppColors.GREEN, frame.panel(task3Name).target().getBackground());
        Assert.assertEquals(AppColors.GREEN, frame.panel(task4Name).target().getBackground());

        frame.robot().moveMouse(frame.panel(task2Name).target());
        Assert.assertEquals(AppColors.RED, frame.panel(task0Name).target().getBackground());
        Assert.assertEquals(AppColors.ORANGE, frame.panel(task1Name).target().getBackground());
        Assert.assertEquals(AppColors.RED.brighter(), frame.panel(task2Name).target().getBackground());
        Assert.assertEquals(AppColors.GREEN, frame.panel(task3Name).target().getBackground());
        Assert.assertEquals(AppColors.GREEN, frame.panel(task4Name).target().getBackground());

        frame.robot().moveMouse(frame.panel(task3Name).target());
        Assert.assertEquals(AppColors.RED, frame.panel(task0Name).target().getBackground());
        Assert.assertEquals(AppColors.ORANGE, frame.panel(task1Name).target().getBackground());
        Assert.assertEquals(AppColors.RED, frame.panel(task2Name).target().getBackground());
        Assert.assertEquals(AppColors.GREEN.brighter(), frame.panel(task3Name).target().getBackground());
        Assert.assertEquals(AppColors.GREEN, frame.panel(task4Name).target().getBackground());

        frame.robot().moveMouse(frame.panel(task4Name).target());
        Assert.assertEquals(AppColors.RED, frame.panel(task0Name).target().getBackground());
        Assert.assertEquals(AppColors.ORANGE, frame.panel(task1Name).target().getBackground());
        Assert.assertEquals(AppColors.RED, frame.panel(task2Name).target().getBackground());
        Assert.assertEquals(AppColors.GREEN, frame.panel(task3Name).target().getBackground());
        Assert.assertEquals(AppColors.GREEN.brighter(), frame.panel(task4Name).target().getBackground());
    }

    @After
    public void after() {
        frame.cleanUp();
    }

}
