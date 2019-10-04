package seedu.hustler.task;

import seedu.hustler.ui.Ui;
import java.time.LocalDateTime;

import static seedu.hustler.parser.DateTimeParser.convertDateTime;
import static seedu.hustler.parser.DateTimeParser.toDateTimeString;

/**
 * This task type inherits from Task.
 * It specifies a day before which a task should be completed.
 */
public class Deadline extends Task {
    /**
     * LocalDateTime to store the deadline's date and time.
     */
    protected LocalDateTime by;

    /**
     * Ui instance to communicate errors.
     */
    private Ui ui = new Ui();

    /**
     * Initializes description and by.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }


    /**
     * Overrides the toString method in Task to display task type and date time.
     *
     * @return a string with the target info.
     */
    public String toString() {
        return "[D]" + super.toString() + " (by: " + toDateTimeString(this.by) + ")";
    }

    /**
     * Overrides the toSaveFormat function to include task type and date time.
     *
     * @return a string with pipe separated info.
     */
    public String toSaveFormat() {
        return "D|" + super.toSaveFormat() + "|" + convertDateTime(by);
    }

    /**
     * Checks equality with another Deadline instance.
     *
     * @param temp the instance to compare against.
     * @return true or false to the comparison.
     */
    public boolean equals(Deadline temp) {
        if (this.description == temp.description && this.by == temp.by) {
            return true;
        }
        return false;
    }

    @Override
    public LocalDateTime getDateTime() {
        return this.by;
    }

    @Override
    public void setDateTime(LocalDateTime localDateTime) {
        this.by = localDateTime;
    }

}