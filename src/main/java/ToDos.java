/**
 * Represents a task without a specific time. This class
 * extends from the Task class.
 */
public class ToDos extends Task {
    protected boolean isToDo;

    /**
     *  Constructs a <code>ToDo</code> object.
     * @param description A string of the task description.
     */
    public ToDos(String description) {
        super(description);
        this.isToDo = true;
    }
    /**
     * Returns a string pattern to the user output
     * @return A string which displays the type,
     *          description and deadline of the task.
     */
    @Override
    public String toString() {
        return "[T]" + super.printStatus();
    }

    /**
     * Returns a string with the following format to be read from a local file.
     * @return A string in a specific format to be read from a local file.
     */
    public String txtFormat() {
        return "T | " + (this.isDone ? "1" : "0") + " | " + this.description;
    }

    /**
     * Returns a string with the following format to be stored in a local file
     * @return A string in a specific format to be stored in a local file.
     */
    public String writeTxt(){
        return "T | " + (this.isDone ? "1" : "0") + " | " + this.description;
    }

}