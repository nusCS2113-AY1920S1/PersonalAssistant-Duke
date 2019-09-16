package Task;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Creates a class for the Deadline task
 */
public class Deadline extends item{

    protected Date by;

    /**
     * Constructor to create the deadline class
     *
     * @param info This parameter is the info of the item created
     * @param status The status of the item created, either true or false
     * @param by The date of the deadline
     */
    public Deadline(String info, Boolean status, String by) {
        super(info, status);
        super.setType("D");
        this.by = TaskList.dateConvert(by);
    }

    /**
     * This function takes the "by" data in the Event class and converts it into the string output format
     *  Format: 2nd of December 2019, 2pm.
     *
     * @return New string format
     */
    @Override
    public String getDate () {
        String hour =  new SimpleDateFormat("h").format(by);
        String min = new SimpleDateFormat("mm").format(by);
        String marker = new SimpleDateFormat("a").format(by);
        String day = new SimpleDateFormat("d").format(by);
        String monthYear = new SimpleDateFormat("MMMMM yyyy").format(by);
        String newDateFormat = TaskList.numOrdinal(Integer.parseInt(day)) + " of " + monthYear + ", " +
                                hour + (min.equals("00") ? marker : ("." + min + marker));
        return newDateFormat;
    }

    /**
     * Gets all the info of the deadline
     *
     * @return A string phrase containing all the details of the deadline object
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + getDate() + ")";
    }

}
