package ui;

import edu.mikedev.task_manager.Model;
import edu.mikedev.task_manager.Task;
import edu.mikedev.task_manager.User;
import edu.mikedev.task_manager.ui.AppColors;
import edu.mikedev.task_manager.ui.LoginWindow;
import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.edt.GuiActionRunner;
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
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        for (int i = 0; i < 5; i++) {
            frame.label("lblTitleTask" + i).requireText(tasksListSorted.get(i).getTitle());
            if(i == 4){
                Assert.assertTrue(frame.label("lblDescrTask" + i).text().startsWith("Super Long description"));
            } else {
                frame.label("lblDescrTask" + i).requireText(tasksListSorted.get(i).getDescription());
            }
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
        Assert.assertTrue(longDescription.startsWith("Super Long description"));
        Assert.assertEquals(50 + 3, longDescription.length());
    }

    @After
    public void after() {
        frame.cleanUp();
    }

}
