package duke.model;

import duke.commons.exceptions.DukeException;
import duke.logic.api.ApiParser;
import duke.model.locations.Venue;

import java.time.LocalDateTime;

/**
 * Represents an event occurring at a particular venue between a certain time.
 */
public class Event extends TaskWithDates {
    private Venue venue;

    /**
     * Initializes a event task with location.
     *
     * @param locationDescription A description of this task.
     * @param startDate Starting date of task
     * @param endDate Ending date of task
     */
    public Event(String locationDescription, LocalDateTime startDate, LocalDateTime endDate) throws DukeException {
        super(locationDescription, startDate, endDate);
        this.venue = ApiParser.getLocationSearch(locationDescription);
    }

    /**
     * Initializes a event task from persistent storage.
     *
     * @param locationDescription A description of this task.
     * @param startDate Starting date of task
     * @param endDate Ending date of task
     * @param venue location of the holiday object
     */
    public Event(String locationDescription, LocalDateTime startDate, LocalDateTime endDate, Venue venue) {
        super(locationDescription, startDate, endDate);
        this.venue = venue;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " between " + super.getStartDate() + " and " + super.getEndDate();
    }

    /**
     * Gets the Venue of the event.
     */
    public Venue getLocation() {
        assert (venue != null) : "Event can only be created with a venue";
        return venue;
    }

    public void setLocation(Venue venue) {
        this.venue = venue;
    }

    @Override
    public boolean isSameTask(Task otherTask) {
        if (otherTask == this) {
            return true;
        }

        return otherTask != null
                && otherTask.getDescription().equals(getDescription())
                && otherTask instanceof Event
                && ((TaskWithDates) otherTask).getStartDate().isEqual(getStartDate())
                && ((TaskWithDates) otherTask).getEndDate().isEqual(getEndDate())
                && ((Event) otherTask).getLocation().equals(getLocation());
    }
}
