package dolla.exception;

public class DollaException extends Exception {

    private String message;

    /**
     * Creates a new DollaException object.
     *
     * @param message The exception message.
     */
    public DollaException(String message) {
        super(message);
        this.message = message;
    }

    /**
     * Converts exception messages to string format.
     *
     * @return The corresponding message in string format.
     */
    public String toString() {
        return this.message;
    }
}
