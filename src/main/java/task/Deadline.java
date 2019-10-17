package task;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Task containing information of a deadline.
 */

public class Deadline extends Task implements Serializable {
    //protected String by;
    private static SimpleDateFormat dataformat = new SimpleDateFormat("dd/MM/yyyy HHmm");

    /**
     * Creates a Deadline instance and initialises the required attributes.
     * @param description Description of the deadline.
     */
    public Deadline(String description) {
        super(description);
        this.by = null;
        this.type = "D";
        this.inVoice = null;
        this.isInVoice = false;
    }

    /**
     * set the value of inVoice as true.
     */
    public void setInVoice(String inVoice) {
        this.isInVoice = true;
        setBy(isInVoice);
        this.inVoice = inVoice;
    }

    /**
     * Returns a string status of the Deadline task.
     * @return The task's status icon, description and deadline.
     */
    @Override
    public String giveTask() {
        return "[D]" + super.giveTask() + "(by: " + by + ")" + " (invoice: " + inVoice + ")";
    }
}
