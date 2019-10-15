package parser;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This DateTimeExtractor class allows the identification of dates and is used
 * to print out dates and times.
 *
 * @author Sai Ganesh Suresh
 * @version v2.0
 */
public class DateTimeExtractor {

    public static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
    private static LocalDateTime dateEvent;
    private static LocalDateTime dateDeadline;
    private static LocalDateTime datePostpone;
    private static LocalDateTime dateTodo;
    private static LocalDateTime dateView;
    private static final LocalDateTime dateError = null;

    /**
     * This function appends the end time of an event to provide a standardised
     * output of date and time.
     *
     * @param dateTimeFromUser A string containing only the date segment of the user
     *                         input.
     * @return String A string containing the end time of an event will also returned.
     * @throws ParseException The ParseException is called if the date or time
     *                        format provided by the user is incorrect!
     */
    public static LocalDateTime extractDateTime(String dateTimeFromUser, String command) throws ParseException {
        if (command.equals("event")) {
            dateEvent = LocalDateTime.parse(dateTimeFromUser, DATE_FORMATTER);
            return dateEvent;
        } else if (command.equals("todo")) {
            dateTodo = LocalDateTime.parse(dateTimeFromUser, DATE_FORMATTER);
            return dateTodo;
        } else if (command.equals("deadline")) {
            dateDeadline = LocalDateTime.parse(dateTimeFromUser, DATE_FORMATTER);
            return dateDeadline;
        } else if (command.equals("postpone")) {
            datePostpone = LocalDateTime.parse(dateTimeFromUser, DATE_FORMATTER);
            return datePostpone;
        } else if (command.equals("view")) {
            dateView = LocalDateTime.parse(dateTimeFromUser, DATE_FORMATTER);
            return dateView;
        }
        // Allows the developer to know that a command other than deadline or event was
        // passed to the function!
        return dateError;
    }
}
