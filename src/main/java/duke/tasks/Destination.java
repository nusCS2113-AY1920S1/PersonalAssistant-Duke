package duke.tasks;

import duke.api.ApiParser;
import duke.commons.DukeException;
import javafx.util.Pair;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Destination extends DoWithin {
    private double latitude;
    private double longitude;
    private Pair<Double, Double> coordinates;

    public Destination(String location, LocalDateTime startDate, LocalDateTime endDate) {
        super(location, startDate, endDate);
        //update latitude && longitude
    }

    @Override
    public String toString() {
        return "[DEST] temporary " + super.toString();
    }

    public Pair<Double, Double> getCoordinates() throws DukeException {
        if (coordinates == null) {
            coordinates = ApiParser.getLocationSearch(getDescription());
            latitude = coordinates.getKey();
            longitude = coordinates.getValue();
        }
        return coordinates;
    }
}
