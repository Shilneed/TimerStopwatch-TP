package gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import states.stopwatch.ResetStopwatch;
import states.timer.AbstractTimer;
import states.timer.IdleTimer;
import states.timer.SetTimer;

class TestGUIInteractions extends TestGUIAbstract {

    @DisplayName("Button listeners trigger timer transitions and actions")
    @Test
    void testTimerButtonClicks() {
        assertSame(IdleTimer.Instance(), c.currentState);

        g.b3.doClick();
        assertSame(SetTimer.Instance(), c.currentState);

        g.b2.doClick();
        assertEquals(5, AbstractTimer.getMemTimer());

        g.b1.doClick();
        assertEquals(0, AbstractTimer.getMemTimer());

        g.b3.doClick();
        assertSame(IdleTimer.Instance(), c.currentState);
    }

    @DisplayName("Left button in timer mode changes to stopwatch mode")
    @Test
    void testModeSwitchByClick() {
        assertSame(IdleTimer.Instance(), c.currentState);
        g.b1.doClick();
        assertSame(ResetStopwatch.Instance(), c.currentState);
    }
}
