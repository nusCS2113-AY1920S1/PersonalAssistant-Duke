package owlmoney;

public class MainException extends Exception {
    private String message;

    /**
     * Creates a new GoalsException object.
     *
     * @param message The exception message.
     */
    public MainException(String message) {
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
