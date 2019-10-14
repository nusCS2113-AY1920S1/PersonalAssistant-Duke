package compal.logic.commands;

import compal.commons.Compal;
import compal.model.tasks.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static compal.commons.Messages.MESSAGE_INVALID_DATE_FORMATTING;
import static compal.commons.Messages.MESSAGE_INVALID_YEAR;
import static compal.commons.Messages.MESSAGE_MISSING_COMMAND_ARG;
import static compal.commons.Messages.MESSAGE_MISSING_DATE;
import static compal.commons.Messages.MESSAGE_MISSING_DATE_ARG;
import static compal.commons.Messages.MESSAGE_MISSING_DESC;
import static compal.commons.Messages.MESSAGE_MISSING_END_TIME_ARG;
import static compal.commons.Messages.MESSAGE_MISSING_PRIORITY;
import static compal.commons.Messages.MESSAGE_MISSING_START_TIME_ARG;
import static compal.commons.Messages.MESSAGE_MISSING_TIME;

/**
 * Extracts and formats user input string into description, priority, date and time.
 * Includes input validations to ensure that user input string is in valid format.
 */
public abstract class Command {

    public static final String TOKEN_SLASH = "/";
    public static final String TOKEN_START_TIME = "/start";
    public static final String TOKEN_END_TIME = "/end";
    public static final String TOKEN_DATE = "/date";
    private static final String TOKEN_PRIORITY = "/priority";
    private static final String EMPTY_INPUT_STRING = "";
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
        if (desc.matches(EMPTY_INPUT_STRING)) {
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
        if (restOfInput.contains(TOKEN_DATE)) {
            int startPoint = restOfInput.indexOf(TOKEN_DATE);
            String dateStartInput = restOfInput.substring(startPoint);
            Scanner scanner = new Scanner(dateStartInput);
            scanner.next();
            if (!scanner.hasNext()) {
                compal.ui.printg(MESSAGE_MISSING_DATE);
                throw new Compal.DukeException(MESSAGE_MISSING_DATE);
            }
            String dateInput = scanner.next();
            return inputDateValidation(dateInput);
        } else {
            compal.ui.printg(MESSAGE_MISSING_DATE_ARG);
            throw new Compal.DukeException(MESSAGE_MISSING_DATE_ARG);
        }
    }


    /**
     * Checks if input date and time is after current date time.
     *
     * @param inputDate The date of input
     * @param inputTime the time of input
     * @return True or false depending if the date and time is after or before.
     */
    public boolean isValidDateAndTime(String inputDate, String inputTime) throws ParseException {

        Calendar c = Calendar.getInstance();
        Date inputDateFormat = new SimpleDateFormat("dd/MM/yyyy").parse(inputDate);
        c.setTime(inputDateFormat);
        c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(inputTime.substring(0, 2)));
        c.set(Calendar.MINUTE, Integer.parseInt(inputTime.substring(2,4)));
        Date inputDateAndTime = c.getTime();

        Date currentDate = Calendar.getInstance().getTime();
        c.setTime(currentDate);
        Date currDateAndTime = c.getTime();

        return inputDateAndTime.after(currDateAndTime);
    }

    /**
     * Parses through each date string input by the user and makes sure it is of the correct format.
     *
     * @param inputDateStr The date string input by the user, which may not be of correct format.
     * @return A date string with its format validated.
     * @throws Compal.DukeException If date format is invalid (not dd/MM/yyyy), year is before current year.
     */
    public String inputDateValidation(String inputDateStr) throws Compal.DukeException {
        String regex = "^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(inputDateStr);

        if (!matcher.matches()) {
            compal.ui.printg(MESSAGE_INVALID_DATE_FORMATTING);
            throw new Compal.DukeException(MESSAGE_INVALID_DATE_FORMATTING);
        }
        int inputSize = inputDateStr.length();

        String year = inputDateStr.substring(inputSize - 4, inputSize);
        int inputYear = Integer.parseInt(year);
        int currYear = Calendar.getInstance().get(Calendar.YEAR);

        if (inputYear < currYear) {
            compal.ui.printg(MESSAGE_INVALID_YEAR);
            throw new Compal.DukeException(MESSAGE_INVALID_YEAR);
        }
        return inputDateStr;
    }

    /**
     * Converts a date string to a Date object.
     *
     * @param dateStr The date string to be converted.
     * @return The date string in the form of a Date object.
     */
    public Date stringToDate(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * Converts a Date object to a date string. Correct type for creating a Task object.
     *
     * @param date The date in the form of a Date object.
     * @return The date in the form of a String object.
     */
    public String dateToString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(date);
    }

    /**
     * Returns start time string if specified in the task.
     *
     * @param restOfInput Input description after initial command word.
     * @return Time in the form of a string.
     * @throws Compal.DukeException If time field is empty or time token (/sTime) is missing.
     */
    public String getStartTime(String restOfInput) throws Compal.DukeException {
        if (restOfInput.contains(TOKEN_START_TIME)) {
            int startPoint = restOfInput.indexOf(TOKEN_START_TIME);
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
            compal.ui.printg(MESSAGE_MISSING_START_TIME_ARG);
            throw new Compal.DukeException(MESSAGE_MISSING_START_TIME_ARG);
        }
    }

    /**
     * Returns end time string if specified in the task.
     *
     * @param restOfInput Input description after initial command word.
     * @return Time in the form of a string.
     * @throws Compal.DukeException If time field is empty or time token (/eTime) is missing.
     */
    public String getEndTime(String restOfInput) throws Compal.DukeException {
        if (restOfInput.contains(TOKEN_END_TIME)) {
            int startPoint = restOfInput.indexOf(TOKEN_END_TIME);
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
            compal.ui.printg(MESSAGE_MISSING_END_TIME_ARG);
            throw new Compal.DukeException(MESSAGE_MISSING_END_TIME_ARG);
        }
    }

    /**
     * Returns a priority string if specified in the task.
     *
     * @param restOfInput Input description after initial command word.
     * @return Priority of task.
     * @throws Compal.DukeException If priority field is empty or priority token (/priority) is missing.
     */
    public Task.Priority getPriority(String restOfInput) throws Compal.DukeException {
        if (restOfInput.contains(TOKEN_PRIORITY)) {
            int startPoint = restOfInput.indexOf(TOKEN_PRIORITY);
            String priorityStartInput = restOfInput.substring(startPoint);
            Scanner scanner = new Scanner(priorityStartInput);
            scanner.next();
            if (!scanner.hasNext()) {
                compal.ui.printg(MESSAGE_MISSING_PRIORITY);
                throw new Compal.DukeException(MESSAGE_MISSING_PRIORITY);
            }
            String priorityInput = scanner.next();
            Task.Priority priority = Task.Priority.valueOf(priorityInput.toLowerCase());
            return priority;
        } else {
            /* compal.ui.printg(MESSAGE_MISSING_PRIORITY_ARG);
            throw new Compal.DukeException(MESSAGE_MISSING_PRIORITY_ARG); */
            Task.Priority priority = Task.Priority.low;
            return priority;
        }
    }

}
