package compal.model.tasks;

/**
 * Represents assignment task type with a due date.
 */
public class Deadline extends Task {

    /**
     * Constructs Deadline object.
     *
     * @param description Description of deadline.
     * @param priority    priority level of task type.
     * @param date        Due date of deadline.
     * @param endTime     End time of deadline
     */
    public Deadline(String description, Priority priority, String date, String endTime) {
        super(description, priority);
        super.symbol = "D";
        super.setMainDate(date);
        super.setEndTime(endTime);
    }
}
