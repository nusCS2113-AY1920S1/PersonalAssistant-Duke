package Duke.Tasks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Deadline extends Task {
    private String by;
    private Date dateNow;

    /**
     * Initialises the description of the task
     * @param description String containing description
     *                    of the task inputted by user
     * @param by The details of when task is to be done
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HHmm");
        sdf.setLenient(false);
        try {
            dateNow = sdf.parse(by);
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
        return "[D]" + description + " (by: " + by + ")";
    }

    @Override
    public String getFullString() {
        return "[D][" + getStatusIcon() + "] " + description + " (by: " + by + ")";
    }

    /**
     * Method to get date of task if possible
     * @return String containing the date of Task
     */
    @Override
    public String getDateTime() {
        return dateNow.toString();
    }

    /**
     * Method to get details of extra details
     * concerning the task
     * @return String containing details of when task
     *         is to be done by
     */
    @Override
    public String getExtra() {
        return this.by;
    }

}
