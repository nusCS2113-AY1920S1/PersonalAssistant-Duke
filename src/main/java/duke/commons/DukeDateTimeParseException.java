package duke.commons;

public class DukeDateTimeParseException extends DukeException {
    public DukeDateTimeParseException() {
        super(MessageUtil.INVALID_FORMAT);
    }
}
