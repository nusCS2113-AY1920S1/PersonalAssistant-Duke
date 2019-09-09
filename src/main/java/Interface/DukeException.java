package Interface;
/**
 * Represents the exception specifically catered to
 * Duke program should any error occur
 */
public class DukeException extends Exception {
    public DukeException(String message) {
        super(message);
    }
}
