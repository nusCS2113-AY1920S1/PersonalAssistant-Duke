package duke.task;

import java.util.Date;

/**
 * One of the B-Extensions.
 * @author CEGLincoln
 */
public class DoWithinPeriodTasks extends Task {

    private String from;
    private String to;

    /**
     * The constructor method for DoWithinPeriodTasks.
     */
    public DoWithinPeriodTasks(String d, String f, String t) {
        super(d);
        from = f;
        to = t;
    }

    @Override
    public void setNewDate(String date) {
        //which one?
    }

    @Override
    public Date getCurrentDate() {
        return null;
    }

    @Override
    public String toString() {
        return "[P]" + super.toString() + "(from: " + from + " to: " + to + ")";
    }

    @Override
    public String printInFile() {
        if (super.isDone()) {
            return "P|1|" + super.getDescription() + "|" + from + "|" + to;
        } else {
            return "P|0|" + super.getDescription() + "|" + from + "|" + to;
        }
    }
}
