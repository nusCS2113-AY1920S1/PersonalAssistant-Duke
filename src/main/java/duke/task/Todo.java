package duke.task;

/**
 * Represents a task without a specific time. This class
 * extends from the Task class.
 */
public class Todo extends Task {

    /**
     * Constructs a Todo object.
     *
     * @param description A string of the task description.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns a string pattern to the user output
     *
     * @return A string which displays the type,
     * description and deadline of the task.
     */
    @Override
    public String toString() {
        return "[T]" + super.printStatus();
    }

    /**
     * Returns a string with the following format to be stored in a local file
     *
     * @return A string in a specific format to be stored in a local file.
     */
    public String writeTxt() {
        return "T | "
                + (this.isDone() ? "1" : "0")
                + " | "
                + this.description
                + " | "
                + this.isRecurring;
    }

}