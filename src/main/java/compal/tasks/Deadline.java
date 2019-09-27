package compal.tasks;

/**
 * Represents assignment task type with a due date.
 */
public class Deadline extends Task {

    /**
     * Constructs Deadline object.
     *
     * @param description Description of deadline.
     * @param date        Due date of deadline.
     */
    public Deadline(String description, Priority priority, String date) {
        super(description, priority);
        super.symbol = "D";
        super.setDate(date);
    }
}
