package edu.mikedev.task_manager.ui;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.swing.*;

@RunWith(GUITestRunner.class)
public class TestLoginWindow extends AssertJSwingJUnitTestCase {

	JFrame window;
	FrameFixture frame;

	@Override
	protected void onSetUp(){
		GuiActionRunner.execute(() ->{
			window = new JFrame();
			window.setContentPane(new LoginPage());
			return window;
		});
		frame = new FrameFixture(robot(), window);
		frame.show();
	}


    @SuppressWarnings("java:S2699")
    @Test
	@GUITest
	public void testInitialState(){
		frame.textBox("tfUsername").requireEditable();
		frame.textBox("tfPassword").requireEditable();
		frame.label(JLabelMatcher.withName("lblErrorMessage")).requireNotVisible();
	}



	@After
	public void after() {
		frame.cleanUp();
	}
}
