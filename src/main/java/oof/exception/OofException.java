package oof.exception;

/**
 * Represents a newly specified Exception.
 */
public class OofException extends Exception {

    /**
     * Constructor for OofException.
     *
     * @param error Error message.
     */
    public OofException(String error) {
        super(error);
    }
}