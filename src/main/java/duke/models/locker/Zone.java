package duke.models.locker;

import com.fasterxml.jackson.annotation.JsonGetter;

import com.fasterxml.jackson.annotation.JsonSetter;
import duke.exceptions.DukeException;

import static java.util.Objects.requireNonNull;

public class Zone {

    public static final String ERROR_MESSAGE = " Zone should have only 1 alphanumeric character "
           + "and cannot be empty";

    public static final String CHECK_REGEX = "^[a-zA-Z]*$";
    public String zone;

    /**
     * This constructor is used to instantiate the class with the zone value passed to it.
     * @param zone stores the zone assigned to a locker
     * @throws DukeException when zone is in invalid format
     */

    public Zone(String zone) throws DukeException {
        requireNonNull(zone);
        if (!checkIsValidZone(zone)) {
            throw new DukeException(ERROR_MESSAGE);
        }
        this.zone = zone;
    }

    public Zone() {

    }

    public static boolean checkIsValidZone(String zone) {
        return zone.matches(CHECK_REGEX) && zone.length() == 1;
    }

    @JsonGetter("Zone")
    public String getZone() {
        return zone;
    }

    @JsonSetter("Zone")
    public void setZone(String zone) {
        this.zone = zone;
    }

    /* We need to override functions equals() and hashCode() in order to account for
      used defined checking for equality while using streams
    */
    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if the two objects are the same
                || (other instanceof Zone //handles all cases for null
                && zone.equalsIgnoreCase(((Zone) other).zone)); //checks for equality
    }

    @Override
    public int hashCode() {
        return zone.hashCode();
    }
}
