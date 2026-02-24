package gui;

import java.awt.Frame;
import java.awt.GraphicsEnvironment;

import javax.swing.SwingUtilities;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import states.Context;

class TestSwingGUI {

    @AfterEach
    void disposeFrames() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            for (Frame frame : Frame.getFrames()) {
                frame.dispose();
            }
        });
    }

    @DisplayName("SwingGUI initializes labels, buttons and listeners")
    @Test
    void testSwingGUIInitialization() throws Exception {
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless(), "Skipping SwingGUI test in headless environment");

        Context context = new Context();
        SwingGUI[] guiHolder = new SwingGUI[1];

        SwingUtilities.invokeAndWait(() -> guiHolder[0] = new SwingGUI(context));
        SwingGUI gui = guiHolder[0];

        assertNotNull(gui.b1);
        assertNotNull(gui.b2);
        assertNotNull(gui.b3);
        assertNotNull(gui.myText1);
        assertNotNull(gui.myText2);
        assertNotNull(gui.myText3);
        assertTrue(gui.b1.getActionListeners().length > 0);
        assertTrue(gui.b2.getActionListeners().length > 0);
        assertTrue(gui.b3.getActionListeners().length > 0);

        SwingUtilities.invokeAndWait(() -> gui.updateUI(context));
        assertEquals(context.getLeftText(), gui.b1.getText());
        assertEquals(context.getUpText(), gui.b2.getText());
        assertEquals(context.getRightText(), gui.b3.getText());
    }
}