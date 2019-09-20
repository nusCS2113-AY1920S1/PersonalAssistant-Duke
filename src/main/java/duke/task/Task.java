package duke.task;
import java.time.LocalDateTime;

public abstract class Task {
    public TaskType taskType;
    private String description;
    private boolean isCompleted = false;
    enum TaskType {
        TODO, EVENT, DEADLINE
    }
    public Task(String description, TaskType taskType) {
        this.description = description;
        this.taskType = taskType;
    }

    /**
     * To make task as done.
     */
    public void markAsDone() {
        isCompleted = true;
    }

    /**
     * To check if task is done.
     * @return {@code true} task is completed
     *          {@code false} task is incomplete
     */
    public boolean isCompleted() {
        return isCompleted;
    }

    /**
     * Get the task description.
     * @return String of the task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the corresponding Icon according to whether task is completed.
     * @return If task is complete get TICK, If task is incomplete get CROSS
     */
    private String getStatusIcon() {
        return (isCompleted ? "\u2713" : "\u2718");
    }

    /**
     * Details of the task.
     * @return the expected format of String message for this task
     */
    public String toString() {
        return  "[" + getStatusIcon() + "] " + this.description;
    }

    public TaskType getTaskType() {
        return taskType;
    }
    public abstract String getSymbol();

    public abstract String writeToFile();

    public abstract String getDateTime();

    public abstract LocalDateTime getLocalDate();

    public abstract void setDate(String newDate);
}
