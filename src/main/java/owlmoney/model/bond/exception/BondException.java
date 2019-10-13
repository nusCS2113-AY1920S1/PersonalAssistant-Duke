package owlmoney.model.bond.exception;

public class BondException extends Exception {
    private String message;

    /**
     * Creates a new BondException object.
     *
     * @param message The exception message.
     */
    public BondException(String message) {
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
