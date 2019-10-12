package duke.model;

import duke.model.ApiParser;
import duke.commons.exceptions.DukeException;
import duke.model.Venue;

import java.time.LocalDateTime;

public class Event extends DoWithin {
    private Venue venue;

    /**
     * Initializes a holiday task with location.
     *
     * @param locationDescription A description of this task.
     * @param startDate Starting date of task
     * @param endDate Ending date of task
     */
    public Event(String locationDescription, LocalDateTime startDate, LocalDateTime endDate) throws DukeException {
        super(locationDescription, startDate, endDate);
        // This can be removed once we implement the map ?
        this.venue = ApiParser.getLocationSearch(locationDescription);
    }

    /**
     * Initializes a holiday task from persistent storage.
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
        return "[H]" + super.toString();
    }

    /**
     * Get coordinates of the destination.
     * @return
     */
    public duke.model.Venue getLocation() throws DukeException {
        if (this.venue == null) {
            this.venue = ApiParser.getLocationSearch(getDescription());
        }
        return this.venue;
    }

    public void setLocation(Venue venue) {
        this.venue = venue;
    }

    /**
     * Returns the string to store the Holiday object in persistent storage.
     */
    public String getHoliday() {
        return super.getWithin() + " | " + venue.toString();
    }
}
