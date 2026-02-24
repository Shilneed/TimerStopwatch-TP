package gui;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JFrame;

import states.EventListener;

/**
 * @author tommens
 * This class extends the HeadlessGUI to draw a real GUI on the screen
 */
public class SwingGUI extends HeadlessGUI {

    public SwingGUI(EventListener o) { super(o); }
    
    protected void initGUI() {
        super.initGUI();

        JFrame myFrame = new JFrame("Chronometer");
        Container myContent = myFrame.getContentPane();
        // grid layout with 2 rows and 3 columns
        myContent.setLayout(new GridLayout(2,3,1,1));
        // filling first row of grid (3 columns) with text information
        myContent.add(myText1,0);
        myContent.add(myText2,1);
        myContent.add(myText3,2);
        // filling second row of grid (3 columns) with buttons
        myContent.add(b1);
        myContent.add(b2);
        myContent.add(b3);
        myFrame.setSize(1000, 1000);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setVisible(true);
    }
       
}
