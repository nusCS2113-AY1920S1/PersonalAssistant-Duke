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
    private final static SimpleDateFormat TimeFormat = new SimpleDateFormat("MM/dd/yyyy HHmm") ;

    /**
     * Parses the time string into <code>Date</code> object.
     *
     * @param timeStr The time string to be converted into <code>Date</code> type.
     * @return The <code>Date</code> object.
     * @throws DukeException If time string has incorrect time format.
     */
    public static Date parse(String timeStr) throws DukeException {
        try {
            return TimeFormat.parse(timeStr);
        } catch (ParseException e) {
            throw new DukeException("☹ OOPS!!! Incorrect time format.");
        }
    }
    
}
