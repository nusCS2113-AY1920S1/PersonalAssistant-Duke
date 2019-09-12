package task;

/**
 * Abstract class to represent task
 */
public abstract class Task {
    protected String description; //User input
    protected boolean isDone; //To check if the task is completed

    /**
     * Constructor for class Task
     * @param description String containing the description of the task
     */
    public Task(String description) {
        this.description = description; //user input string
        this.isDone = false; //initially marked as not completed
    }

    /**
     * Get the description of the different tasks
     * @return String containing the description of the task
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the task as done after completion
     */
    public void markAsDone(){
        isDone = true;
    } //marked as completed when done

//    public String getStatusIcon() {
//        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
//    }

    /**
     * Get the current status of the task
     * @return String containing plus symbol if completed and minus symbol otherwise
     */
    public String getStatusIcon() {
        return (isDone ? "+" : "-"); //return plus or minus symbols
    }

    /**
     * Converts user input command to a standardized format to store in file
     * @return String containing the standardized format
     */
    public String toSaveString(){
        return " | " + getStatusIcon() + " | " + description;
    }

    /**
     * Converts user input command to a standardized format in taskList
     * @return String containing the standardized format
     */
    public String toString(){
        return "[" + getStatusIcon() + "] " + description;
    }
}