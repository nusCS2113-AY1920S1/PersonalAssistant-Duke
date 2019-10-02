package duke.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that is to be done after a specific date or task.
 */
public class DoAfter extends Task {

    protected String afterTask;
    protected LocalDateTime afterDate;

    /**
     * Takes in the description, either a date to be completed after or a task.
     * @param description    String holding the description of the task
     * @param taskToComplete Task to complete first.
     */
    public DoAfter(String description, LocalDateTime afterDate, String taskToComplete) {
        super(description);
        this.afterTask = taskToComplete;
        this.afterDate = afterDate;
    }

    @Override
    public String toString() {
        if (this.afterDate != null) {
            return "[A]" + super.toString() + " (after: " + dateToString(this.afterDate) + ")";
        } else {
            return "[A]" + super.toString() + " (after: " + this.afterTask + ")";
        }
    }

    /**
     * Converts the input LocalDateTime to printable format in String.
     * @param dateTime LocalDateTime object to be converted to String
     * @return String format of the LocalDateTime
     */
    private String dateToString(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        return dateTime.format(formatter);
    }
}