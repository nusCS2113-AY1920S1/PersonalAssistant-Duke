package duke.task;

import duke.DateFormatter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Event extends Task {
    private final String SYMBOL = "[E]";
    private String at;

    /**
     * Constructor for Event task.
     * @param description The event name
     * @param date The dateTime of the event
     */
    public Event(String description, String date) {
        super(description.trim(), Task.TaskType.EVENT);
        this.at = date;
    }

    /**
     * Details of the task.
     * @return return the expected format of String message for this task
     */
    @Override
    public String toString() {
        return this.SYMBOL + super.toString() + " (at: " + new DateFormatter(this.at).getDateTime() + ")\n";
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
        return String.format("E | %d | %s | %s",  (isCompleted() ? 1 : 0), this.getDescription(), this.at);
    }

    public LocalDateTime getLocalDate() {
        LocalDateTime t = new DateFormatter(this.at).toLocalDateTime(this.at);
        return t;
    }
    @Override
    /**
     * Function to return at string.
     * @return at string, which represents the date and time of 'at' field.
     */
    public String getDateTime() {
        return this.at;
    }

    public void setDate(String newDateTime) {
        this.at = newDateTime;
    }
}
