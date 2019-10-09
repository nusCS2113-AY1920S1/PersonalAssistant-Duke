package duke.exception;

/**
 * The exception Duke throws upon encountering a problem that can be recovered from.
 */
public class DukeException extends Exception {
    public static final String MESSAGE_LOAD_FILE_FAILED = "The file at %s could not be loaded.";
    public static final String MESSAGE_SAVE_FILE_FAILED = "The file at %s could not be saved to."
        + "Close other programs that may be accessing it.";
    public static final String MESSAGE_AMOUNT_INVALID = "%s is not a valid amount!";
    public static final String MESSAGE_TIME_INVALID = "%s is not a valid time!";

    public DukeException(String message) {
        super(message);
    }
}
