package gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import states.timer.AbstractTimer;
import states.timer.IdleTimer;
import states.timer.PausedTimer;
import states.timer.RingingTimer;
import states.timer.RunningTimer;
import states.timer.SetTimer;

class TestTimerLabels extends TestGUIAbstract {

	void assertTimerLabels(AbstractTimer state) {
		c.currentState = state;
		String stateName = state.getClass().getSimpleName();
		System.out.println(stateName);
		g.updateUI(c);
		assertEquals(g.b1.getText(),c.getLeftText(),"button 1 for state " + stateName);
		assertEquals(g.b2.getText(),c.getUpText(),"button 2 for state " + stateName);
		assertEquals(g.b3.getText(),c.getRightText(),"button 3 for state " + stateName);
	}
	@DisplayName("All timer states expose button labels consistent with context texts")
	@Test
	void testIdleTimerLabels() {
		assertTimerLabels(IdleTimer.Instance());
		assertTimerLabels(PausedTimer.Instance());
		assertTimerLabels(RingingTimer.Instance());
		assertTimerLabels(RunningTimer.Instance());
		assertTimerLabels(SetTimer.Instance());
	}

	@DisplayName("Idle timer UI shows default labels and values")
   @Test
   void testTimerButtonLabels1() {
		g.updateUI(c);
		assertEquals("change mode",g.b1.getText());
		assertEquals("run",g.b2.getText());
		assertEquals("set",g.b3.getText());
		assertEquals("IdleTimer",g.myText3.getText());
		assertEquals("timer",g.myText2.getText());
		assertEquals("memTimer = 0",g.myText1.getText());
	};

	@DisplayName("Set timer UI shows increment and confirm actions")
	@Test
	void testTimerButtonLabels2() {
		c.right(); //simulate clicking on the left button
		g.updateUI(c); //apply the effect on the user interface
		assertEquals("reset",g.b1.getText());
		assertEquals("inc 5",g.b2.getText());
		assertEquals("done",g.b3.getText());
		assertEquals("SetTimer",g.myText3.getText());
		assertEquals("timer",g.myText2.getText());
		assertEquals("memTimer = 0",g.myText1.getText());
	}

	@DisplayName("Switching to stopwatch mode shows reset stopwatch labels")
	@Test
	void testStopwatchButtonLabels1() {
		c.left(); //simulate clicking on the left button
		g.updateUI(c); //apply the effect on the user interface
		assertEquals("change mode",g.b1.getText());
		assertEquals("run",g.b2.getText());
		assertEquals("(unused)",g.b3.getText());
	}

	@DisplayName("Running stopwatch mode shows split and reset button labels")
	@Test
	void testStopwatchButtonLabels2() {
		c.left(); //simulate clicking on the left button
		c.up(); //simulate clicking on the right button
		g.updateUI(c); //apply the effect on the user interface
		assertEquals("change mode",g.b1.getText());
		assertEquals("split",g.b2.getText());
		assertEquals("reset",g.b3.getText());
	}


}
