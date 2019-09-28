package duke.tasks;

import java.time.LocalDateTime;

public class Destination extends DoWithin {
    private double latitude;
    private double longitude;

    public Destination(String location, LocalDateTime startDate, LocalDateTime endDate) {
        super(location, startDate, endDate);
        //update latitude && longitude
    }

    @Override
    public String toString() {
        return "[DEST] temporary " + super.toString();
    }
}
