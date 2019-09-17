package duke.task;

import duke.core.DateTimeParser;
import duke.core.DukeException;

/**
 * Represents a task with a deadline. It is
 * extended from the Task class.
 */
public class Deadline extends Task {
    /**
     * A string that represents the deadline of the task.
     */
    private String dateTime;
    private String dateTimeEnglish;

    /**
     * Constructs a Deadline object. Date and time are parsed and
     * stored in dateTime field if input is of "dd/MM/yyyy HHmm"
     * format.
     *
     * @param description A string that describes the specific
     *                    description of task.
     * @param dateTime          A string that specifies the deadline of the
     *                    task.
     */
    public Deadline(String description, String dateTime) throws DukeException {
        super(description);
        super.updateLocalDateTime(dateTime);
        this.dateTime = dateTime;
        dateTimeEnglish = DateTimeParser.convertToEnglishDateTime(dateTime);
    }

    /**
     * Returns a string pattern to the user output
     *
     * @return A string which displays the type,
     * description and deadline of the task.
     */
    @Override
    public String toString() {
        return "[D]" + super.printStatus() + " (by: " + dateTimeEnglish + ")";
    }

    /**
     * Returns a string with the following format to be stored in a local file
     *
     * @return A string in a specific format to be stored in a local file.
     */
    public String writeTxt() {
        return "D | "
                + (isDone() ? "1" : "0")
                + " | "
                + getDescription()
                + " | "
                + dateTime
                + " | "
                + this.isRecurring;
    }

}