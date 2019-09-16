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
    private static String dateEvent;
    private static String dateDeadline;
    private static final Integer END_TIME_INDEX_FIRST = 16;
    private static final Integer END_TIME_INDEX_LAST = 20;
    private static final Integer INDEX_TO_INSERT_END_TIME = 18;
    private static final String dateUnknown = "00/00/0000";
    /**
     * This function appends the end time of an event to provide a standardised output of date and time.
     *
     * @param dateTimeFromUser A string containing only the date segment of the user input.
     * @return String A string containing the end time of an event will also returned.
     * @throws ParseException The ParseException is called if the date or time format provided by the user is incorrect!
     *
     */
    public static String extractDateTime(String dateTimeFromUser, String command) throws ParseException {

        if(command.equals("event")) {
            return eventDateAndDuration(dateTimeFromUser);
        }
        else if(command.equals("deadline")) {
            dateDeadline = (DATE_FORMATTER.parse(dateTimeFromUser)).toString();
            return dateDeadline;
        }
        // Allows the developer to know that a command other than deadline or event was passed to the function!
        return dateUnknown;
    }

    /**
     * Appends the end time of an event to provide a standardised output of date and time.
     *
     * @param dateTimeFromUser A string containing only the date segment of the user input.
     * @return String A string containing the end time of an event will also returned.
     * @throws ParseException The ParseException is called if the date or time format provided by the user is incorrect!
     *
     */
    public static String eventDateAndDuration (String dateTimeFromUser) throws ParseException{
        dateEvent = (DATE_FORMATTER.parse(dateTimeFromUser)).toString();
        String finalDateEvent = new String();
        // After processing and adding the task this is a definite location of the end time of an event.
        String endTime = dateTimeFromUser.substring(END_TIME_INDEX_FIRST,END_TIME_INDEX_LAST);
        // This for loop allows the insertion of the desired string into the new string.
        for (int i = 0; i < dateEvent.length(); i++) {
            finalDateEvent += dateEvent.charAt(i);
            if (i == INDEX_TO_INSERT_END_TIME) {
                finalDateEvent  += "-" + endTime.charAt(0) + endTime.charAt(1)+
                        ":"+ endTime.charAt(2) + endTime.charAt(3)+":00";
            }
        }
        return finalDateEvent;
    }
    /**
     * Extracts a Date from the given user input. (Can be utilised in the future)
     *
     * @param dateFromUser A string containing only the date segment of the user input.
     * @return Date A Date will be returned to the called after processing the user's date input.
     *
     */
    public Date ObtainDate(String dateFromUser) {
        Date date_needed = new Date();
        try {
            date_needed = new SimpleDateFormat("dd/MM/yyyy").parse(dateFromUser);
            return date_needed;
        }
        catch (ParseException e)
        {
            DukeException.WRONG_DATE_OR_TIME();
        }
        return date_needed;
    }
}
