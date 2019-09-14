package Duke.Tasks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Event extends Task {
    private String at;
    private Date dateNow;

    /**
     * Initialises the description of the task
     * @param description String containing description
     *                    of the task inputted by user
     * @param at The details of when task is to be done
     */
    public Event(String description, String at) {
        super(description);
        this.at = at;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HHmm");
        sdf.setLenient(false);
        try {
            dateNow = sdf.parse(at);
        } catch (ParseException e) {
            //throw new DukeException("Tasks.Task does not have dd/MM/yyyy HHmm date-time format!");
        }
    }

    /**
     * Gets the task type in [] format and
     * its description
     * @return String containing type and description
     */
    @Override
    public String toString() {
        return "[E]" + description + " (at: " + at + ")";
    }

    /**
     * Method to get date of task if possible
     * @return String containing the date of Task
     */
    @Override
    public String getDateTime() {
        return dateNow.toString();
    }

    @Override
    public String getFullString() {
        return "[E][" + getStatusIcon() + "] " + description + " (at: " + at + ")";
    }

    /**
     * Method to get details of extra details
     * concerning the task
     * @return String containing details of when task
     *         is to be done at
     */
    @Override
    public String getExtra() {
        return this.at;
    }
}
