package duke.Task;

import java.util.Date;

/**
 * Creates a class for the Deadline task.
 */
public class Deadline extends Item {
    /**
     * "by" is the date-time allocated to the task to be completed by.
     */
    private Date by;

    /**
     * Constructor method for the Deadline class.
     *
     * @param info   This parameter is the info of the Item created
     * @param status The status of the Item created, either true or false
     * @param date     the time to finish the task by
     */
    public Deadline(final String info,
                    final Boolean status,
                    final String date) {
        super(info, status);
        super.setType("D");
        this.by = TaskList.dateConvert(date);
    }

    /**
     * This function takes the "by" data in the Event class.
     * Converts it into the string output format.
     * Format: 2nd of December 2019, 2pm.
     *
     * @return New string format
     */
    @Override
    public String getDate() {
        return TaskList.dateToStringFormat(by);
    }


    /**
     * Function gets the unformatted date of by.
     *
     * @return by
     */
    @Override
    public Date getRawDate() {
        return this.by;
    }

    /**
     * Gets all the info of the deadline.
     *
     * @return A string phrase containing all the details of the deadline object
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + getDate() + ")";
    }

}
