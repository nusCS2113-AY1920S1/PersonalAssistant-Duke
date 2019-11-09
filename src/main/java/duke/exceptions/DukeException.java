package duke.exceptions;


import java.text.ParseException;

/**
 * Represents duke.exceptions specific to Coach Manager.
 */
public class DukeException extends Exception {
    /**
     * Constructor.
     *
     * @param message Error message.
     */
    public void errMessage(final String message) {
        System.err.println("Oops something went wrong!");
        System.err.println(message);
    }


}
