package duke.commons;

public class TaskNotFoundException extends DukeException {
    public TaskNotFoundException() {
        super(MessageUtil.TASK_NOT_FOUND);
    }
}
