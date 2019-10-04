package seedu.duke.task;

import seedu.duke.ui.Ui;
import java.time.LocalDateTime;

import static seedu.duke.parser.DateTimeParser.convertDateTime;
import static seedu.duke.parser.DateTimeParser.toDateTimeString;

public class DoAfter extends Task {
    /**
     * LocalDateTime to store the DoAfter's date and time.
     */
    protected String after;

    /**
     * Object of the Ui class that that is used to
     * communicate errors to the user.
     */
    private Ui ui = new Ui();

    public DoAfter(String description, String after) {
        super(description);
        this.after = after;
    }

    /**
     * Overrides the toString method in Task to display task type and date time.
     *
     * @return a string with the target info.
     */
    public String toString() {
        return "[A]" + super.toString() + " (after: " + after + ")";
    }

    /**
     * Overrides the toSaveFormat function to include task type and date time.
     *
     * @return a string with pipe separated info.
     */
    public String toSaveFormat() {
        return "A|" + super.toSaveFormat() + "|" + after;
    }

    @Override
    public void markAsDone(TaskList list) {
        boolean cannotMarkAsDone = true;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).description.equals(this.after)) {
                if (list.get(i).isDone) {
                    cannotMarkAsDone = false;
                    break;
                }
            }
        }
        if (cannotMarkAsDone) {
            System.out.println("        ________________________________________________________________________");
            System.out.println("        Task cannot be marked as done! The pre-requisite task has not been done!");
            System.out.println("        ________________________________________________________________________");
            System.out.println();
        } else {
            this.isDone = true;
        }
    }

    /**
     * Checks equality with another Deadline instance.
     *
     * @param temp the instance to compare against.
     * @return true or false to the comparison.
     */
    public boolean equals(DoAfter temp) {
        if (this.description == temp.description && this.after == temp.after) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Overrides the getDateTime method in Task to obtain the DoAfter's date and time.
     *
     * @return date and time of deadline of type LocalDateTime.
     */
    @Override
    public LocalDateTime getDateTime() {
        return null;
    }

    /**
     * Overrides the setDateTime method in Task to set the deadline's date and time.
     *
     * @param localDateTime the date and time of the deadline of type LocalDateTime.
     */
    @Override
    public void setDateTime(LocalDateTime localDateTime) {

    }
}
