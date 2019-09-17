package duke.exception;

import duke.task.Task;

public class DukeTaskClashException extends DukeException {
    DukeTaskClashException(Task task) {
        super("☹ OOPS!!! This task clashed with " + task.getDescription() + ".\n"
                + "Please try another time slot.");
    }
}
