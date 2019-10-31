package duke.models.locker;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import duke.exceptions.DukeException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

import static java.util.Objects.requireNonNull;

public class LockerDate {

    public static final String ERROR_MESSAGE = " The date should satisfy the following constraints:"
            + "\n\n      1. Should be a valid date as per the Gregorian Calendar."
            + "\n      2. Should be in the format of <DD-MM-YYYY>";

    private static final DateTimeFormatter checkDateFormat =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public String date;

    /**
     * This constructor is used to instantiate a valid Date.
     * @param date stores the date that is to be assigned to the member field
     * @throws DukeException when the date is in invalid format
     */
    public LockerDate(String date) throws DukeException {
        requireNonNull(date);
        if (!checkIsValidDate(date)) {
            throw new DukeException(ERROR_MESSAGE);
        }
        this.date = date;
    }

    public LockerDate() {

    }

    /**
     * This function is used to check whether the date is in correct format or not.
     * @param date stores the date that is to be tested for its validity.
     * @return true if the date is in valid format, false otherwise.
     */
    public static boolean checkIsValidDate(String date) {
        try {
            LocalDate.parse(date,checkDateFormat);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    @JsonGetter("date")
    public String getDate() {
        return date;
    }

    @JsonSetter("date")
    public void setDate() {
        this.date = date;
    }

    /**
     * This function is used to check if the there is a difference of at least 7 days
     * between the two dates.
     * @param startDate the starting date of locker subscription
     * @param endDate the end date of locker subscription
     * @return true if the difference is valid, false otherwise
     */
    public static boolean isDifferenceBetweenDatesValid(String startDate,String endDate) {
        LocalDate localStartDate = LocalDate.parse(startDate,checkDateFormat);
        LocalDate localEndDate = LocalDate.parse(endDate,checkDateFormat);
        long daysBetween = localStartDate.until(localEndDate, ChronoUnit.DAYS);
        if (daysBetween <= 6 || daysBetween > 365) {
            return false;
        }
        return true;
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
