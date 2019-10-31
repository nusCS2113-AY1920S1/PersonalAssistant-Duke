package javacake.exceptions;

public class CakeException extends Exception {
    /**
     * Constructor for customised Exception class.
     * @param message The error message thrown by other methods
     */
    public CakeException(String message) {
        super(message);
    }
}
