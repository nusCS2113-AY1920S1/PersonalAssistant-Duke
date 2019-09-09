package Duke.Task;

public abstract class Task {
    private String description;
    private boolean isCompleted = false;

    public Task(String description) {
        this.description = description;
    }

    /**
     * To make task as done
     */
    public void markAsDone() {
        isCompleted = true;
    }

    /**
     * To check if task is done
     * @return {@code true} Task is completed
     *          {@code false} Task is incomplete
     */
    public boolean isCompleted() {
        return isCompleted;
    }

    /**
     * Get the task description
     * @return String of the task description
     */
    public String getDescription(){
        return description;
    }

    /**
     * Get the corresponding Icon according to whether task is completed
     * @return If task is complete get TICK, If task is incomplete get CROSS
     */
    private String getStatusIcon(){
        return (isCompleted ? "\u2713" : "\u2718");
    }

    /**
     * @return the expected format of String message for this task
     */
    public String toString(){
        return  "[" + getStatusIcon() + "] " + this.description;
    }


    public abstract String getSymbol();

    public abstract String writeToFile();
}
