package tasks;

/**
 * ToDo task
 * command is "add todo [description]"
 */
public class ToDo extends Task {

    /**
     * default constructor of ToDo
     */
    public ToDo() {
    }

    /**
     * another constructor of ToDo
     *
     * @param description description of this task
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * @return string
     * how task list will print out in console
     * @Override toString() in Task
     * a method to format the output of the task list
     */
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * @return string
     * a string which will show in data file that store the task list
     * @Override dataString() in Task
     * a method to format the data list data store in file
     */
    public String dataString() {
        return "T | " + (this.isDone ? 1 : 0) + " | " + this.description + " | " + this.getPrecondition();
    }
}
