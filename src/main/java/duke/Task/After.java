package duke.Task;

import java.util.Date;

public class After extends Item {
    /**
     * "after" is the date allocated to the task to be completed by.
     */
    private Date after;

    /**
     * Constructor method for the after class.
     *
     * @param info   the description of the task
     * @param status whether the task is complete or not
     * @param date  the time to finish the task by
     */
    public After(final String info, final Boolean status, final String date) {
        super(info, status);
        super.setType("A");
        this.after = TaskList.dateConvert(date);
    }

    /**
     * This function takes the "after" data in the After class.
     * Converts it into the string output format.
     * Format: 2nd of December 2019, 2pm.
     *
     * @return New string format
     */
    @Override
    public String getDate() {
        return TaskList.dateToStringFormat(after);
    }

    /**
     * Function gets the unformatted date of after.
     *
     * @return after
     */
    @Override
    public Date getRawDate() {
        return this.after;
    }

    /**
     * Overrides the toString function in Item.
     * Extends function to include After class info.
     *
     * @return string of data with After class info
     */
    @Override
    public String toString() {
        return "[A]" + super.toString() + " (after: " + getDate() + ")";
    }
}
