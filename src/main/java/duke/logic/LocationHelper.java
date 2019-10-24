package duke.logic;

import duke.model.locations.Venue;
import javafx.scene.input.KeyCode;

public class LocationHelper {

    /**
     * Checks if the one venue is above/below/left/right of another venue.
     * @param keyCode The keyCode to decide up/down/left/right.
     * @param currentVenue The current venue.
     * @param v The other venue.
     * @return true if v is indeed above/below/left/right of currentVenue.
     */
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

    /**
     * Checks if v is above currentVenue.
     */
    private static boolean isUp(Venue currentVenue, Venue v) {
        return v.getLatitude() >= currentVenue.getLatitude();
    }
}
