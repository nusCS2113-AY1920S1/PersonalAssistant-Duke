package owlmoney.model.bank.exception;

public class BankException extends Exception {
    private String message;

    /**
     * Creates a new BankException object.
     *
     * @param message The exception message.
     */
    public BankException(String message) {
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
