package Duke.Task;

public class Deadline extends Task{
    public final String SYMBOL = "[D]";
    protected String by;

    /**
     * Constructor for Deadline Task
     * @param description The deadline's task name
     * @param by The dateTime of the deadline
     */
    public Deadline(String description, String by){
        super(description);
        this.by = by;
    }

    /**
     * @return the expected format of String message for this task
     */
    @Override
    public String toString() {
        return SYMBOL + super.toString() + " (by: " + this.by + ")\n";
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
}
