package compal.tasks;

import java.text.SimpleDateFormat;
import java.util.Date;

import compal.main.Duke;
import compal.tasks.Task;

/**
 * Provides support for recurring tasks.
 */
public class RecurringTask extends Task {

    /**
     * Store the recurring task.
     *
     * @param description Description of the task to be stored.
     * @param date        Date of the event.
     */
    public RecurringTask(String description, String date,String time) {
        super(description);
        super.symbol = "RT";
        super.setDate(date);
        super.setTime(time);
    }

    /**
     * Overrides the toString method. This provides different date for tasks
     * with identical descriptions. This is done by concatenating the string form
     * of the task's date attribute to the description.
     *
     * @return The description of the task with the date of the task.
     */
}
