package javacake.unused;

import javacake.tasks.Task;

import java.util.Date;

public class ToDo extends Task {

    /**
     * Initialises the description of the task.
     * @param description String containing description
     *                    of the task inputted by user
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Gets the task type in [] format and
     * its description.
     * @return String containing type and description
     */
    @Override
    public String toString() {
        return "[T]" + description;
    }

    @Override
    public String getFullString() {
        return "[T][" + getStatusIcon() + "] " + description;
    }

    /**
     * No extra in this task type.
     * @return Null String
     */
    @Override
    public String getExtra() {
        return null;
    }

    /**
     * No date in this task type.
     * @return Null String
     */
    @Override
    public Date getDateTime() {
        return null;
    }

    @Override
    public void changeDate(String newDate) {
        //do nothing here.
    }
}
