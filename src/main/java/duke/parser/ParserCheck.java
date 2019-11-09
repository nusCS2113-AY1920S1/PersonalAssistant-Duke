package duke.parser;

import duke.exceptions.DukeException;
import duke.models.locker.Address;
import duke.models.locker.LockerDate;
import duke.models.locker.SerialNumber;
import duke.models.locker.Zone;
import duke.models.student.Email;
import duke.models.student.Major;
import duke.models.student.MatricNumber;
import duke.models.student.Name;
import duke.models.tag.Tag;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class ParserCheck {

    private static final String SIZE_ERROR = " The size of the batch of lockers should satisfy the "
            + " following constraints:"
            + "\n     1. It should be a positive integer."
            + "\n     2. It should be within the range of 1 to 30 (inclusive).";
    private static final String SPLIT_BY_SPACE = " ";

    private static final String PREFERENCES_ERROR = " There must be at least one valid zone "
            + "under preferences"
            + "\n    " + Zone.ERROR_MESSAGE;
    private static final String DATE_FORMAT = "dd-MM-uuuu";

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
     *
     * @param size to store the number of lockers to be added
     * @return a valid size in terms of a number
     * @throws DukeException when the size is invalid
     */
    public static int parseSize(String size) throws DukeException {
        requireNonNull(size);
        try {
            int numLockers = Integer.parseInt(size.trim());
            if (numLockers <= 0 || numLockers > 30) {
                throw new DukeException(SIZE_ERROR);
            }
            return numLockers;
        } catch (NumberFormatException e) {
            throw new DukeException(SIZE_ERROR);
        }
    }

    /**
     * This function is used to parse the name of the student.
     *
     * @param name stores the name of the student
     * @return a valid instance of the student name
     * @throws DukeException when the name is in invalid format
     */
    public static Name parseName(String name) throws DukeException {
        requireNonNull(name);
        if (!Name.checkIsValidName(name.trim())) {
            throw new DukeException(Name.ERROR_MESSAGE);
        }
        return new Name(name.trim());
    }

    /**
     * This function is used to parse the matric number / student id of the student.
     * @param matricNumber stores the matriculation number of the student
     * @return a valid instance of MatricNumber
     * @throws DukeException when the matriculation number is in invalid format
     */
    public static MatricNumber parseMatricNumber(String matricNumber) throws DukeException {
        requireNonNull(matricNumber);
        if (!MatricNumber.checkIsValidMatricNumber(matricNumber.trim())) {
            throw new DukeException(MatricNumber.ERROR_MESSAGE);
        }
        return new MatricNumber(matricNumber.trim());
    }

    /**
     * This function parses the major/course pursued by a student.
     * @param major stores the major of the student
     * @return a valid instance of Major
     * @throws DukeException when the major is in invalid format
     */
    public static Major parseMajor(String major) throws DukeException {
        requireNonNull(major);
        if (!Major.checkIsValidCourse(major)) {
            throw new DukeException(Major.ERROR_MESSAGE);
        }
        return new Major(major.trim());
    }

    /**
     * This function parses the email of the student.
     * @param email stores the email id of the student
     * @return a valid instance of Email
     * @throws DukeException when the email is in invalid format
     */
    public static Email parseEmail(String email) throws DukeException {
        requireNonNull(email);
        if (!Email.checkIsValidEmail(email.trim())) {
            throw new DukeException(Email.ERROR_MESSAGE);
        }
        return new Email(email.trim());
    }

    /**
     * This function parses the date for the subscription of the lockers.
     * @param date stores the date for subscription
     * @return a valid instance of LockerDate
     * @throws DukeException when the date is in invalid format
     */
    public static LockerDate parseDate(String date) throws DukeException {
        requireNonNull(date);
        if (!LockerDate.checkIsValidDate(date)) {
            throw new DukeException(LockerDate.ERROR_MESSAGE);
        }
        return new LockerDate(date.trim());
    }

    /**
     * This function is used to parse the user preferences for allocation of lockers.
     * @param preferences stores the preferences of the user.
     * @return a list of all valid zones
     * @throws DukeException when there are no valid zones in the list of preferences
     */
    public static List<Zone> parsePreferences(String preferences) throws DukeException {
        requireNonNull(preferences);
        List<Zone> getPreferences = new ArrayList<>();
        List<String> getEachPreference = new ArrayList<String>();
        getEachPreference = Arrays.asList(preferences.trim().split(SPLIT_BY_SPACE));
        //Only the preferences with a valid zone name will be added to the list of preferences
        for (String s : getEachPreference) {
            if (Zone.checkIsValidZone(s)) {
                getPreferences.add(parseZone(s));
            }
        }

        if (getPreferences.size() == 0) {
            throw new DukeException(PREFERENCES_ERROR);
        }
        return getPreferences;
    }

    /**
     * checks if the status of the locker is in the correct format.
     * @param status stores the status to be checked
     * @return an instance of a valid Tag
     * @throws DukeException when the format is invalid.
     */
    public static Tag parseStatus(String status) throws DukeException {
        requireNonNull(status);
        if (!Tag.checkValidTagName(status)) {
            throw new DukeException(Tag.INVALID_TAG_NAME);
        }
        return new Tag(status);
    }

    /**
     * This function is used to check the difference between dates.
     * @param startDate stores the starting date for rental
     * @param endDate stores the ending date for rental
     * @throws DukeException when the date is invalid format
     */
    public static void parseDifferenceBetweenStartAndEndDate(LockerDate startDate,
                                                             LockerDate endDate) throws DukeException {
        requireNonNull(startDate);
        requireNonNull(endDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT)
                .withResolverStyle(ResolverStyle.STRICT);
        LocalDate currentDate = LocalDate.now();
        if (!LockerDate.isDifferenceBetweenDatesValid(startDate.getDate(),
                endDate.getDate())
                || LockerDate.isEndDateBeforeCurrentDate(endDate.getDate(), formatter.format(currentDate))) {

            throw new DukeException(LockerDate.ERROR_IN_DATE_DIFFERENCE);

        }
    }
}
