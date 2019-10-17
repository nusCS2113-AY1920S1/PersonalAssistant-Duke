package common;

/**
 * A class for exceptions specific to AlphaNUS.
 */

public class AlphaNUSException extends Exception {
    /**
     * Creates an instance of an exception specific to AlphaNUS.
     * @param message Message describing the exception thrown.
     */
    public AlphaNUSException(String message) {
        super(message);
    }
}
