package duke.task;

import duke.DateFormatter;

public class Event extends Task {
    private final String SYMBOL = "[E]";
    private String at;

    /**
     * Constructor for Event task.
     * @param description The event name
     * @param date The dateTime of the event
     */
    public Event(String description, String date) {
        super(description.trim());
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

    @Override
    public String getDateTime() {
        return this.at;
    }
}
