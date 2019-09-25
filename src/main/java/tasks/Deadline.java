package tasks;

import java.text.SimpleDateFormat;
import java.util.Date;

/**

 * This is the Deadline Class.

 * inherits from Task Class.

 * Has constructor and methods for the Deadline object.

 */

public class Deadline extends Task {

    protected Date by;
    private SimpleDateFormat simpleDateFormat;


    /**
     * This constructor "Deadline" is used to construct the Deadline object.
     * @param description This is the first parameter to the Deadline constructor.
     * @param by This is the second parameter to the Deadline constructor.
     */

    public Deadline(String description, Date by) {
        super(description);
        this.by = by;
        super.type = "D";
        simpleDateFormat  = new SimpleDateFormat("d/M/yyyy HHmm");
    }


    /**
     * This method "toString" is used to return the relevant attributes to be printed.
     * @return String This returns the relevant attributes to be printed.
     */

    @Override
    public String toString() {
        return "[D]" + super.toString() + "(by: " + getBy() + ")";
    }


    /**
     * This method "getBy" is used to get the due date of the object.
     * @return String This returns the due date of the object.
     */

    public String getBy() {
        return simpleDateFormat.format(by);
    }


    /**
     * This method "getDateBy" is used to get the due date of the object.
     * @return Date This returns the due date of the object.
     */

    public Date getDateBy() {
        return by;
    }
}
