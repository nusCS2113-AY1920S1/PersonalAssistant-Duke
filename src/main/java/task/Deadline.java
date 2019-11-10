package task;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Task containing information of a deadline.
 */

public class Deadline extends Task implements Serializable {
    //protected String by;
    private static SimpleDateFormat dataformat = new SimpleDateFormat("dd-MM-yyyy");

    /**
     * Creates a Deadline instance and initialises the required attributes.
     * @param description Description of the deadline.
     */
    public Deadline(String description, Date by) {
        super(description);
        this.date = by;
        this.type = "D";
    }


    /**
     * Returns a string status of the Deadline task.
     * @return The task's status icon, description and deadline.
     */
    @Override
    public String giveTask() {
        return  super.giveTask() + "(by: " + dataformat.format(this.date) + ")";
    }
}
