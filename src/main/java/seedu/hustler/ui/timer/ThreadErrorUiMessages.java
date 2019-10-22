package seedu.hustler.ui.timer;

/**
 * This class generates the various error messages to be printed
 * if the user's commands are invalid or if they cannot be performed.
 */
public class ThreadErrorUiMessages {
    /**
     * Types of errors that can occur. To be used so that the appropriate error
     * messages for different situations can be printed.
     */
    public enum ThreadErrorType {
        RESUMEERROR,
        PAUSEERROR,
        STOPERROR
    }

    /**
     * Returns a string to tell the user '/resumetimer' command
     * cannot be used currently.
     * @return a string to tell the user '/resumetimer' command
     *     cannot be used currently.
     */
    protected static String noTimerToResumeError() {
        String output = "Error: No paused timer to resume!";
        return output;
    }

    /**
     * Returns a string to tell the user '/pausetimer' command
     * cannot be used currently.
     * @return a string to tell the user '/pausetimer' command
     *     cannot be used currently.
     */
    protected static String noTimerToPauseError() {
        String output = "Error: No running timer to pause!";
        return output;
    }

    /**
     * Returns a string to tell the user '/stoptimer' command
     * cannot be used currently.
     * @return a string to tell the user '/stoptimer' command
     *     cannot be used currently.
     */
    protected static String noTimerToStopError() {
        String output = "Error! No running timer to stop!";
        return output;
    }
}
