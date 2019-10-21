package duke.logic;

import duke.model.locations.Venue;
import javafx.scene.input.KeyCode;

public class LocationHelper {

    public static boolean checkDirection(KeyCode keyCode, Venue currentVenue, Venue v) {
        if (keyCode.equals(KeyCode.UP)) {
            return isUp(currentVenue, v);
        } else if (keyCode.equals(KeyCode.DOWN)) {
            return isDown(currentVenue, v);
        } else if (keyCode.equals(KeyCode.LEFT)) {
            return isLeft(currentVenue, v);
        } else {
            return isRight(currentVenue, v);
        }
    }

    private static boolean isRight(Venue currentVenue, Venue v) {
        return v.getLongitude() >= currentVenue.getLongitude();
    }

    private static boolean isLeft(Venue currentVenue, Venue v) {
        return v.getLongitude() <= currentVenue.getLongitude();
    }

    private static boolean isDown(Venue currentVenue, Venue v) {
        return v.getLatitude() <= currentVenue.getLatitude();
    }

    private static boolean isUp(Venue currentVenue, Venue v) {
        return v.getLatitude() >= currentVenue.getLatitude();
    }
}
