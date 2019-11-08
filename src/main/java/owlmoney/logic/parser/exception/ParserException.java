package owlmoney.logic.parser.exception;

/**
 * Handles all exceptions that parser would throw.
 */
public class ParserException extends Exception {
    private String message;

    /**
     * Creates a new parserException object.
     *
     * @param message The exception message.
     */
    public ParserException(String message) {
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
