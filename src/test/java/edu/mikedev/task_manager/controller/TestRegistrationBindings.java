package edu.mikedev.task_manager.controller;

import edu.mikedev.task_manager.Task;
import edu.mikedev.task_manager.User;
import edu.mikedev.task_manager.model.Model;
import edu.mikedev.task_manager.utils.UIScenarios;
import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.matcher.DialogMatcher;
import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.exception.ComponentLookupException;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JLabelFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.assertj.swing.util.Triple;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;

import javax.swing.*;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(GUITestRunner.class)
public class TestRegistrationBindings extends AssertJSwingJUnitTestCase {

    Model model;
    JFrame window;
    FrameFixture frame;
    TaskManagerController controller;


    @Override
    protected void onSetUp(){
        Triple<Model, User, List<Task>> scenario = UIScenarios.anyLoginUserTasksScenario();
        when(scenario.first.userExists(ArgumentMatchers.matches("existinguser1"))).thenReturn(true);

        GuiActionRunner.execute(() ->{
            controller = new TaskManagerController(scenario.first);
            return controller.getWindow();
        });
        window = controller.getWindow();
        frame = new FrameFixture(robot(), window);
        frame.show();

        frame.button("btnRegister").click();
    }

    @SuppressWarnings("java:S2699")
    @Test
    @GUITest
    public void testEmptyFieldsRegistration(){
        JLabelFixture lblErrorMessageUsername = frame.label(JLabelMatcher.withName("lblErrorMessageUsername"));
        JLabelFixture lblErrorMessagePassword = frame.label(JLabelMatcher.withName("lblErrorMessagePassword"));
        JLabelFixture lblErrorMessageEmail = frame.label(JLabelMatcher.withName("lblErrorMessageEmail"));

        frame.button("btnConfirmRegister").click();

        lblErrorMessageUsername.requireVisible();
        lblErrorMessageUsername.requireText("Missing username");
        lblErrorMessagePassword.requireVisible();
        lblErrorMessagePassword.requireText("Missing password");
        lblErrorMessageEmail.requireVisible();
        lblErrorMessageEmail.requireText("Missing e-mail");
    }

    @SuppressWarnings("java:S2699")
    @Test
    @GUITest
    public void testWrongFormattedEmail(){
        frame.textBox("tfEmail").setText("This is not an email");

        frame.button("btnConfirmRegister").click();

        JLabelFixture lblErrorMessageEmail = frame.label(JLabelMatcher.withName("lblErrorMessageEmail"));
        lblErrorMessageEmail.requireVisible();
        lblErrorMessageEmail.requireText("The input prompted above is not an e-mail");
    }

    @SuppressWarnings("java:S2699")
    @Test
    @GUITest
    public void testExistingUser(){
        frame.textBox("tfUsername").setText("existinguser1");

        frame.button("btnConfirmRegister").click();

        JLabelFixture lblErrorMessageUsername = frame.label(JLabelMatcher.withName("lblErrorMessageUsername"));
        lblErrorMessageUsername.requireVisible();
        lblErrorMessageUsername.requireText("Username exists");
    }

    @SuppressWarnings("java:S2699")
    @Test
    @GUITest
    public void testCorrectRegistration(){
        frame.textBox("tfUsername").setText("newuser1");
        frame.textBox("tfPassword").setText("password1");
        frame.textBox("tfEmail").setText("email@email.it");

        frame.button("btnConfirmRegister").click();

        frame.dialog(DialogMatcher.withTitle("Registration completed")).requireVisible();
    }

    @SuppressWarnings("java:S2699")
    @Test
    @GUITest
    public void testCancelButton() {
        frame.button("btnCancel").click();

        Assert.assertThrows(ComponentLookupException.class, () -> frame.button("btnCancel"));
        Assert.assertThrows(ComponentLookupException.class, () -> frame.button("btnConfirmRegister"));
        frame.requireTitle("Login page");
        frame.button("btnLogin").requireEnabled();

    }


        @After
    public void after() {
        frame.cleanUp();
    }


}
