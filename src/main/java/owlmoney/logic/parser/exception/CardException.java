package owlmoney.logic.parser.exception;

/**
 * CardException that extends Exception and handles all exception that card would throw.
 */
public class CardException extends Exception {
    private String message;

    /**
     * Creates a new cardException object.
     *
     * @param message The exception message.
     */
    public CardException(String message) {
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
