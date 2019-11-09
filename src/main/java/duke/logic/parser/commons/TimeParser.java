package duke.logic.parser.commons;

import duke.commons.core.Message;
import duke.logic.parser.exceptions.ParseException;
import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.Date;

//@@author liujiajun
/**
 * Parser to parse time.
 */
public class TimeParser {
    public static SimpleDateFormat displayDateFormat = new SimpleDateFormat("EEE, MMM d, yyyy HH:mm");
    public static SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private static PrettyTime prettyTime = new PrettyTime();

    /**
     * If the difference (in absolute number) between the time given and current dime is within
     * this threshold, TimeParser returns the human-friendly representation of time.
     * Otherwise, TimeParser returns time in format specified in {@code dateFormat}.
     */
    private static final long THRESHOLD_TIME_MILLIS = 1000 * 3600 * 24 * 5;


    /**
     * Converts a Date object to a string representing the date.
     *
     * @param date Date object to convert
     * @return a string representing the date
     */
    public static String convertDateToString(Date date) {
        if (Math.abs(date.getTime() - System.currentTimeMillis()) > THRESHOLD_TIME_MILLIS) {
            return displayDateFormat.format(date);
        } else {
            return prettyTime.format(date);
        }
    }


    /**
     * Converts a string representing the date to a Date object.
     * @param str a string representing the date. Must be in the format {@code dd/MM/yyyy HH:mm},
     *            for example, "30/10/1999 18:00" (in specified format),
     * @return the date based on the string.
     * @throws ParseException if the string cannot be parsed into a date.
     */
    public static Date convertStringToDate(String str) throws ParseException {
        try {
            inputDateFormat.setLenient(false);
            return inputDateFormat.parse(str);
        } catch (java.text.ParseException e) {
            throw new ParseException(Message.MESSAGE_INVALID_DATE);
        }
    }
}
