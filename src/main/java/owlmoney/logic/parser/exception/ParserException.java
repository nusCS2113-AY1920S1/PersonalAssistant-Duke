package owlmoney.logic.parser.exception;

public class ParserException extends Exception {
    private String message;

    public ParserException(String message) {
        super(message);
        this.message = message;
    }

    /**
     * Converts any exception messages to string format.
     * @return The corresponding message in string format.
     */
    public String toString() {
        return this.message;
    }
}
