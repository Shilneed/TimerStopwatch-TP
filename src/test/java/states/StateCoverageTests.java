package states;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import states.stopwatch.AbstractStopwatch;
import states.stopwatch.LaptimeStopwatch;
import states.stopwatch.RunningStopwatch;
import states.timer.AbstractTimer;
import states.timer.IdleTimer;
import states.timer.RingingTimer;
import states.timer.RunningTimer;
import states.timer.SetTimer;

class StateCoverageTests {

    private Context context;

    @BeforeEach
    void setup() {
        context = new Context();
        AbstractTimer.resetInitialValues();
        AbstractStopwatch.resetInitialValues();
        context.currentState = IdleTimer.Instance();
    }

    @DisplayName("SetTimer left resets memTimer and stays in SetTimer")
    @Test
    void setTimerLeftResetsAndNoTransition() {
        context.right();
        context.tick();
        assertSame(SetTimer.Instance(), context.currentState);
        assertEquals(1, AbstractTimer.getMemTimer());

        assertSame(SetTimer.Instance(), context.currentState.left());
        assertEquals(0, AbstractTimer.getMemTimer());
    }

    @DisplayName("RunningTimer tick decrements timer without ringing when value stays positive")
    @Test
    void runningTimerTickStaysRunningWhenPositive() {
        context.right();
        context.tick();
        context.up();
        context.right();
        context.up();

        assertSame(RunningTimer.Instance(), context.currentState);
        assertEquals(6, AbstractTimer.getTimer());

        context.tick();
        assertSame(RunningTimer.Instance(), context.currentState);
        assertEquals(5, AbstractTimer.getTimer());
    }

    @DisplayName("RingingTimer sets and clears isRinging on entry and exit")
    @Test
    void ringingTimerEntryExitFlags() {
        context.right();
        context.tick();
        context.right();
        context.up();

        assertSame(RunningTimer.Instance(), context.currentState);
        assertEquals(1, AbstractTimer.getTimer());

        context.tick();
        assertSame(RingingTimer.Instance(), context.currentState);
        assertTrue(AbstractTimer.isRinging());

        context.right();
        assertSame(IdleTimer.Instance(), context.currentState);
        assertEquals(false, AbstractTimer.isRinging());
    }

    @DisplayName("LaptimeStopwatch returns to RunningStopwatch after timeout")
    @Test
    void laptimeTimeoutTransitionsBackToRunning() {
        context.left();
        context.up();
        context.up();

        assertSame(LaptimeStopwatch.Instance(), context.currentState);

        for (int i = 0; i < 4; i++) {
            context.tick();
            assertSame(LaptimeStopwatch.Instance(), context.currentState);
        }

        context.tick();
        assertSame(RunningStopwatch.Instance(), context.currentState);
    }
}
