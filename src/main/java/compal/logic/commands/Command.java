package compal.logic.commands;

import compal.main.Duke;

import java.util.Calendar;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public String getDescription(String restOfInput) throws Duke.DukeException {
        if (!restOfInput.contains(TOKEN_SLASH)) {
            duke.ui.printg("ArgumentError: Missing date or time!");
            throw new Duke.DukeException("ArgumentError: Missing date or time!");
        }
        int splitPoint = restOfInput.indexOf(TOKEN_SLASH);
        String desc = restOfInput.substring(0, splitPoint).trim();
        if (desc.matches(" ")) {
            duke.ui.printg("DescError: Description field cannot be empty. Please enter a description");
            throw new Duke.DukeException("DescError: Description field cannot be empty. Please enter a description.");
        }
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
            if (!scanner.hasNext()) {
                duke.ui.printg("MissingDateError: Date field cannot be empty. Please enter a valid date.");
                throw new Duke.DukeException("MissingDateError: Date field cannot be empty. Please enter a valid date.");
            }
            String date_input = scanner.next();

            String regex = "^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(date_input);

            if (matcher.matches() == false) {
                duke.ui.printg("DateFormattingError: Date format input is invalid! Please make sure is dd/mm/yyyy format.");
                throw new Duke.DukeException("DateFormattingError: Date format input is invalid! Please make sure is dd/mm/yyyy format.");
            }
            int inputSize=date_input.length();

            String year = date_input.substring(inputSize-4,inputSize);
            int inputYear = Integer.parseInt(year);
            int currYear = Calendar.getInstance().get(Calendar.YEAR);

            if(inputYear < currYear){
                duke.ui.printg("YearRangeError: You can only put input schedule of the "+ currYear + " onwards!");
                throw new Duke.DukeException("YearRangeError: You can only put input schedule of the "+ currYear + " onwards!");
            }
            return date_input;
        } else {
            duke.ui.printg("ArgumentError: Missing /date");
            throw new Duke.DukeException("ArgumentError: Missing /date");
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
            int startPoint = restOfInput.indexOf(TIME_TOKEN);
            String dateStartInput = restOfInput.substring(startPoint);
            Scanner scanner = new Scanner(dateStartInput);
            scanner.next();
            if (!scanner.hasNext()) {
                duke.ui.printg("MissingTimeError: Time field cannot be empty. Please enter a valid time.");
                throw new Duke.DukeException("MissingTimeError: Time field cannot be empty. Please enter a valid time.");
            }
            String time_input = scanner.next();
            return time_input;
        } else {
            duke.ui.printg("ArgumentError: Missing /time");
            throw new Duke.DukeException("ArgumentError: Missing /time");
        }
    }
}
