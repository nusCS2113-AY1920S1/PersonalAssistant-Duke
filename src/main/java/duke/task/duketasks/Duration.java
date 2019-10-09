package duke.task.duketasks;

import java.util.Date;

/**
 * Represent a fixed duration task and inherits all the fields and methods of Task parent class.
 */
public class Duration extends Task {

    protected String need; // private instance variables

    /**
     * Constructor for class Duration.
     * @param description String containing the description of the task
     * @param need        String containing time needed for the task
     */
    public Duration(String description, String need) {
        super(description);
        this.need = need;
    }

    /**
     * Converts user input command to a standardized format to store in file.
     * @return String containing the standardized format
     */
    @Override
    public String toSaveString() {
        return "F" + super.toSaveString() + " | " + need;
    }

    /**
     * Converts user input command to a standardized format in taskList.
     * @return String containing the standardized format
     */
    @Override
    public String toString() {
        return "[F]" + super.toString() + " (need: " + need + ")";
    }

    public Date getDateTime() {
        return null;
    }
}