package edu.mikedev.task_manager.controller;

import edu.mikedev.task_manager.Task;
import edu.mikedev.task_manager.User;
import edu.mikedev.task_manager.model.Model;
import edu.mikedev.task_manager.ui.AppColors;
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

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(GUITestRunner.class)
public class TestTaskDetailBindings extends AssertJSwingJUnitTestCase {

    JFrame window;
    FrameFixture frame;
    List<Task> tasksListSorted;
    TaskManagerController controller;
    Model model;


    @Override
    protected void onSetUp(){
        Triple<Model, User, List<Task>> scenario = UIScenarios.anyLoginUserTasksScenario();
        tasksListSorted = scenario.third;
        model = scenario.first;
        GuiActionRunner.execute(() ->{
            controller = new TaskManagerController(scenario.first);
            return controller.getWindow();
        });
        window = controller.getWindow();
        frame = new FrameFixture(robot(), window);
        frame.show();

        frame.button("btnLogin").click();
        frame.panel("task4").click();
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

        verify(model, times(1)).deleteTask(tasksListSorted.get(4));
    }

    @SuppressWarnings("java:S2699")
    @Test
    @GUITest
    public void testUpdateButton(){
        frame.button("btnUpdate").click();

        frame.requireTitle("Update task \"Task 5\"");

        frame.label("lblTaskName").requireEnabled();
        frame.label("lblTaskDescription").requireEnabled();
        frame.label("lblTaskDeadline").requireEnabled();

        frame.textBox("tfTaskName").requireText("Task 5");
        String taskDescription = frame.textBox("tfTaskDescription").text();
        Assert.assertTrue(taskDescription.startsWith("Super Long description"));
        frame.textBox("tfTaskDeadline").requireText("11/02/2014");

    }

    @SuppressWarnings("java:S2699")
    @Test
    @GUITest
    public void undoneTask(){
        frame.checkBox("cbDone").uncheck();

        frame.panel("mainPanel").background().requireEqualTo(AppColors.RED);
    }

}
