package duke.exceptions;

import java.lang.Exception;

/**
 * Represents duke.exceptions specific to Duke
 */
public class DukeException extends Exception {
    public DukeException (String message) {
        super(message);
    }
}
