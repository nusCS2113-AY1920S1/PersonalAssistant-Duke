package owlmoney.model.profile.exception;

public class ProfileException extends Exception {
    private String message;

    /**
     * Creates a new Profile Exception object.
     *
     * @param message The exception message.
     */
    public ProfileException(String message) {
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
