package compal.model.tasks;

/**
 * Represents recurring task type with date and time.
 */
public class RecurringTask extends Task {

    /**
     * Constructs RecurringTask object.
     *
     * @param description Description of recurring task.
     * @param date        Starting date of recurring task.
     * @param startTime   Starting time of recurring task.
     * @param priority    priority level of task type
     * @param endTime     End time of deadline
     */
    public RecurringTask(String description, Priority priority, String date,
                         String startTime, String endTime, String symbol) {
        super(description, priority);
        super.symbol = symbol;
        super.setDate(date);
        super.setStartTime(startTime);
        super.setEndTime(endTime);
    }
}
