package Model_Classes;


import Enums.TimeUnit;

import java.util.Date;

/**
 * An object class representing types of tasks with deadlines.
 * Stores the description and when the task should be done by.
 */
public class Assignment extends Task {
    /**
     * Constructor for the Deadline object.
     * Takes in inputs for description and date/time the tasks should be done by.
     * @param description Description of the task
     * @param by The time the tasks should be done by.
     */
    public Assignment (String description, Date by) {
        super(description, by);
    }

    public Assignment (String description, String duration, TimeUnit unit) {
        super(description, null);
    }

    /**
     * Returns the full description including the deadline of the task.
     * @return A string indicating the task type, description, and when it should be done by.
     */
    @Override
    public String toString() {
        return "[A]" + super.toString() + " (by: " + super.getDate() + ")";
    }
}
