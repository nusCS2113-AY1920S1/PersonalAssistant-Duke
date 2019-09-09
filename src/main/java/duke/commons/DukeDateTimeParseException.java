package duke.commons;

public class DukeDateTimeParseException extends DukeException {
    public DukeDateTimeParseException() {
        super(Message.INVALID_FORMAT);
    }
}
