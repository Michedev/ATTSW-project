package ui;

import edu.mikedev.task_manager.Model;
import edu.mikedev.task_manager.Task;
import edu.mikedev.task_manager.User;
import edu.mikedev.task_manager.ui.LoginWindow;
import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.assertj.swing.util.Triple;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(GUITestRunner.class)
public class TestNewUpdateTaskWindow extends AssertJSwingJUnitTestCase{

    LoginWindow window;
    FrameFixture frame;
    List<Task> tasksListSorted;

    @Override
    protected void onSetUp() {
        Triple<Model, User, List<Task>> scenario = TestUtils.anyLoginUserTasksScenario();
        tasksListSorted = scenario.third;
        GuiActionRunner.execute(() ->{
            window = new LoginWindow(scenario.first);
            return window;
        });
        frame = new FrameFixture(robot(), window);
        frame.show();

        frame.button("btnLogin").click();
        frame.button("btnNewTask").click();
    }

    @Test
    @GUITest
    public void testInitialState(){
        frame.requireTitle("New task");
        frame.label("lblTaskName").requireText("Task name");
        frame.label("lblTaskDescription").requireText("Task description");
        frame.label("lblTaskDeadline").requireText("Deadline (dd/MM/yyyy)");
        frame.button("btnSaveButton").requireText("Save");
    }
}
