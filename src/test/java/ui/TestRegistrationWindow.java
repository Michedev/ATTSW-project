package ui;

import edu.mikedev.task_manager.Model;
import edu.mikedev.task_manager.ui.LoginWindow;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
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
        when(model.areCredentialCorrect(ArgumentMatchers.matches("myusername"),
                ArgumentMatchers.matches("mypassword"))).thenReturn(true);

        GuiActionRunner.execute(() ->{
            window = new LoginWindow(model);
            return window;
        });
        frame = new FrameFixture(robot(), window);
        frame.button("btnRegistration").click();
        frame.show();
    }

    public void testEmptyFieldsRegistration(){
        frame.label("lblErrorMessage").requireNotVisible();
        frame.label("lblErrorMessage").requireDisabled();
        frame.button("btnRegister").click();

        frame.requireTitle("Registration page");
        frame.label("lblErrorMessage").requireVisible();


    }

}
