package duke.data.tasks;

import duke.parsers.api.ApiParser;
import duke.commons.DukeException;
import duke.data.Location;

import java.time.LocalDateTime;

public class Holiday extends DoWithin {
    private Location location;

    public Holiday(String locationDescription, LocalDateTime startDate, LocalDateTime endDate) {
        super(locationDescription, startDate, endDate);
    }

    @Override
    public String toString() {
        return "[DEST] temporary " + super.toString();
    }

    /**
     * Get coordinates of the destination.
     */
    public Location getLocation() throws DukeException {
        if (this.location == null) {
            this.location = ApiParser.getLocationSearch(getDescription());
        }
        return this.location;
    }

    public void setLocation(Location location)
    {
        this.location = location;
    }
}
