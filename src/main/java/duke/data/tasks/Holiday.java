package duke.data.tasks;

import duke.parsers.api.ApiParser;
import duke.commons.DukeException;
import duke.data.Location;

import java.time.LocalDateTime;

public class Holiday extends DoWithin {
    private Location location;

    public Holiday(String locationDescription, LocalDateTime startDate, LocalDateTime endDate) throws DukeException {
        super(locationDescription, startDate, endDate);
        // This can be removed once we implement the map ?
        this.location = ApiParser.getLocationSearch(locationDescription);
    }

    public Holiday(String locationDescription, LocalDateTime startDate, LocalDateTime endDate ,Location location) {
        super(locationDescription, startDate, endDate);
        this.location = location;
    }
    @Override
    public String toString() {
        return "[H]" + super.toString();
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

    public String getHoliday() {
        return super.getStartDate().toString() + " | " + super.getEndDate() + "|" + this.location.getAddress()
                + "|" +this.location.getLatitude()+ "|" + this.location.getLongitude() + "|" + this.location.getDistX()
                + "|" +this.location.getDistY();
    }
}
