package duke.models.locker;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import duke.exceptions.DukeException;

import static java.util.Objects.requireNonNull;

public class SerialNumber {

    public static final String ERROR_MESSAGE = " Serial number can only be a positive integer with no more"
            + "than six digits and it cannot be empty";

    public static final String CHECK_REGEX = "[0-9]+";

    public final String serialNumberForLocker;

    /**
     * This constructor is used to instantiate the class with the serial number passed to it.
     * @param serialNumber stores the serialnumber assigned to a locker
     * @throws DukeException when the serial number is in invalid format
     */
    @JsonCreator
    public SerialNumber(@JsonProperty("serialNumber") String serialNumber) throws DukeException {
        requireNonNull(serialNumber);
        if (!checkIsValidSerialNumber(serialNumber)) {
            throw new DukeException(ERROR_MESSAGE);
        }
        this.serialNumberForLocker = serialNumber;
    }

    public static boolean checkIsValidSerialNumber(String serialNumberForLocker) {
        return serialNumberForLocker.matches(CHECK_REGEX)
                && serialNumberForLocker.length() <= 6;
    }

    @JsonGetter("serialNumber")
    public String getSerialNumberForLocker() {
        return serialNumberForLocker;
    }

    /* We need to override functions equals() and hashCode() in order to account for
       used defined checking for equality while using streams
     */
    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if the two objects are the same
                || (other instanceof SerialNumber //handles all cases for null
                && serialNumberForLocker.equals(((SerialNumber) other)
                .serialNumberForLocker)); //checks for equality
    }

    @Override
    public int hashCode() {
        return serialNumberForLocker.hashCode();
    }
}
