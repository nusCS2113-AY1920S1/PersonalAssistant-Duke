package Model_Classes;

import java.util.Date;

public class RecurringDeadline extends Deadline {
    private String recurrence;
    /**
     * Constructor for the Deadline object.
     * Takes in inputs for description and date/time the tasks should be done by.
     *
     * @param description Description of the task
     * @param by          The time the tasks should be done by.
     */
    public RecurringDeadline(String description, Date by, String recurrence) {
        super(description, by);
        this.recurrence = recurrence;
    }

    @Override
    public String toString() {
        return super.toString() + " (R: " + recurrence + ")";
    }
}
