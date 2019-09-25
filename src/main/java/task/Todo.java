package task;

public class Todo extends Task {
    protected String type = "T";

    /**
     * Task initialization with string as input.
     *
     * @param description String containing description information
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Overloaded constructor which reads in a task from file.
     *
     * @param bool        String which should be 1 or 0, describing if the Task is done or not
     * @param description String which contains description of Task
     */
    public Todo(String bool, String description) {
        super(description);
        this.isDone = (1 == Integer.parseInt(bool));
    }

    /**
     * Returns Task in print friendly format.
     *
     * @return String which contains Task Type icon, status and Description and DueDate if any
     */
    @Override
    public String toList() {
        return "[T][" + this.getStatusIcon() + "] " + this.getDescription();
    }

    /**
     * Returns type of Task.
     *
     * @return String consisting of a single Letter (for now)
     */
    @Override
    public String getType() {
        return "T";
    }
}

