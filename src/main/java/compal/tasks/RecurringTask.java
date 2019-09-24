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
}
