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
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static edu.mikedev.task_manager.ui.UserTasksPage.htmlWrappedDescription;
import static org.mockito.Mockito.mock;

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
    }

    @After
    public void after() {
        frame.cleanUp();
    }

}
