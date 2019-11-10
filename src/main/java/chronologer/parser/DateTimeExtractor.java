package chronologer.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * This DateTimeExtractor class allows the identification of dates and is used
 * to print out dates and times.
 *
 * @author Sai Ganesh Suresh
 * @version v2.0
 */
public class DateTimeExtractor {

    public static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
    private static LocalDateTime dateToReturn;

    /**
     * This function appends the end time of an event to provide a standardised
     * output of date and time.
     *
     * @param dateTimeFromUser A string containing only the date segment of the user
     *                         input.
     * @return String A string containing the end time of an event will also
     *         returned.
     * @throws DateTimeParseException Throws this exception if the date or time
     *                        format provided by the user is incorrect!
     */
    public static LocalDateTime extractDateTime(String dateTimeFromUser) throws DateTimeParseException {
        dateToReturn = LocalDateTime.parse(dateTimeFromUser, DATE_FORMATTER);
        return dateToReturn;
    }
}
