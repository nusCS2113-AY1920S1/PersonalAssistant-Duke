package duke.tasks;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;
import duke.DukeException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Event extends Task {
    private String at;
    private Date dateNow;

    /**
     * Initialises the description of the task.
     * @param description String containing description
     *                    of the task inputted by user
     * @param at The details of when task is to be done
     */
    public Event(String description, String at) throws DukeException {
        super(description);
        this.at = at;
        taskType = TaskType.EVENT;
        try {
            Parser parser = new Parser();
            List<DateGroup> groups = parser.parse(at);
            dateNow = groups.get(0).getDates().get(0);
        } catch (Exception e) {
            throw new DukeException("   Date cannot be parsed: " + at);
        }
    }

    /**
     * Gets the task type in [] format and
     * its description.
     * @return String containing type and description
     */
    @Override
    public String toString() {
        return "[E]" + description + " (at: " + at + ")";
    }

    /**
     * Method to get date of task if possible.
     * @return String containing the date of Task
     */
    @Override
    public Date getDateTime() {
        return dateNow;
    }

    @Override
    public String getFullString() {
        return "[E][" + getStatusIcon() + "] " + description + " (at: " + at + ")";
    }

    /**
     * Method to get details of extra details
     * concerning the task.
     * @return String containing details of when task
     *         is to be done at
     */
    @Override
    public String getExtra() {
        return this.at;
    }
}
