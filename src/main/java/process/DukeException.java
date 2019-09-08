package process;

/**
 * Represents an exception thrown by Duke that may be specific to its application
 */
public class DukeException extends Exception {
    private String error;
    /**
     * Creates a new DukeException of e
     * @param e The error message
     */
    public DukeException(String e) {
        this.error = e;
    }
    /**
     * Represents the exception in a String
     */
    public String toString() {
        return "DukeException[" + error + "]";
    }
    @Override
    /**
     * Retrieve the message from the error
     */
    public String getMessage() {
        return this.error;
    }
}
