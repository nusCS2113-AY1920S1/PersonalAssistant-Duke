package seedu.hustler.ui.timer;

import seedu.hustler.ui.timer.timer.threadStatus;

/**
 * This class generates the various messages to explain why a timer has
 * been stopped (whether because it has been paused, stopped, etc.).
 * These string messages will then be printed to inform the user of what
 * has happened.
 */
public class threadStopUIMessages {

    /**
     * @return a string telling the user that the timer has been paused.
     */
    protected static String TimerPaused() {
        String output = "Timer mode has been paused!";
	return output;
    }

    /**
     * @return a string telling the user that the timer has been stopped.
     */
    protected static String TimerStopped() {
        String output = "Timer has been stopped and reset";
        return output;
    }

    /**
     * @return a string telling the user that the countdown is complete..
     */
    protected static String TimesUp() {
        String output = "Timer mode has been completed!";
	    return output;
    }

}
