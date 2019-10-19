package javacake.tasks;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;
import javacake.exceptions.DukeException;

import java.util.Date;
import java.util.List;

public class Deadline extends Task {
    private String by;
    private Date dateNow;

    /**
     * Initialises the description of the task.
     * @param description String containing description
     *                    of the task inputted by user
     * @param by The details of when task is to be done
     */
    public Deadline(String description, String by) throws DukeException {
        super(description);
        this.by = by;
        taskType = TaskType.DEADLINE;
        try {
            Parser parser = new Parser();
            List<DateGroup> groups = parser.parse(by);
            dateNow = groups.get(0).getDates().get(0);
        } catch (Exception e) {
            throw new DukeException("   Date cannot be parsed: " + by);
        }
    }

    /**
     * Gets the task type in [] format and
     * its description.
     * @return String containing type and description
     */
    @Override
    public String toString() {
        return "[D]" + description + " (by: " + by + ")";
    }

    @Override
    public String getFullString() {
        return "[D][" + getStatusIcon() + "] " + description + " (by: " + by + ")";
    }

    /**
     * Method to get date of task if possible.
     * @return String containing the date of Task
     */
    @Override
    public Date getDateTime() {
        return dateNow;
    }

    /**
     * Method to get details of extra details
     * concerning the task.
     * @return String containing details of when task
     *         is to be done by
     */
    @Override
    public String getExtra() {
        return this.by;
    }

    @Override
    public void changeDate(String newDate) { 
        this.by = newDate; 
    }

}
