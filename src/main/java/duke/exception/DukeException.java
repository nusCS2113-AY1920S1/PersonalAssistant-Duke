package duke.exception;

public class DukeException extends Exception {
    public DukeException(String msg) {
        super(msg);
    }

    public DukeException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
