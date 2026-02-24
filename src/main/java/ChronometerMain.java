import gui.HeadlessGUI;
import gui.SwingGUI;
import states.Context;

public class ChronometerMain {
    
    private HeadlessGUI g;
	private Context c;

    ChronometerMain() {}

    ChronometerMain(HeadlessGUI gui, Context context) {
        this.g = gui;
        this.c = context;
    }
   	
    // The method run() ensures that with a given frequency
    // the state machine's actions are executed with tick() and
    // the ui is updated accordingly with updateUIText().    
    void run(int frequency) {
        // infinite loop that asks the current state to do whatever it needs to do
        // and that updates the graphical user interface accordingly
 		  g.updateUI(c);
        	 while (!Thread.currentThread().isInterrupted()) {
    		try { Thread.sleep(frequency); }
        		catch (InterruptedException e) {
        			Thread.currentThread().interrupt();
        			break;
        		}
 	        g.updateUI(c);
 	        c.tick();
  	      }
      }
    
    public static void main(String[] args) {
        ChronometerMain myChrono = new ChronometerMain(); // create an instance of Chronometer;
        myChrono.c = new Context(); // create the state machine context
        myChrono.g = new SwingGUI(myChrono.c); // create the GUI and pass it the context
        
        myChrono.run(100); // and start running with frequency of 100 millisecs
        
    }
   
}
