package duke.logic.parser.commons;

import duke.commons.core.Message;
import duke.logic.parser.exceptions.ParseException;
import org.ocpsoft.prettytime.PrettyTime;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Parser to parse time.
 * Convert between date object and String date.
 * TODO: explain in detail with examples.
 */
public class TimeParser {
    private static PrettyTime prettyTime = new PrettyTime();
    private static PrettyTimeParser prettyTimeParser = new PrettyTimeParser();
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy HH:mm");

    /**
     * Converts a Date object to a human-friendly string representing the date.
     *
     * @param date Date object
     * @return a human-friendly string representing the date
     */
    public static String convertDateToString(Date date) {
        //TODO: remove magic number
        if (Math.abs(date.getTime() - System.currentTimeMillis()) > 1000 * 3600 * 24 * 5) {
            return dateFormat.format(date);
        } else {
            return prettyTime.format(date);
        }
    }


    /**
     * Converts a string representing the date to a Date object.
     *
     * @param str a string representing the date.
     * @return the date based on the string.
     * @throws ParseException if cannot parse the string into a date.
     */
    public static Date convertStringToDate(String str) throws ParseException {
        List<Date> dates = prettyTimeParser.parse(str);
        if (dates.isEmpty()) {
            throw new ParseException(Message.MESSAGE_INVALID_DATE);
        }
        return dates.get(0);
    }
}
