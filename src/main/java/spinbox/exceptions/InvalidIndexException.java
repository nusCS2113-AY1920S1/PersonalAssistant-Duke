package spinbox.exceptions;

public class InvalidIndexException extends InputException {
    private static final String ERROR_MESSAGE = "You have entered an invalid index.";

    public InvalidIndexException() {
        super(ERROR_MESSAGE);
    }
}
