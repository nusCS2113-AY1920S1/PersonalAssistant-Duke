package duke.logic.command.exceptions;

/**
 * Signals that some given data does not fulfill some constraints.
 */
public class IllegalValueException extends Exception {
    /**
     * Represents an exception for values that fail constraints.
     * @param message should contain relevant information on the failed constraint(s)
     */
    public IllegalValueException(String message) {
        super(message);
    }

    /**
     * Represents an exception for values that fail constraints.
     * @param message should contain relevant information on the failed constraint(s)
     * @param cause of the main exception
     */
    public IllegalValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
