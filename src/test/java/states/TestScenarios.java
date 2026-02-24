package states;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import states.stopwatch.AbstractStopwatch;
import states.stopwatch.LaptimeStopwatch;
import states.stopwatch.ResetStopwatch;
import states.stopwatch.RunningStopwatch;
import states.timer.AbstractTimer;
import states.timer.IdleTimer;
import states.timer.PausedTimer;
import states.timer.RingingTimer;
import states.timer.RunningTimer;
import states.timer.SetTimer;

class TestScenarios {

	Context c;
	
    @BeforeEach
	void setup() {
    	c = new Context();
     	//before each test, reset the timer values to avoid interference between tests:
    	AbstractTimer.resetInitialValues();
    	AbstractStopwatch.resetInitialValues();
    }
    
  //This is more a kind of integration test than a real unit test	
	@DisplayName("Complete timer-stopwatch scenario preserves transitions and data")
  @Test
  void completeScenario() {
	  assertEquals(IdleTimer.Instance(),c.currentState);
	  assertEquals(0,AbstractTimer.getMemTimer());
	  
	  c.right(); // start incrementing the memTimer variable
	  c.tick();
	  assertSame(SetTimer.Instance(),c.currentState);
	  assertEquals(1,AbstractTimer.getMemTimer());
	  assertEquals(0,AbstractTimer.getTimer());

	  c.tick();
	  assertEquals(2,AbstractTimer.getMemTimer());
	  assertEquals(0,AbstractTimer.getTimer());

	  c.right(); // stop incrementing the memTimer variable
	  c.tick();
	  assertEquals(2,AbstractTimer.getMemTimer());
	  assertEquals(0,AbstractTimer.getTimer());
	  
	  c.up(); // start running the timer
	  assertEquals(2, AbstractTimer.getTimer(),"value of timer ");
	  c.tick();
	  assertEquals(2, AbstractTimer.getMemTimer(),"value of memTimer ");
	  assertEquals(1, AbstractTimer.getTimer(),"value of timer ");
	  
	  
	  c.up(); // pause the timer
	  c.tick();
	  assertSame(PausedTimer.Instance(), c.currentState);
	  assertEquals(2, AbstractTimer.getMemTimer(),"value of memTimer ");
	  assertEquals(1, AbstractTimer.getTimer(),"value of timer ");
	  
	  c.left(); // go to stopwatch mode
	  c.tick();
	  assertSame(ResetStopwatch.Instance(), c.currentState);
	  assertEquals(0, AbstractStopwatch.getTotalTime(),"value of totalTime ");
	  assertEquals(0, AbstractStopwatch.getLapTime(),"value of lapTime ");
	  
	  c.up(); //start running the stopwatch
	  c.tick();
	  assertSame(RunningStopwatch.Instance(), c.currentState);
	  assertEquals(1, AbstractStopwatch.getTotalTime(),"value of totalTime ");
	  assertEquals(0, AbstractStopwatch.getLapTime(),"value of lapTime ");
	 
	  c.up(); // record stopwatch laptime
	  c.tick();
	  assertSame(LaptimeStopwatch.Instance(), c.currentState);
	  assertEquals(2, AbstractStopwatch.getTotalTime(),"value of totalTime ");
	  assertEquals(1, AbstractStopwatch.getLapTime(),"value of lapTime ");

	  c.left(); // go back to timer mode (remembering history state)
	  c.tick();
	  assertSame(PausedTimer.Instance(), c.currentState);
	  assertEquals(2, AbstractTimer.getMemTimer(),"value of memTimer ");
	  assertEquals(1, AbstractTimer.getTimer(),"value of timer ");
	  
	  c.up(); // continue running timer
	  assertSame(RunningTimer.Instance(), c.currentState);
	  c.tick();
	  //automatic switch to ringing timer since timer has reached 0...
	  assertSame(RingingTimer.Instance(), c.currentState);
	  assertEquals(2, AbstractTimer.getMemTimer(),"value of memTimer ");
	  assertEquals(0, AbstractTimer.getTimer(),"value of timer ");
	  
	  c.right(); // return to idle timer state
	  c.tick();
	  assertSame(IdleTimer.Instance(), c.currentState);
	  assertEquals(2, AbstractTimer.getMemTimer(),"value of memTimer ");
	  assertEquals(0, AbstractTimer.getTimer(),"value of timer ");
	  }

}
