package duke.Task;


import java.util.Date;

public class Event extends Item {
    /**
     * "at" is the date-time allocated to the task to be completed by.
     */
    private Date at;

    /**
     * Constructor method for the Event class.
     *
     * @param info   This is the information about the task being added
     * @param status This determines if whether the Item
     *               added is completed or uncompleted
     * @param date   the time to finish the task by
     */
    public Event(final String info, final Boolean status, final String date) {
        super(info, status);
        super.setType("E");
        this.at = TaskList.dateConvert(date);
    }

    /**
     * This function takes the "at" data in the Event class.
     * Converts it into the string output format.
     * Format: 2nd of December 2019, 2pm.
     *
     * @return New string format
     */
    @Override
    public String getDate() {
        return TaskList.dateToStringFormat(at);
    }

    /**
     * Function gets the unformatted date of at.
     *
     * @return at
     */
    @Override
    public Date getRawDate() {
        return this.at;
    }

    /**
     * This function gets the type, information, and date of the task.
     *
     * @return String phrase with the type, info and date
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + getDate() + ")";
    }
}
