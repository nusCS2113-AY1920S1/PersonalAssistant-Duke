package Model_Classes;

import Enums.Priority;

import java.util.Date;

public class RecurringEvent extends Event {
    private String recurrence;
    /**
     * Constructor for RecurringEvent object
     * Takes in inputs for description of the event and the time the event occurs
     * @param recurrence Frequency task is repeated
     * @param description Description of the event
     * @param at Time the event happens
     */
    public RecurringEvent(String description, Date at, String recurrence) {
        super(description, at);
        this.recurrence = recurrence;
    }

    /**
     *
     * @param description
     * @param at
     * @param recurrence
     */
    public RecurringEvent(String description, Date at, String recurrence, String user) {
        super(description, at, user);
        this.recurrence = recurrence;
    }

    /**
     * Overload constructor for RecurringEvent object
     * Takes in inputs for description of the event and the time the event occurs
     * @param recurrence Frequency task is repeated
     * @param description Description of the event
     * @param at Time the event happens
     */
    public RecurringEvent(String description, Date at, String recurrence, boolean done, Priority priority) {
        super(description, at, done, priority);
        this.recurrence = recurrence;
    }

    /**
     * Returns the full descriptor of the task
     * will show the type of task, whether it has been done,
     * the description of the task, the time it should be done by,
     * and the recurrence schedule of the task
     * @return A String with all the information listed above.
     */
    @Override
    public String toString() {
        return super.toString() + " (R: " + recurrence + ")";
    }
}
