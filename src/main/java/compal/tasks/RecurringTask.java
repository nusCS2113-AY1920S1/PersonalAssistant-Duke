package compal.tasks;

import java.util.Date;

/**
 * Provides support for recurring tasks.
 */
public class RecurringTask extends Task{

    /**
     * Store the recurring task. Will be expanded into Lecture, Tutorials, Labs etc. after I figure out the date bug.
     *
     * @param description Description of the task to be stored.
     * @param date Date of the event. Supposed to differ among all recurring task, but somehow remain the same. I
     *             will try and figure out what's wrong. 
     */
    public RecurringTask(String description, Date date) {
        super(description);
        super.symbol = "RT";
        super.setDateTime(date);
    }

}
