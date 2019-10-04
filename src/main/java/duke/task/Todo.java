package duke.task;

import java.util.Date;

/**
 * Represent a todo task and inherits all the fields and methods of Task parent class.
 */
public class Todo extends Task {

    /**
     * Constructor for class Todo.
     * @param description String containing the description of the task
     */
    public Todo(String description) {
        super(description);//super class constructor call to the Task(description) constructor
    }

    /**
     * Converts user input command to a standardized format to store in file.
     * @return String containing the standardized format
     */
    @Override
    public String toSaveString() {
        return "T" + super.toSaveString();
    }

    /**
     * Converts user input command to a standardized format in taskList.
     * @return String containing the standardized format
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    public Date getDateTime() {
        return null;
    }
}