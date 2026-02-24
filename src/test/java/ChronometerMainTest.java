import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import gui.HeadlessGUI;
import states.Context;

class ChronometerMainTest {

    private static class CountingContext extends Context {
        private int tickCount;

        @Override
        public void tick() {
            tickCount++;
            super.tick();
        }
    }

    private static class CountingGUI extends HeadlessGUI {
        private int updateCount;

        CountingGUI(Context observer) {
            super(observer);
        }

        @Override
        public void updateUI(Context c) {
            updateCount++;
            super.updateUI(c);
        }
    }

    @DisplayName("Run loop updates UI and stops cleanly on interrupt")
    @Test
    void runStopsOnInterrupt() throws InterruptedException {
        CountingContext context = new CountingContext();
        CountingGUI gui = new CountingGUI(context);
        ChronometerMain chrono = new ChronometerMain(gui, context);

        Thread runThread = new Thread(() -> chrono.run(5));
        runThread.start();

        Thread.sleep(40);
        runThread.interrupt();
        runThread.join(1000);

        assertFalse(runThread.isAlive(), "run loop should terminate after interrupt");
        assertTrue(gui.updateCount >= 2, "UI should be updated at least once before and during the loop");
        assertTrue(context.tickCount >= 1, "Context tick should be executed while the loop runs");
    }
}