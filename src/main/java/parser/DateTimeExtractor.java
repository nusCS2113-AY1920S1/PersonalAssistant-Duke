package parser;
import exception.DukeException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This DateTimeExtractor class allows the identification of dates and is used to print out dates and times.
 *
 * @author Sai Ganesh Suresh
 * @version v2.0
 */
public class DateTimeExtractor {

    private static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd/MM/yyyy HHmm");
    private static Date dateEvent;
    private static Date dateDeadline;
    private static final Date dateError = new Date();

    /**
     * This function appends the end time of an event to provide a standardised output of date and time.
     *
     * @param dateTimeFromUser A string containing only the date segment of the user input.
     * @return String A string containing the end time of an event will also returned.
     * @throws ParseException The ParseException is called if the date or time format provided by the user is incorrect!
     *
     */
    public static Date extractDateTime(String dateTimeFromUser, String command) throws ParseException {

        if(command.equals("event")) {
            dateEvent = (DATE_FORMATTER.parse(dateTimeFromUser));
            return dateEvent;
        }
        else if(command.equals("deadline")) {
            dateDeadline = (DATE_FORMATTER.parse(dateTimeFromUser));
            return dateDeadline;
        }
        // Allows the developer to know that a command other than deadline or event was passed to the function!
        return dateError;
    }
}
