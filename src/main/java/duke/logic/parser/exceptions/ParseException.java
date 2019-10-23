package duke.logic.parser.exceptions;

/**
 * Represents a parse error encountered by a parser.
 */
public class ParseException extends RuntimeException {

    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParseException() {

    }
}
