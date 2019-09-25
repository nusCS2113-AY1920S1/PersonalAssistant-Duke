package Model_Classes;

import java.util.Date;

public class RecurringEvent extends Event {
    private String recurrence;
    /**
     * Constructor for Event object
     * Takes in inputs for description of the event and the time the event occurs
     *
     * @param description Description of the event
     * @param at          Time the event happens
     */
    public RecurringEvent(String description, Date at, String recurrence) {
        super(description, at);
        this.recurrence = recurrence;
    }

    @Override
    public String toString() {
        return super.toString() + " (R: " + recurrence + ")";
    }
}
