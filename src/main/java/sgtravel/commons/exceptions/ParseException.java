package sgtravel.commons.exceptions;

/**
 * Exception thrown when parsing fails.
 */
public class ParseException extends DukeException {

    /**
     * Constructs the Exception.
     *
     * @param message The message to display.
     */
    public ParseException(String message) {
        super(message + "\nRefer to help for format of instructions.");
    }
}
