package duke.parser;

import duke.exceptions.DukeException;
import duke.models.Address;
import duke.models.SerialNumber;
import duke.models.Zone;

import static java.util.Objects.requireNonNull;

public class ParserCheck {

    /**
     * This function is used to parse the serial number for the locker.
     * @param serialNumber stores the serial number that is to be parsed
     * @return reference to a valid serialNumber
     * @throws DukeException when the Serial Number has invalid format
     */
    public static SerialNumber parseSerialNumber(String serialNumber) throws DukeException {
        requireNonNull(serialNumber);
        if (!SerialNumber.checkIsValidSerialNumber(serialNumber.trim())) {
            throw new DukeException(SerialNumber.ERROR_MESSAGE);
        }
        return new SerialNumber(serialNumber.trim());
    }

    /**
     * This function is used to parse the address for the locker.
     * @param address stores the address that is to be parsed
     * @return reference to a valid Address
     * @throws DukeException when the address has invalid format
     */
    public static Address parseAddress(String address) throws DukeException {
        requireNonNull(address);
        if (!Address.checkIsValidAddress(address.trim())) {
            throw new DukeException(Address.ERROR_MESSAGE);
        }
        return new Address(address.trim());
    }

    /**
     * This function is used to parse the zone for the locker.
     * @param zone stores the zone that is to be parsed
     * @return a valid reference to zone
     * @throws DukeException when the zone has invalid format
     */
    public static Zone parseZone(String zone) throws DukeException {
        requireNonNull(zone);
        if (!Zone.checkIsValidZone(zone.trim())) {
            throw new DukeException(Zone.ERROR_MESSAGE);
        }
        return new Zone(zone.trim());
    }

    /**
     * This function is used to parse the number of lockers to be added in bulk.
     * @param size to store the number of lockers to be added
     * @return a valid size in terms of a number
     * @throws DukeException when the size is invalid
     */
    public static int parseSize(String size) throws DukeException {
        requireNonNull(size);
        try {
            int numLockers = Integer.parseInt(size.trim());
            if (numLockers < 0 || numLockers > 30) {
                throw new DukeException(" Please enter a positive number within the range of 1 to 30");
            }
            return numLockers;
        } catch (NumberFormatException e) {
            throw new DukeException(" Please enter a positive integer for the number of lockers");
        }
    }
}
