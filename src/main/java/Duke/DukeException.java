package Duke;

public class DukeException extends Exception {
    /**
     * Constructor for customised Exception class
     * @param message The error message thrown by other methods
     */
    public DukeException(String message) {
        super(message);
    }
}
