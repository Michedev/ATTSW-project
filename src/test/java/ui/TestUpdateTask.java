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
public class TestUpdateTask extends AssertJSwingJUnitTestCase{

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
        frame.panel("task0").click();
        frame.button("btnUpdate").click();
    }

    @Test
    @GUITest
    public void testInitialState(){
        frame.requireTitle("Update task \"Task 1\"");
        frame.label("lblTaskName").requireText("Task Name");
        frame.label("lblTaskDescription").requireText("Task Description");
        frame.label("lblTaskDeadline").requireText("Deadline (dd/MM/yyyy)");
        frame.textBox("tfTaskName").requireText("Task 1");
        frame.textBox("tfTaskDescription").requireText("Description task 1");
        frame.textBox("tfTaskDeadline").requireText("11/02/2014");
        frame.button("btnSave").requireText("Update");
    }

    @Test
    @GUITest
    public void testCorrectUpdateTask(){
        frame.textBox("tfTaskName").deleteText().enterText("Updated task abc");
        frame.textBox("tfTaskDescription").deleteText().enterText("Updated description");
        frame.textBox("tfTaskDeadline").deleteText().enterText("19/04/2022");

        frame.button("btnSave").click();

        frame.label("lblTitleTask0").requireText("Updated task abc");
        frame.label("lblDescrTask0").requireText("Updated description");
        frame.label("lblDateTask0").requireText("19/04/2022");
    }

//    @Test
//    @GUITest
//    public void testWrongDateFormat(){
//
//    }
//
//    @Test
//    @GUITest
//    public void testMissingTaskName(){
//
//    }




}
