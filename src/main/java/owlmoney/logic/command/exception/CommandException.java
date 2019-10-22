package owlmoney.logic.command.exception;

/**
 * FILL IN JAVADOCS HERE ##############################################################################.
 */
public class CommandException extends Exception {
    private String message;

    /**
     * Creates a new parserException object.
     *
     * @param message The exception message.
     */
    public CommandException(String message) {
        super(message);
        this.message = message;
    }

    /**
     * Converts any exception messages to string format.
     *
     * @return The corresponding message in string format.
     */
    public String toString() {
        return this.message;
    }
}
