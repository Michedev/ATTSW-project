package ui;

import edu.mikedev.task_manager.Model;
import edu.mikedev.task_manager.ui.LoginWindow;
import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JLabelFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;

import javax.swing.*;

import static java.lang.Thread.sleep;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(GUITestRunner.class)
public class TestLoginWindow extends AssertJSwingJUnitTestCase {

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
		frame.show();
	}


	@Test
	@GUITest
	public void testInitialState(){
		frame.requireTitle("Login page");
		frame.textBox("tfUsername").requireEditable();
		frame.textBox("tfPassword").requireEditable();
		frame.label(JLabelMatcher.withName("lblErrorMessage")).requireNotVisible();
	}

	@Test
	@GUITest
	public void testClickLoginAndThenErrorMessage(){
		frame.label(JLabelMatcher.withName("lblErrorMessage")).requireNotVisible();  //to find not visible elements they must be found through Matcher class
		frame.button("btnLogin").click();
		frame.label("lblErrorMessage").requireVisible();
		frame.label("lblErrorMessage").requireText("Username and/or password are wrong");
	}

	@Test
	@GUITest
	public void testCorrectLogin() throws InterruptedException {
		frame.textBox("tfUsername").click().enterText("myusername");
		frame.textBox("tfPassword").click().enterText("mypassword");
		frame.button("btnLogin").click();
		frame.requireTitle("Main page");
	}

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


	@After
	public void after() {
		frame.cleanUp();
	}
}
