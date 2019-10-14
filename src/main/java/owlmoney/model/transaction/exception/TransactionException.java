package owlmoney.model.transaction.exception;

public class TransactionException extends Exception {
    private String message;

    /**
     * Creates a new TransactionException object.
     *
     * @param message The exception message.
     */
    public TransactionException(String message) {
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
