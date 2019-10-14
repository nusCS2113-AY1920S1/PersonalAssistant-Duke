package owlmoney.model.card.exception;

public class CardException extends Exception {
    private String message;

    /**
     * Creates a new CardException object.
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
