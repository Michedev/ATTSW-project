package edu.mikedev.task_manager.ui;

import edu.mikedev.task_manager.Model;
import edu.mikedev.task_manager.Task;
import edu.mikedev.task_manager.User;
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

    LoginWindow window;
    FrameFixture frame;
    List<Task> tasksListSorted;

    @Override
    protected void onSetUp(){
        Triple<Model, User, List<Task>> scenario = TestUtils.anyLoginUserTasksScenario();
        Model model = scenario.first;
        tasksListSorted = scenario.third;
        GuiActionRunner.execute(() ->{
            window = new LoginWindow(model);
            return window;
        });
        frame = new FrameFixture(robot(), window);
        frame.show();
        frame.textBox("tfUsername").enterText("username1");
        frame.button("btnLogin").click();
    }

    @Test
    @GUITest
    public void testInitialState(){
        frame.requireTitle("username1 tasks");
        for (int i = 0; i < 5; i++) {
            frame.panel("task" + i).requireEnabled();

        }
        frame.button("btnNewTask").requireEnabled();
    }

    @Test
    @GUITest
    public void testCorrectOrderTasks(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        for (int i = 0; i < 5; i++) {
            frame.label("lblTitleTask" + i).requireText(tasksListSorted.get(i).getTitle());
            if(i == 4){
                Assert.assertTrue(frame.label("lblDescrTask" + i).text().startsWith("<html><p style=\"width:75px\">Super Long description"));
            } else {
                frame.label("lblDescrTask" + i).requireText(htmlWrappedDescription(tasksListSorted.get(i).getDescription()));
            }
            frame.label("lblDateTask" + i).requireText(dateFormat.format(tasksListSorted.get(i).getDeadline()));
        }
    }

    @Test
    @GUITest
    public void testCorrectColorCards(){
        List<Color> expectedColors = Arrays.asList(AppColors.RED, AppColors.ORANGE, AppColors.RED, AppColors.GREEN, AppColors.GREEN);
        for(int i = 0; i < 5; i++){
            JPanel taskPanel = frame.panel("task"+i).target();
            Assert.assertEquals(expectedColors.get(i), taskPanel.getBackground());
        }
    }

    @Test
    @GUITest
    public void testCorrectFormattingLongDescritption(){
        String longDescription = frame.label("lblDescrTask4").text();
        Assert.assertTrue(longDescription.startsWith("<html><p style=\"width:75px\">Super Long description"));
        Assert.assertEquals(50 + 3, longDescription.replaceAll("<html><p style=\"width:75px\">", "").replaceAll("</p></html>", "").length());
    }

    @Test
    @GUITest
    public void testNewTaskTransition(){
        frame.button("btnNewTask").click();
        frame.requireTitle("New task");
        Assert.assertThrows(ComponentLookupException.class, () -> frame.panel("task0"));
        Assert.assertThrows(ComponentLookupException.class, () -> frame.panel("task1"));
        Assert.assertThrows(ComponentLookupException.class, () -> frame.panel("task2"));
        Assert.assertThrows(ComponentLookupException.class, () -> frame.panel("task3"));
        Assert.assertThrows(ComponentLookupException.class, () -> frame.panel("task4"));


    }

    @After
    public void after() {
        frame.cleanUp();
    }

}
