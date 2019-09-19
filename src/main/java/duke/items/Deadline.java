package duke.items;

import duke.exceptions.BadInputException;
import java.util.Calendar;
import java.util.Date;

/**
 * In addition to the deadline and done status (inherited from Task),
 * the Deadline has a doByDate that is represented by a date class.
 * The save and print strings have been overridden to show more information.
 */

public class Deadline extends Task implements Snooze {

    private String doBy;
    private DateTime doByDate;

    /**
     * Deadline object has a "by" string as well as a Date object.
     */
    public Deadline(int index, String description, String by) throws BadInputException {
        super(index, description, TaskType.DEADLINE); //Using the Task constructor. isDone is set to false.
        this.doBy = by;
        this.doByDate = new DateTime(doBy);
    }

    public String getDoBy() {
        return doBy;
    }

    public String getDoByDate() {
        return doByDate.returnFormattedDate();
    }

    public Date getDate() {
        return doByDate.getAt();
    }

    @Override
    public void snooze() {
        Calendar newDate = Calendar.getInstance();
        newDate.setTime(doByDate.getAt());
        newDate.add(Calendar.DATE, 1);
        doByDate.setDate(newDate.getTime());
    }

    @Override
    public String saveDetailsString() {
        return super.saveDetailsString() + "/" + doBy;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + doByDate.returnFormattedDate() + ")";
    }
}