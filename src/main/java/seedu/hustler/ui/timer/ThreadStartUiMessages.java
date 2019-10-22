package seedu.hustler.ui.timer;

/**
 * A class that returns a descrption of the timer activity each timer
 * the timer starts, resumes, etc.
 */
public class ThreadStartUiMessages {
    /**
     * This method informs the user each time a new timer is started.
     *
     * @param timeArray the current hours, minutes and seconds of the timer.
     * @return a string telling the user that a new timer has been started,
     *     along with the timer's duration.
     */
    protected static String timerStart(int[] timeArray) {
        String output = "Timer has commenced! Time set: " + UiLogic.padOutput(timeArray);
        return output;
    }

    /**
     * This method informs the user each time a paused timer is resumed.
     * @return a string telling the user that a paused timer has been
     *     resumed.
     */
    protected static String timerResumed() {
        String output = "Timer mode has resumed!";
        return output;
    }
}
