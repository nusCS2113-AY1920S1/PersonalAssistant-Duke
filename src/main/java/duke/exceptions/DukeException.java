package duke.exceptions;

public class DukeException extends Exception {
    public DukeException() {
        super();
    }

    public DukeException(String message) {
        super(message);
    }

    /**
     * Over-writing the exception class GetMessage method,
     * so that other sub-classes would have this message.
     * @return Starting portion indicating a DukeException.
     */
    @Override
    public String getMessage() {
        return "DukeException: ";
    }
}
