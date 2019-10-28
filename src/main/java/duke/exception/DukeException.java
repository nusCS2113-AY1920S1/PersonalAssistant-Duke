package duke.exception;

/**
 * Represents an {@link Exception} class used to throw exceptions from the Duke class.
 */
public class DukeException extends Exception {

    public DukeException(String message) {
        super("\t ☹ OOPS!!! " + message+". \n\tYou can type: \n\t'template' to see the format of the commands, \n\t'back' to see all your options, \n\t'q' to exit");
    }
}
