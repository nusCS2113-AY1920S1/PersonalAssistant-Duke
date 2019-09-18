package parser;

import exception.DukeException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Parses the time in <code>String</code> type into the <code>Date</code> type.
 * The time string should follow the format: MM/dd/yyyy HHmm.
 */
public class TimeParser {
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HHmm");

    /**
     * Parses the time string into <code>Date</code> object.
     *
     * @param timeStr The time string to be converted into <code>Date</code> type.
     * @return The <code>Date</code> object.
     * @throws DukeException If time string has incorrect time format.
     */
    public static Date parse(String timeStr) throws DukeException {
        if (timeStr == null) {
            return null;
        }

        try {
            return TIME_FORMAT.parse(timeStr);
        } catch (ParseException e) {
            throw new DukeException("☹ OOPS!!! Incorrect time format.");
        }
    }

    /**
     * Converts a date the corresponding string.
     * @param date the date to be converted into a <code>String</code>.
     * @return the string.
     */
    public static String format(Date date) {
        if (date == null) {
            return null;
        }

        return TIME_FORMAT.format(date);
    }

}
