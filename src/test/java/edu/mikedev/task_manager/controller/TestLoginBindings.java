package edu.mikedev.task_manager.controller;

import edu.mikedev.task_manager.Task;
import edu.mikedev.task_manager.User;
import edu.mikedev.task_manager.model.DBLayer;
import edu.mikedev.task_manager.model.HibernateModel;
import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;

import javax.swing.*;
import java.awt.event.WindowListener;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(GUITestRunner.class)
public class TestLoginBindings extends AssertJSwingJUnitTestCase{


    private HibernateModel model;

    JFrame window;
    FrameFixture frame;
    TaskManagerController controller;
    private DBLayer dbLayer;

    @Override
    protected void onSetUp(){
        model = mock(HibernateModel.class);
        dbLayer = mock(DBLayer.class);
        when(model.getDBLayer()).thenReturn(dbLayer);
        when(model.areCredentialCorrect(ArgumentMatchers.matches("myusername"),
                ArgumentMatchers.matches("mypassword"))).thenReturn(true);
        User dummyUser = new User("myusername", "mypassword", "email@email.com");
        Set<Task> taskSet = new HashSet<Task>();
        dummyUser.setTasks(taskSet);
        when(model.loginUser(anyString(), anyString())).thenReturn(dummyUser);
        GuiActionRunner.execute(() ->{
            controller = new TaskManagerController(model);
            return controller.getWindow();
        });
        window = controller.getWindow();
        frame = new FrameFixture(robot(), window);
        frame.show();
    }


    @SuppressWarnings("java:S2699")
    @Test
    @GUITest
    public void testClickLoginAndThenErrorMessage(){
        frame.label(JLabelMatcher.withName("lblErrorMessage")).requireNotVisible();  //to find not visible elements they must be found through Matcher class
        frame.button("btnLogin").click();
        frame.label("lblErrorMessage").requireVisible();
        frame.label("lblErrorMessage").requireText("Username and/or password are wrong");

        verify(model, times(1)).areCredentialCorrect(anyString(), anyString());
        verify(model, times(0)).loginUser(anyString(), anyString());
    }

    @SuppressWarnings("java:S2699")
    @Test
    @GUITest
    public void testCorrectLogin() throws InterruptedException {
        frame.textBox("tfUsername").click().enterText("myusername");
        frame.textBox("tfPassword").click().enterText("mypassword");
        frame.button("btnLogin").click();
        frame.requireTitle("myusername tasks");
    }

    @SuppressWarnings("java:S2699")
    @Test
    @GUITest
    public void testWrongPromptedLogin(){
        frame.textBox("tfUsername").click().enterText("wrongusername");
        frame.textBox("tfPassword").click().enterText("wrongpassword");
        frame.button("btnLogin").click();

        frame.requireTitle("Login page");

        frame.label("lblUsername").requireText("Username");
        frame.label("lblErrorMessage").requireEnabled();
    }

    @SuppressWarnings("java:S2699")
    @Test
    @GUITest
    public void goToRegisterPage(){
        frame.button("btnRegister").click();

        frame.requireTitle("Registration page");
        frame.label("lblUsername").requireEnabled();
        frame.label("lblPassword").requireEnabled();
        frame.label("lblEmail").requireEnabled();
        frame.button("btnConfirmRegister").requireEnabled();
    }

    @SuppressWarnings("java:S2699")
    @Test
    @GUITest
    public void closingWindowEvents() {
        for (WindowListener windowListener : controller.getWindow().getWindowListeners()) {
            windowListener.windowClosing(null);
        }
        verify(dbLayer, times(1)).closeConnection();
    }

}
