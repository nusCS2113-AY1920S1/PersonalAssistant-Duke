package tasks;

/**
 * ToDo task
 * command is "add todo [description]"
 */
public class ToDo extends Task {

    /**
     * default constructor of ToDo
     */
    public ToDo() {}

    /**
     * another constructor of ToDo
     * @param description
     *                  description of this task
     */
    public ToDo(String description){
        super(description);
    }

    /**
     * @Override toString() in Task
     * a method to format the output of the task list
     * @return string
     *              how task list will print out in console
     */
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * @Override dataString() in Task
     * a method to format the data list data store in file
     * @return string
     *              a string which will show in data file that store the task list
     */
    public String dataString() {
        return "T | " + (this.isDone ? 1 : 0) + " | " + this.description + " | " + this.getPrecondition();
    }
}
