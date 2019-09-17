package duke.task;

import duke.core.DukeException;

/**
 * Represents a task with a deadline. It is
 * extended from the Task class.
 */
public class Deadline extends Task {
    /**
     * A string that represents the deadline of the task.
     */
    private String by;

    /**
     * Constructs a Deadline object. Date and time are parsed and
     * stored in dateTime field if input is of "dd/MM/yyyy HHmm"
     * format.
     *
     * @param description A string that describes the specific
     *                    description of task.
     * @param by          A string that specifies the deadline of the
     *                    task.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
        super.updateLocalDateTime(by);
    }

    /**
     * Returns a string pattern to the user output
     *
     * @return A string which displays the type,
     * description and deadline of the task.
     */
    @Override
    public String toString() {
        return "[D]" + super.printStatus() + " (by: " + super.timeFormatter(by) + ")";
    }

    /**
     * Returns a string with the following format to be stored in a local file
     *
     * @return A string in a specific format to be stored in a local file.
     */
    public String writeTxt() {
        return "D | "
                + (this.isDone ? "1" : "0")
                + " | "
                + this.description
                + " | "
                + this.by
                + " | "
                + this.frequency;
    }

}