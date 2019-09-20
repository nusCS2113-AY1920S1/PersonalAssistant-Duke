package duke.task;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Todo extends Task {
    private final String SYMBOL = "[T]";

    /**
     * Constructor for Todo task.
     * @param description The deadline's task name
     */
    public Todo(String description) {
        super(description.trim(), Task.TaskType.TODO);
    }

    /**
     * Details of the task.
     * @return the expected format of String message for this task
     */
    @Override
    public String toString() {
        return SYMBOL + super.toString() + "\n";
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
        return String.format("T | %d | %s",  (isCompleted() ? 1 : 0), this.getDescription());
    }

    // not applicable for Todo.
    public String getDateTime() {
        return null;
    }

    public LocalDateTime getLocalDate() {
        return null;
    }


    // does not apply for Todo
    @Override
    public void setDate(String newDate) {
    }
}
