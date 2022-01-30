package ui;

import edu.mikedev.task_manager.Model;
import edu.mikedev.task_manager.ui.LoginWindow;
import org.assertj.swing.annotation.GUITest;
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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(GUITestRunner.class)
public class TestRegistrationWindow extends AssertJSwingJUnitTestCase {

    LoginWindow window;
    FrameFixture frame;

    @Override
    protected void onSetUp(){
        Model model = mock(Model.class);

        GuiActionRunner.execute(() ->{
            window = new LoginWindow(model);
            return window;
        });
        frame = new FrameFixture(robot(), window);
        frame.show();

        frame.button("btnRegister").click();
    }

    @Test
    @GUITest
    public void testInitState(){
        JLabelFixture lblErrorMessageUsername = frame.label(JLabelMatcher.withName("lblErrorMessageUsername"));
        JLabelFixture lblErrorMessagePassword = frame.label(JLabelMatcher.withName("lblErrorMessagePassword"));
        JLabelFixture lblErrorMessageEmail = frame.label(JLabelMatcher.withName("lblErrorMessageEmail"));
        lblErrorMessageUsername.requireNotVisible();
        lblErrorMessagePassword.requireNotVisible();
        lblErrorMessageEmail.requireNotVisible();
        frame.requireTitle("Registration page");

    }

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


    @After
    public void after() {
        frame.cleanUp();
    }


}
