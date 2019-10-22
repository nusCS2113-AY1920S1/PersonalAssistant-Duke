package seedu.hustler.ui.timer;

/**
 * This class generates the various messages to explain why a timer has
 * been stopped (whether because it has been paused, stopped, etc.).
 * These string messages will then be printed to inform the user of what
 * has happened.
 */
public class ThreadStopUiMessages {
    /**
     * Returns a message to tell the user the timer has been paused.
     * @return a string telling the user that the timer has been paused.
     */
    protected static String timerPaused() {
        String output = "Timer mode has been paused!";
        return output;
    }

    /**
     * Returns a message to tell the user the timer has been stopped.
     * @return a string telling the user that the timer has been stopped.
     */
    protected static String timerStopped() {
        String output = "Timer has been stopped and reset";
        return output;
    }

    /**
     * Returns a message to tell the user the countdown has been completed.
     * @return a string telling the user that the countdown has been completed.
     */
    protected static String timesUp() {
        String output = "Timer mode has been completed!";
        return output;
    }
}
