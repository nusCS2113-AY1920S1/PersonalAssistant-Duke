package seedu.duke.task;

import seedu.duke.ui.Ui;
import java.time.LocalDateTime;

/**
 * This task type inherits from Task. 
 * It specifies a task that takes a fixed amount of time but does not have a fixed
 * start/end time.
 *
 * Users can enter such a fixed duration task by typing the following command:
 * "fixedtask <task descrption> /needs <duration>"
 *
 * Example:
 * "fixedtask read report /needs about 4 days"
 */
public class FixedDurationTask extends Task {
    /**
     * LocalDateTime to store the FixdDurationTask's date and time.
     */
    protected LocalDateTime localDateTime;

    /**
     * String storing the duration required by the FixedDurationTask to complete.
     */
    protected String needs;

    /**
     * Ui instance to communicate errors.
     */
    private Ui ui = new Ui();

    /**
     * Initializes description and needs.
     */
    public FixedDurationTask(String description, String needs) {
        super(description);
        this.needs = needs;
    }

    /**
     * Overrides the toString method in Task to display task type and required time.
     *
     * @return a string with the target info.
     */
    public String toString() {
        return "[F]" + super.toString() + " (needs: " + this.needs + ")";
    }

    /**
     * Overrides the toSaveFormat function to include task type and required time.
     *
     * @return a string with pipe separated info.
     */
    public String toSaveFormat() {
        return "F|" + super.toSaveFormat() + "|" + this.needs;
    }

    /**
     * Checks equality with another FixedDurationTask instance.
     *
     * @param temp the instance to compare against.
     * @return true or false to the comparison.
     */
    public boolean equals(FixedDurationTask temp) {
        if (this.description == temp.description && this.needs == temp.needs) {
            return true;
        }
        return false;
    }

    @Override
    public LocalDateTime getDateTime() {
        return this.localDateTime;
    }

    @Override
    public void setDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
