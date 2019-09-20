package duke;

import java.lang.Exception;

/**
 * Represents exceptions specific to Duke
 */
public class DukeException extends Exception {
    public DukeException (String message) {
        super(message);
    }
}
