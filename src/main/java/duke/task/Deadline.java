package duke.task;

/**
 * Represents a task with a deadline. It is
 * extended from the <code>duke.task.Task</code> class.
 */
public class Deadline extends Task {
    /**
     * A string that represents the deadline of the task.
     */
    private String by;
    /**
     * Constructs a <code>duke.task.Deadline</code> object. Date and time are parsed and
     * stored in <code>dateTime</code> field if input is of "dd/MM/yyyy HHmm"
     * format.
     * @param description A string that describes the specific
     *          description of task.
     * @param by A string that specifies the deadline of the
     *          task.
     */
    public Deadline(String description, String by)
    {
        super(description);
        this.by = by;
    }
    /**
     * Returns a string pattern to the user output
     * @return A string which displays the type,
     *          description and deadline of the task.
     */
    @Override
    public String toString() {
            return "[D]" + super.printStatus() + " (by: " + super.timeFormatter(by) + ")";
    }

    /**
     * Returns a string with the following format to be read from a local file.
     * @return A string in a specific format to be read from a local file.
     */
    public String txtFormat() {
        return "D | " + (this.isDone ? "1" : "0") + " | " + this.description + " | " + super.timeFormatter(by);
    }
    /**
     * Returns a string with the following format to be stored in a local file
     * @return A string in a specific format to be stored in a local file.
     */
    public String writeTxt(){
        return "D | " + (this.isDone ? "1" : "0") + " | " + this.description + " | " + this.by;
    }

}