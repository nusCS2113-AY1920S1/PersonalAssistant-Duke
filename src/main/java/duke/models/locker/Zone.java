package duke.models.locker;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import duke.exceptions.DukeException;

import static java.util.Objects.requireNonNull;

/**
 * Stores the zone associated with a locker.
 */
public class Zone {

    public static final String ERROR_MESSAGE = " Zone should consist of only 1 letter "
           + "and cannot be empty";

    private static final int SINGLE_LETTER = 1;
    private static final String CHECK_REGEX = "^[a-zA-Z]*$";
    private final String zone;

    /**
     * This constructor is used to instantiate the class with the zone value passed to it.
     * @param zone stores the zone assigned to a locker
     * @throws DukeException when zone is in invalid format
     */
    @JsonCreator
    public Zone(@JsonProperty("Zone") String zone) throws DukeException {
        requireNonNull(zone);
        if (!checkIsValidZone(zone)) {
            throw new DukeException(ERROR_MESSAGE);
        }
        this.zone = zone.toUpperCase();
    }

    /**
     * Checks if the zone is valid or not.
     * A zone is valid if it contains only a single alphabet
     * @param zone string to be tested for validity
     * @return true if the zone is valid
     */
    public static boolean checkIsValidZone(String zone) {
        return zone.matches(CHECK_REGEX)
                && zone.length() == SINGLE_LETTER;
    }

    @JsonGetter("Zone")
    public String getZone() {
        return zone;
    }

    /* Need to override functions equals() and hashCode() in order to account for
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
