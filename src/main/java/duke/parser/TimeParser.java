package duke.parser;

import duke.commons.DukeException;
import duke.commons.core.Message;
import duke.parser.exceptions.ParseException;
import org.ocpsoft.prettytime.PrettyTime;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Parse time. Convert between date object and String date.
 */
public class TimeParser {
    private static PrettyTime prettyTime = new PrettyTime();
    private static PrettyTimeParser prettyTimeParser = new PrettyTimeParser();
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy HH:mm");

    /**
     * Converts a Date object to a String representing the date.
     *
     * @param date Date object
     * @return a String representing the date.
     */
    public static String convertDateToString(Date date) {
        if (date.getTime() - System.currentTimeMillis() > 1000 * 3600 * 24 * 5) {
            return dateFormat.format(date);
        } else {
            return prettyTime.format(date);
        }
    }

    /**
     * Converts a String representing the date to a Date object.
     *
     * @param str a String representing the date. Must be in correct format: dd-MM-yyyy HHmm
     * @return a Date object.
     * @throws DukeException if the String is of incorrect format.
     */
    public static Date convertStringToDate(String str) throws ParseException {
        List<Date> dates = prettyTimeParser.parse(str);
        if (dates.isEmpty()) {
            throw new ParseException(Message.MESSAGE_INVALID_DATE);
        }
        return dates.get(0);
    }
}
