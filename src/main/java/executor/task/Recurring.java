package executor.task;

import duke.exception.DukeException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Recurring extends Task {
    private final TaskType taskType;

    // Initialization
    Recurring(String name) {
        super(name);
        this.taskType = TaskType.RECUR;
        this.detailDesc = "for";
        this.recordTaskDetails(name);
    }
}
