package seedu.duke.task;

import seedu.duke.ui.Ui;
import java.time.LocalDateTime;

import static seedu.duke.parser.DateTimeParser.convertDateTime;
import static seedu.duke.parser.DateTimeParser.toDateTimeString;


/**
 * This task type inherits from Task. It specifies an event at a particular time.
 */
public class RangedTask extends Task {
    /**
     * LocalDateTime to store RangedTask's from date and time.
     */
    protected LocalDateTime from;

    /**
     * LocalDateTime to store RangedTask's by date and time.
     */
    protected LocalDateTime by;

    /**
     * Object of the Ui class that that is used to
     * communicate errors to the user.
     */
    private Ui ui = new Ui();

    /**
     * Initializes description and variables.
     *
     * @param description Description of RangedTask.
     * @param from starting date and time of RangedTask.
     * @param by ending date and time of RangedTask.
     */
    public RangedTask(String description, LocalDateTime from, LocalDateTime by) {
        super(description);
        this.from = from;
        this.by = by;
    }

    /**
     * Overrides the toString method in Task to display task type and date time.
     *
     * @return a string with the target info.
     */
    public String toString() {
        return "[R]" + super.toString() + " (from: " + toDateTimeString(from)
                + ", by: " + toDateTimeString(by) + ")";
    }

    /**
     * Overrides the toSaveFormat function to include task type and date time.
     *
     * @return a string with pipe separated info.
     */
    public String toSaveFormat() {
        return "R|" + super.toSaveFormat() + "|" + convertDateTime(from) + " and " + convertDateTime(by);
    }

    /**
     * Checks equality with another Event instance.
     *
     * @param temp the instance to compare against.
     * @return true or false to the comparison.
     */
    public boolean equals(RangedTask temp) {
        if (this.description == temp.description && this.by == temp.by
                && this.from == temp.from) {
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