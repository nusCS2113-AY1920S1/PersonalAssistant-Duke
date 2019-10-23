package seedu.hustler.ui.timer;

/**
 * This class contains the various types of statuses
 * needed by the program to signal specific types of
 * error, specific thread statuses, etc.
 */
public class statusTypes {

    /**
     * Types of erros that can occur. To be used so that the appropriate error
     * messages for different situations can be printed.
     */
    public enum threadError {
        RESUMEERROR,
        PAUSEERROR,
        STOPERROR
    }

    /**
     * Types of statuses the timer can have.
     */
    public enum threadStatus {
        DEFAULT,
	RUNNING,
	PAUSED,
        RESUMED,
        RESET,
        STOPPED,
        FINISHED
    }
}
