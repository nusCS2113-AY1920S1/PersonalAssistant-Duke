package duke.exception;

/**
 * DukeException as a child class inherits all the fields and methods of Exception parent class.
 */
public class DukeException extends Exception {

    /**
     * Constructor for the class DukeException.
     * @param errorMessage String containing the error messages
     */
    public DukeException(String errorMessage) {
        super(errorMessage);
    }
}