package ui;

import edu.mikedev.task_manager.Model;
import edu.mikedev.task_manager.ui.LoginWindow;
import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
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
		JLabel errorlbl = frame.label(JLabelMatcher.withName("lblErrorMessage")).target();
		Assert.assertFalse(errorlbl.isEnabled());
		Assert.assertEquals("Error message", errorlbl.getText());
	}

	@Test
	@GUITest
	public void testClickLoginAndThenErrorMessage(){
		frame.button("btnLogin").click();
		JLabel errorlbl = frame.label(JLabelMatcher.withName("lblErrorMessage")).target();
		Assert.assertTrue(errorlbl.isEnabled());
		Assert.assertEquals("Username and/or password are wrong", errorlbl.getText());
	}

	@Test
	@GUITest
	public void testCorrectLogin() throws InterruptedException {
		frame.textBox("tfUsername").click().enterText("myusername");
		frame.textBox("tfPassword").click().enterText("mypassword");
		frame.button("btnLogin").click();

		Assert.assertEquals("Main page", frame.target().getTitle());
	}

	@Test
	@GUITest
	public void testWrongPromptedLogin(){
		frame.textBox("tfUsername").click().enterText("wrongusername");
		frame.textBox("tfPassword").click().enterText("wrongpassword");
		frame.button("btnLogin").click();

		Assert.assertEquals("Login page", frame.target().getTitle());
		frame.label("lblUsername").requireText("Username");
		frame.label("lblErrorMessage").requireEnabled();
	}


	@After
	public void after() {
		frame.cleanUp();
	}
}
