package duke.task;
import java.time.LocalDateTime;
import duke.DateFormatter;

public class Deadline extends Task {
    private final String SYMBOL = "[D]";
    private String by;

    /**
     * Constructor for Deadline task.
     * @param description The deadline's task name
     * @param by The dateTime of the deadline
     */
    public Deadline(String description, String by) {
        super(description.trim(), Task.TaskType.DEADLINE);
        this.by = by;
    }

    /**
     * Details of the task.
     * @return the expected format of String message for this task
     */
    @Override
    public String toString() {
        return SYMBOL + super.toString() + " (by: " + new DateFormatter(this.by).getDateTime() + ")\n";
    }

    /**
     * Used when using storage.write
     * For reference to store the correct task type for each task
     * @return The Symbol to reference to their task type
     */
    @Override
    public String getSymbol() {
        return this.SYMBOL;
    }

    /**
     * The String format to be written into the duke.txt File for each task
     * @return String format for task to be written into the duke.txt File
     */
    @Override
    public String writeToFile() {
        return String.format("D | %d | %s | %s",  (isCompleted() ? 1 : 0), this.getDescription(), this.by);
    }

    @Override
    /**
     * Function to return at string.
     * @return at string, which represents the date
     */
    public String getDateTime() {
        return this.by;
    }

    @Override
    public LocalDateTime getLocalDate() {
        LocalDateTime t = new DateFormatter(this.by).convertToLocalDate(this.by);
        return t;
    }

    public void setDate(String newDateTime) {
        this.by = newDateTime;
    }
}
