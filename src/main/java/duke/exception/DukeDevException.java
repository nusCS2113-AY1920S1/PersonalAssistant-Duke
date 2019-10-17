package duke.exception;

/**
 * An Exception caused by an invalid state, usually because of developers doing something they shouldn't.
 */
public class DukeDevException extends DukeException {

    public DukeDevException(String msg) {
        super(msg + System.lineSeparator() + System.lineSeparator()
                + "This shouldn't happen unless Dr. Duke has been modified; try reinstalling?");
    }
}
