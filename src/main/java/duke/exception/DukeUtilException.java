package duke.exception;

/**
 * Exception thrown by a static function, that needs some extra context to be properly displayed. Meant to be caught
 * and re-thrown.
 */
public class DukeUtilException extends DukeException {

    public DukeUtilException(String msg) {
        super(msg);
    }
}
