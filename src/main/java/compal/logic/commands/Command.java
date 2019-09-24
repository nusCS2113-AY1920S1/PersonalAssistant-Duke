package compal.logic.commands;

import compal.compal.Compal;

import java.util.Calendar;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static compal.compal.Messages.MESSAGE_MISSING_COMMAND_ARG;
import static compal.compal.Messages.MESSAGE_INVALID_DATE_FORMATTING;
import static compal.compal.Messages.MESSAGE_INVALID_YEAR;
import static compal.compal.Messages.MESSAGE_MISSING_DATE;
import static compal.compal.Messages.MESSAGE_MISSING_DATE_ARG;
import static compal.compal.Messages.MESSAGE_MISSING_DESC;
import static compal.compal.Messages.MESSAGE_MISSING_TIME;
import static compal.compal.Messages.MESSAGE_MISSING_TIME_ARG;

/**
 * Extracts and formats user input string into description, date and time.
 * Includes input validations to ensure that user input string is in valid format.
 */
public abstract class Command {

    public static final String TOKEN_SLASH = "/";
    public static final String TIME_TOKEN = "/time";
    public static final String DATE_TOKEN = "/date";
    public Compal compal;

    /**
     * Constructs a Command object.
     *
     * @param d Compal object
     */
    public Command(Compal d) {
        this.compal = d;
    }

    /**
     * Returns description from the input string according to the token (/at or /by etc).
     *
     * @param restOfInput Input description after initial command word.
     * @return Description without date and time.
     * @throws Compal.DukeException If restOfInput is missing date, time or description field.
     */
    public String getDescription(String restOfInput) throws Compal.DukeException {
        if (!restOfInput.contains(TOKEN_SLASH)) {
            compal.ui.printg(MESSAGE_MISSING_COMMAND_ARG);
            throw new Compal.DukeException(MESSAGE_MISSING_COMMAND_ARG);
        }
        int splitPoint = restOfInput.indexOf(TOKEN_SLASH);
        String desc = restOfInput.substring(0, splitPoint).trim();
        if (desc.matches("")) {
            compal.ui.printg(MESSAGE_MISSING_DESC);
            throw new Compal.DukeException(MESSAGE_MISSING_DESC);
        }
        return desc;
    }

    /**
     * Returns a date string if specified in the task.
     *
     * @param restOfInput Input description after initial command word.
     * @return Date in the form of a string.
     * @throws Compal.DukeException If date field is empty, date or date format is invalid,
     *                              date token (/date) is missing.
     */
    public String getDate(String restOfInput) throws Compal.DukeException {
        if (restOfInput.contains(DATE_TOKEN)) {
            int startPoint = restOfInput.indexOf(DATE_TOKEN);
            String dateStartInput = restOfInput.substring(startPoint);
            Scanner scanner = new Scanner(dateStartInput);
            scanner.next();
            if (!scanner.hasNext()) {
                compal.ui.printg(MESSAGE_MISSING_DATE);
                throw new Compal.DukeException(MESSAGE_MISSING_DATE);
            }
            String dateInput = scanner.next();

            String regex = "^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(dateInput);

            if (matcher.matches() == false) {
                compal.ui.printg(MESSAGE_INVALID_DATE_FORMATTING);
                throw new Compal.DukeException(MESSAGE_INVALID_DATE_FORMATTING);
            }
            int inputSize = dateInput.length();

            String year = dateInput.substring(inputSize - 4, inputSize);
            int inputYear = Integer.parseInt(year);
            int currYear = Calendar.getInstance().get(Calendar.YEAR);

            if (inputYear < currYear) {
                compal.ui.printg(MESSAGE_INVALID_YEAR);
                throw new Compal.DukeException(MESSAGE_INVALID_YEAR);
            }
            return dateInput;
        } else {
            compal.ui.printg(MESSAGE_MISSING_DATE_ARG);
            throw new Compal.DukeException(MESSAGE_MISSING_DATE_ARG);
        }
    }

    /**
     * Returns a time string if specified in the task.
     *
     * @param restOfInput Input description after initial command word.
     * @return Time in the form of a string.
     * @throws Compal.DukeException If time field is empty or time token (/time) is missing.
     */
    public String getTime(String restOfInput) throws Compal.DukeException {
        if (restOfInput.contains(TIME_TOKEN)) {
            int startPoint = restOfInput.indexOf(TIME_TOKEN);
            String dateStartInput = restOfInput.substring(startPoint);
            Scanner scanner = new Scanner(dateStartInput);
            scanner.next();
            if (!scanner.hasNext()) {
                compal.ui.printg(MESSAGE_MISSING_TIME);
                throw new Compal.DukeException(MESSAGE_MISSING_TIME);
            }
            String timeInput = scanner.next();
            return timeInput;
        } else {
            compal.ui.printg(MESSAGE_MISSING_TIME_ARG);
            throw new Compal.DukeException(MESSAGE_MISSING_TIME_ARG);
        }
    }
}
