package ui;

import edu.mikedev.task_manager.ui.LoginWindow;
import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.swing.*;

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


	@Test
	@GUITest
	public void testInitialState(){
		JLabel errorlbl = frame.label(JLabelMatcher.withName("lblErrorMessage")).target();
		Assert.assertFalse(errorlbl.isEnabled());
		Assert.assertEquals("Error message", errorlbl.getText());
	}
	
}
