package compal.logic.commands;

import compal.main.Duke;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.GregorianCalendar;

public abstract class Command {
    public static final String ERROR_DATE_STRING = "27/07/1987";
    public static final String ERROR_TIME_STRING = "TIME";
    public final String TOKEN_SLASH = "/";
    public final char sadFace = '\u2639';
    public final String TIME_TOKEN = "/time";
    public final String DATE_TOKEN = "/date";
    public String cmdString;
    public Duke duke;

    public Command(Duke d) {
        this.duke = d;
    }

    /**
     * This function builds a description from the description string according to the token (/at or /by etc).
     * Description string is the string before the token.
     *
     * @param restOfInput Input after the initial command word.
     * @return description
     */
    public String getDescription(String restOfInput) {
        int splitPoint = restOfInput.indexOf(TOKEN_SLASH);
        String desc = restOfInput.substring(TOKEN_SLASH.length()).trim();
        return desc;
    }

    /**
     * Returns a date string if specified in the task.
     *
     * @param restOfInput The part of the input after the command word.
     * @return Date in the form of a string.
     * @throws Duke.DukeException
     */
    public String getDate(String restOfInput) throws Duke.DukeException {
        if (restOfInput.contains(DATE_TOKEN)) {
            int startPoint = restOfInput.indexOf(DATE_TOKEN);
            String dateStartInput = restOfInput.substring(startPoint);
            Scanner scanner = new Scanner(dateStartInput);
            scanner.next();
            String date_input = scanner.next();
            return date_input;
        } else {
            throw new Duke.DukeException("Date field cannot be empty. Please enter a valid date.");
        }
    }

    /**
     * Returns a time string if specified in the task.
     *
     * @param restOfInput The part of the input after the command word.
     * @return Time in the form of a string.
     * @throws Duke.DukeException
     */
    public String getTime(String restOfInput) throws Duke.DukeException {
        if (restOfInput.contains(TIME_TOKEN)) {
            Scanner scanner = new Scanner(restOfInput);
            scanner.next();
            String time_input = scanner.next();
            return time_input;
        } else {
            throw new Duke.DukeException("Time field cannot be empty. Please enter a valid time.");
        }
    }
}
