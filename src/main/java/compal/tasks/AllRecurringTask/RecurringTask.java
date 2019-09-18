package compal.tasks.AllRecurringTask;

import java.text.SimpleDateFormat;
import java.util.Date;

import compal.tasks.Task;

/**
 * Provides support for recurring tasks.
 */
public class RecurringTask extends Task {

    /**
     * Store the recurring task.
     *
     * @param description Description of the task to be stored.
     * @param date Date of the event.
     */
    public RecurringTask(String description, Date date) {
        super(description);
        super.symbol = "RT";
        super.setDateTime(date);
    }

    /**
     * Overrides the toString method. This provides different date for tasks
     * with identical descriptions. This is done by concatenating the string form
     * of the task's date attribute to the description.
     *
     * @return The description of the task with the date of the task.
     */
    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyy HHmm");
        Date date = this.getDateTime();
        String strDate = format.format(date);
        return super.toString() + "at " + strDate;
    }
}
