package duke.parser;

import duke.commons.DukeException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Parse time. Convert between date object and String date.
 */
public class TimeParser {

    /**
     * Converts a Date object to a String representing the date.
     *
     * @param date Date object
     * @return a String representing the date.
     */
    public static String convertDateToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HHmm");
        return dateFormat.format(date);
    }

    /**
     * Converts a String representing the date to a Date object.
     *
     * @param str a String representing the date. Must be in correct format: dd-MM-yyyy HHmm
     * @return a Date object.
     * @throws DukeException if the String is of incorrect format.
     */
    public static Date convertStringToDate(String str) throws DukeException {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HHmm");
        try {
            return dateFormat.parse(str);
        } catch (ParseException e) {
            throw new DukeException("Please enter date in correct format: dd-mm-yyyy hhmm. e.g. 18-12-1999 1800.");
        }
    }
}
