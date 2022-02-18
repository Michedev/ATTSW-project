package edu.mikedev.task_manager.ui;

import edu.mikedev.task_manager.model.Model;
import edu.mikedev.task_manager.Task;
import edu.mikedev.task_manager.User;
import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;

import java.util.HashSet;
import java.util.Set;

import static java.lang.Thread.sleep;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(GUITestRunner.class)
public class TestLoginWindow extends AssertJSwingJUnitTestCase {

	LoginWindow window;
	FrameFixture frame;

	@Override
	protected void onSetUp(){
		GuiActionRunner.execute(() ->{
			window = new LoginWindow();
			return window;
		});
		frame = new FrameFixture(robot(), window);
		frame.show();
	}


    @SuppressWarnings("java:S2699")
    @Test
	@GUITest
	public void testInitialState(){
		frame.requireTitle("Login page");
		frame.textBox("tfUsername").requireEditable();
		frame.textBox("tfPassword").requireEditable();
		frame.label(JLabelMatcher.withName("lblErrorMessage")).requireNotVisible();
	}



	@After
	public void after() {
		frame.cleanUp();
	}
}
