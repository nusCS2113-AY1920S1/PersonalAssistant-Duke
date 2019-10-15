package seedu.hustler.ui.timer;

/**
 * This class generates the various error messages to be printed
 * if the user's commands are invalid or if they cannot be performed.
 */
public class threadErrorUIMessages {

    /**
     * Types of erros that can occur. To be used so that the appropriate error
     * messages for different situations can be printed.
     */
    public enum threadErrorType {
        RESUMEERROR,
        PAUSEERROR,
        STOPERROR
    }

    /**
     * @return a string telling the user that the 'resumetimer' command cannot
     * currently be used.
     */
    protected static String NoTimerToResumeError() {
        String output = "Error: No paused timer to resume!";
        return output;
    }

    /**
     * @return a string telling the user that the 'pausetimer' command cannot
     * currently be used.
     */
    protected static String NoTimerToPauseError() {
        String output = "Error: No running timer to pause!";
        return output;
    }

    /**
     * @return a string telling the user that the 'stoptimer' command cannot
     * currently be used.
     */
    protected static String NoTimerToStopError() {
        String output = "Error! No running timer to stop!";
        return output;
    }
}
