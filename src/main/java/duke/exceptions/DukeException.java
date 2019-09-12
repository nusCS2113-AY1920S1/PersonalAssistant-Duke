package duke.exceptions;

public abstract class DukeException extends Exception {
    public DukeException(String errorMsg) {
        super(errorMsg);
    }
}
