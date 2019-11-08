package task;

import exception.DukeException;
import parser.Parser;

public class Todo extends Task {
    protected String type = "T";

    /**
     * Task initialization with string as input.
     *
     * @param description String containing description information
     */
    public Todo(String description) throws DukeException{
        if (description.contains("/priority")){
            String[] splitPriority = description.split(Parser.priority);
            if (splitPriority.length < 2){
                throw new DukeException("Please enter a priority level!");
            }
            if (!splitPriority[1].toLowerCase().matches("very high|high|normal|low")){
                throw new DukeException("Please ensure that priority level is either Very High, High, Normal, or Low");
            }
            this.isDone = false;
            this.description = splitPriority[0];
            this.userDefinedPriority = Parser.userPriorityMap.get(splitPriority[1].toLowerCase());
            this.calculatePriorityScore();
        }
        else {
            this.description = description;
            this.isDone = false;
            this.userDefinedPriority = "Normal";
//            this.calculatePriorityScore();
        }
    }

    /**
     * Overloaded constructor which reads in a task from file.
     *
     * @param bool        String which should be 1 or 0, describing if the Task is done or not
     * @param description String which contains description of Task
     */
    public Todo(String bool, String description, String priority) {
        super(description);
        this.isDone = (1 == Integer.parseInt(bool));
        this.userDefinedPriority = (priority);
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

