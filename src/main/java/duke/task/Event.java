package duke.task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

class Event extends Task {
    // Initialization
    Event(String name) {
        super(name);
        this.taskType = TaskType.EVENT;
        this.recordTaskDetails(name);
    }
}
