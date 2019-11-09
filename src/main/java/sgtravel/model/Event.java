package sgtravel.model;

import sgtravel.commons.exceptions.ApiException;
import sgtravel.logic.api.ApiParser;
import sgtravel.logic.parsers.ParserTimeUtil;
import sgtravel.model.locations.Venue;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents an event occurring at a particular venue between a certain time.
 */
public class Event extends TaskWithDates implements Serializable {
    private Venue venue;

    /**
     * Initializes a event task with location.
     *
     * @param locationDescription A description of this task.
     * @param startDate Starting date of task.
     * @param endDate Ending date of task.
     */
    public Event(String locationDescription, LocalDateTime startDate, LocalDateTime endDate) throws ApiException {
        super(locationDescription, startDate, endDate);
        this.venue = ApiParser.getLocationSearch(locationDescription);
    }

    /**
     * Initializes a event task from persistent storage.
     *
     * @param locationDescription A description of this task.
     * @param startDate Starting date of task.
     * @param endDate Ending date of task.
     * @param venue location of the holiday object.
     */
    public Event(String locationDescription, LocalDateTime startDate, LocalDateTime endDate, Venue venue) {
        super(locationDescription, startDate, endDate);
        this.venue = venue;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " between " + ParserTimeUtil.stringify(getStartDate())
                + " and " + ParserTimeUtil.stringify(getEndDate());
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
                && ((Event) otherTask).getLocation().equals(getLocation());
    }
}
