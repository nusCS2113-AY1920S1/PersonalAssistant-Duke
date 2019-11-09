package task;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
    public Deadline(String description, String by) throws ParseException {
        super(description);
        this.by = by;
        this.date = dataformat.parse(by);
        this.type = "D";
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
