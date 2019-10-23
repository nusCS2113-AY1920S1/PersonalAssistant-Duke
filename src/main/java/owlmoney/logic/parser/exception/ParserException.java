package owlmoney.logic.parser.exception;

/**
 * ParserException that extends Exception and handles all exception that parser would throw.
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
