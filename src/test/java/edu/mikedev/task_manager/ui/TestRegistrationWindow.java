package edu.mikedev.task_manager.ui;

import edu.mikedev.task_manager.model.Model;
import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.matcher.DialogMatcher;
import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JLabelFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;

import javax.swing.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(GUITestRunner.class)
public class TestRegistrationWindow extends AssertJSwingJUnitTestCase {

    JFrame window;
    FrameFixture frame;

    @Override
    protected void onSetUp(){
        Model model = mock(Model.class);
        when(model.userExists(ArgumentMatchers.matches("existinguser1"))).thenReturn(true);

        GuiActionRunner.execute(() ->{
            window = new JFrame();
            window.setContentPane(new RegistrationPage());
            return window;
        });
        frame = new FrameFixture(robot(), window);
        frame.show();

    }

    @SuppressWarnings("java:S2699")
    @Test
    @GUITest
    public void testInitState(){
        JLabelFixture lblErrorMessageUsername = frame.label(JLabelMatcher.withName("lblErrorMessageUsername"));
        JLabelFixture lblErrorMessagePassword = frame.label(JLabelMatcher.withName("lblErrorMessagePassword"));
        JLabelFixture lblErrorMessageEmail = frame.label(JLabelMatcher.withName("lblErrorMessageEmail"));
        lblErrorMessageUsername.requireNotVisible();
        lblErrorMessagePassword.requireNotVisible();
        lblErrorMessageEmail.requireNotVisible();
    }


    @After
    public void after() {
        frame.cleanUp();
    }


}
