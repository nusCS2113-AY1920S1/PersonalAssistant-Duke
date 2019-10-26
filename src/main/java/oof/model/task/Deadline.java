package oof.model.task;

/**
 * Represents a Task object. A Deadline object is a type of Task.
 */
public class Deadline extends Task {

    protected String by;

    /**
     * Constructor for Deadline.
     *
     * @param description Details of the Task.
     * @param by          Due date and time of the Deadline.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    public String getBy() {
        return by;
    }

    /**
     * Returns a string from Deadline task object.
     *
     * @param task Deadline task object.
     * @return String obtained from Deadline task object.
     */
    public String deadlineToStorageString(Deadline task) {
        String dateTime = task.getBy();
        String date = dateTime.split(" ")[DATE];
        String time = dateTime.split(" ")[TIME];
        return "D" + DELIMITER + task.getStatusIcon() + DELIMITER + task.getDescription() + DELIMITER + date
                + DELIMITER + time + DELIMITER + DELIMITER;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}