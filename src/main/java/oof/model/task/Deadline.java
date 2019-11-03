package oof.model.task;

/**
 * Represents a Task object. A Deadline object is a type of Task.
 */
public class Deadline extends Task {

    String deadlineDateTime;

    /**
     * Constructor for Deadline.
     *
     * @param description Details of the Task.
     * @param deadlineDateTime Due date and time of the Deadline.
     */
    public Deadline(String description, String deadlineDateTime) {
        super(description);
        this.deadlineDateTime = deadlineDateTime;
    }

    public String getDeadlineDateTime() {
        return deadlineDateTime;
    }

    /**
     * Converts a task object to string format for storage.
     * @return Task object in string format for storage.
     */
    @Override
    public String toStorageString() {
        String date = deadlineDateTime.split(" ")[INDEX_DATE];
        String time = deadlineDateTime.split(" ")[INDEX_TIME];
        return "DEADLINE" + DELIMITER + getStatusIcon() + DELIMITER + description + DELIMITER + date
                + DELIMITER + time + DELIMITER + DELIMITER + DELIMITER;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + deadlineDateTime + ")";
    }
}