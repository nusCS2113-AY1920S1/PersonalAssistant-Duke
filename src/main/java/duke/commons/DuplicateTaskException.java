package duke.commons;

public class DuplicateTaskException extends DukeException {
    public DuplicateTaskException() {
        super(MessageUtil.DUPLICATED_TASK);
    }
}
