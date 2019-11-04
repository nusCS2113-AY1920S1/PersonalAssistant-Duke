package duke.task;

import java.time.LocalDateTime;
import java.util.Optional;

import duke.exception.DukeException;
import duke.extensions.Recurrence;

/**
 * Subclass of duke.task.Task
 * Describes specific type of task which can be classified as an event
 */
public class Event extends Task {

    /**
     * Constructor for duke.task.Event
     * Takes in a String description like superclass duke.task.Task
     * Takes in another parameter Date to store when the duke.task.Event should be held at
     * Allows reminding of user for when the duke.task.Event is going to be
     *
     * @param description the description of the event
     * @param date and time at which the event will be held
     */
    /**
     * Constructor for duke.task.Event
     * Takes in an optional string, datetime, recurrence, description and duration
     * @param filter of the task
     * @param dateTime date time of the task
     * @param recurrence recurrence period
     * @param description description of the task
     * @param duration fixed duration of the task
     */
    public Event(Optional<String> filter, Optional<LocalDateTime> dateTime, Recurrence recurrence, String description,
                 int duration, int priority) throws DukeException {
        super(filter, dateTime, recurrence, description, duration, priority);
        this.key = "[E]";
    }
}
