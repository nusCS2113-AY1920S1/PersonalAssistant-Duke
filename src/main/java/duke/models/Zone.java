package duke.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import duke.exceptions.DukeException;

import static java.util.Objects.requireNonNull;

public class Zone {

    public static final String ERROR_MESSAGE = " Zone can have at most 2 alphanumeric characters "
           + "and cannot be empty";
    public final String zone;

    /**
     * This constructor is used to instantiate the class with the zone value passed to it.
     * @param zone stores the zone assigned to a locker
     * @throws DukeException when zone is in invalid format
     */

    @JsonCreator
    public Zone(@JsonProperty("lockerZone") String zone) throws DukeException {
        requireNonNull(zone);
        if (!checkIsValidZone(zone)) {
            throw new DukeException(ERROR_MESSAGE);
        }
        this.zone = zone;
    }

    public static boolean checkIsValidZone(String test) {
        return test.matches("^[a-zA-Z0-9]*$") && test.length() <= 2;
    }

    @JsonGetter("lockerZone")
    public String getZone() {
        return zone;
    }
}
