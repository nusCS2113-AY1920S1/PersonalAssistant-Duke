package compal.tasks;

/**
 * Represents recurring task type with date and time.
 */
public class RecurringTask extends Task {

    /**
     * Constructs RecurringTask object.
     *
     * @param description Description of recurring task.
     * @param date        Starting date of recurring task.
     * @param time        Starting time of recurring task.
     */
    public RecurringTask(String description, String date, String time) {
        super(description);
        super.symbol = "RT";
        super.setDate(date);
        super.setTime(time);
    }

    /**
     * Overrides the toString method. This provides different date for tasks
     * with identical descriptions. This is done by concatenating the string form
     * of the task's date attribute to the description.
     *
     * @return The description of the task with the date of the task.
     */
}
