package duke.model;

import duke.parsers.api.ApiParser;
import duke.commons.exceptions.DukeException;
import duke.data.Location;

import java.time.LocalDateTime;

public class Event extends DoWithin {
    private Location location;

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
        this.location = ApiParser.getLocationSearch(locationDescription);
    }

    /**
     * Initializes a holiday task from persistent storage.
     *
     * @param locationDescription A description of this task.
     * @param startDate Starting date of task
     * @param endDate Ending date of task
     * @param location location of the holiday object
     */
    public Event(String locationDescription, LocalDateTime startDate, LocalDateTime endDate, Location location) {
        super(locationDescription, startDate, endDate);
        this.location = location;
    }

    @Override
    public String toString() {
        return "[H]" + super.toString();
    }

    /**
     * Get coordinates of the destination.
     * @return
     */
    public duke.model.Location getLocation() throws DukeException {
        if (this.location == null) {
            this.location = ApiParser.getLocationSearch(getDescription());
        }
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Returns the string to store the Holiday object in persistent storage.
     */
    public String getHoliday() {
        return super.getWithin() + " | " + location.toString();
    }
}
