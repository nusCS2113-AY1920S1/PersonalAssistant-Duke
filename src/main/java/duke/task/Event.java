package duke.task;

import duke.task.Task;

public class Event extends Task {

    public String at;

    public Event(String description, String at) {
        super(description);
        this.at = at;
    }

    @Override
    public String toString() {
        return "[E]" + "[" + super.getStatusIcon() + "] " + super.description + " (at: " + at + ")";

    }
}