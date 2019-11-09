package duke.models.locker;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import duke.exceptions.DukeException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoUnit;

import static java.util.Objects.requireNonNull;

public class LockerDate {

    public static final String ERROR_MESSAGE = " The date should satisfy the following constraints:"
            + "\n\n      1. Should be a valid date as per the Gregorian Calendar."
            + "\n      2. Should be in the format of <DD-MM-YYYY>";

    public static final String ERROR_IN_DATE_DIFFERENCE = " The rental period "
            + " should satisfy the following constraints:\n"
            + "\n     1. The end date should be after the start date."
            + "\n     2. The rental period should be between 7 to 365 days (inclusive)."
            + "\n     3. The rental ending date cannot be before the current date.";

    private static final String DATE_FORMAT = "dd-MM-uuuu";
    private static final DateTimeFormatter checkDateFormat =
            DateTimeFormatter.ofPattern(DATE_FORMAT).withResolverStyle(ResolverStyle.STRICT);

    public String date;

    /**
     * This constructor is used to instantiate a valid Date.
     * @param date stores the date that is to be assigned to the member field
     * @throws DukeException when the date is in invalid format
     */
    @JsonCreator
    public LockerDate(@JsonProperty("date") String date) throws DukeException {
        requireNonNull(date);
        if (!checkIsValidDate(date)) {
            throw new DukeException(ERROR_MESSAGE);
        }
        this.date = date;
    }

    /**
     * This function is used to check whether the date is in correct format or not.
     * @param date stores the date that is to be tested for its validity.
     * @return true if the date is in valid format, false otherwise.
     */
    public static boolean checkIsValidDate(String date) {
        try {
            LocalDate.parse(date, checkDateFormat);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    @JsonGetter("date")
    public String getDate() {
        return date;
    }

    /**
     * This function is used to check if the there is a difference of at least 7 days
     * between the two dates.
     * @param startDate the starting date of locker subscription
     * @param endDate the end date of locker subscription
     * @return true if the difference is valid, false otherwise
     */
    public static boolean isDifferenceBetweenDatesValid(String startDate, String endDate) {
        LocalDate localStartDate = LocalDate.parse(startDate, checkDateFormat);
        LocalDate localEndDate = LocalDate.parse(endDate, checkDateFormat);
        long daysBetween = localStartDate.until(localEndDate, ChronoUnit.DAYS);
        if (daysBetween <= 6 || daysBetween > 365) {
            return false;
        }
        return true;
    }

    /**
     * checks the ending date for locker subscription from current date.
     * @param currentDate the current date
     * @param endDate the rental ending date
     * @return daysBetween for the total amount of days
     */
    public static long differenceBetweenDates(String currentDate, String endDate) {
        LocalDate localCurrentDate = LocalDate.parse(currentDate, checkDateFormat);
        LocalDate localEndDate = LocalDate.parse(endDate, checkDateFormat);
        long daysBetween = localCurrentDate.until(localEndDate, ChronoUnit.DAYS);
        return daysBetween;
    }

    /**
     * checks whether the ending date for locker subscription is before the current date.
     * @param endDate the rental ending date
     * @param currentDate the current date
     * @return true if the ending date is before the current date, false otherwise
     */
    public static boolean isEndDateBeforeCurrentDate(String endDate, String currentDate) {
        LocalDate localEndDate = LocalDate.parse(endDate, checkDateFormat);
        LocalDate localCurrentDate = LocalDate.parse(currentDate, checkDateFormat);
        long daysBetween = localEndDate.until(localCurrentDate, ChronoUnit.DAYS);
        if (daysBetween > 0) {
            return true;
        }
        return false;
    }

    /* We need to override function equals and hashCode() in order
       to account for user defined checks for equality using streams
     */
    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if both the objects are the same
                || (other instanceof LockerDate //checks for all instances of null
                && date.equals(((LockerDate) other).date)); //check for equality
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }
}
