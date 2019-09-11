package duke.task;

import duke.task.Task;

import java.time.LocalDateTime;

public class Deadline extends Task {

    public LocalDateTime by;

    /**
     * Constructor for duke.task.Deadline tasks
     * @param description text description of the task
     * @param by the date and time of the deadline
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + "[" + super.getStatusIcon() + "] " + super.description + " (by: " + by.toString() + ")";
    }
}