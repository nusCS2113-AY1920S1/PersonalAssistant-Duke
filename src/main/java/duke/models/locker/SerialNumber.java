package duke.models.locker;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import duke.exceptions.DukeException;

import static java.util.Objects.requireNonNull;

/**
 * Stores the serial number of the locker.
 */
public class SerialNumber {

    public static final String ERROR_MESSAGE = " Serial number can only be a non-negative integer with no more"
            + "than six digits and it cannot be empty";
    private static final int CHECK_SIX_DIGITS = 6;

    public static final String CHECK_REGEX = "[0-9]+";
    private static final String REGEX_FOR_LEADING_ZEROES = "^0+(?!$)";

    public final String serialNumberForLocker;

    /**
     * Instantiates the class with the serial number passed to it.
     * @param serialNumber stores the serial number assigned to a locker
     * @throws DukeException when the serial number is in invalid format
     */
    @JsonCreator
    public SerialNumber(@JsonProperty("serialNumber") String serialNumber) throws DukeException {
        requireNonNull(serialNumber);
        if (!checkIsValidSerialNumber(serialNumber)) {
            throw new DukeException(ERROR_MESSAGE);
        }
        this.serialNumberForLocker = serialNumber.replaceFirst(REGEX_FOR_LEADING_ZEROES, "");
    }

    /**
     * Checks whether the {@code serialNumberForLocker} is valid or not.
     * A valid serial number is a non-negative integer which has at most six digits
     * @return true if the serial number is valid
     */
    public static boolean checkIsValidSerialNumber(String serialNumberForLocker) {
        return serialNumberForLocker.replaceFirst(REGEX_FOR_LEADING_ZEROES, "")
                .matches(CHECK_REGEX)
                && serialNumberForLocker.replaceFirst(REGEX_FOR_LEADING_ZEROES, "")
                .length() <= CHECK_SIX_DIGITS;
    }

    @JsonGetter("serialNumber")
    public String getSerialNumberForLocker() {
        return serialNumberForLocker;
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if the two objects are the same
                || (other instanceof SerialNumber //handles all cases for null
                && serialNumberForLocker
                .equals(((SerialNumber) other).serialNumberForLocker)); //checks for equality
    }

    @Override
    public int hashCode() {
        return serialNumberForLocker.hashCode();
    }
}
