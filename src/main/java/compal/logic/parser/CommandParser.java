package compal.logic.parser;

import compal.logic.command.Command;
import compal.model.tasks.Task;
import compal.logic.parser.exceptions.ParserException;

import java.text.DateFormat;
import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static compal.commons.Messages.MESSAGE_MISSING_DESC;
import static compal.commons.Messages.MESSAGE_MISSING_PRIORITY;
import static compal.commons.Messages.MESSAGE_MISSING_DATE;
import static compal.commons.Messages.MESSAGE_MISSING_DATE_ARG;
import static compal.commons.Messages.MESSAGE_INVALID_DATE_FORMATTING;
import static compal.commons.Messages.MESSAGE_INVALID_YEAR;
import static compal.commons.Messages.MESSAGE_MISSING_TIME;
import static compal.commons.Messages.MESSAGE_MISSING_END_TIME_ARG;

public interface CommandParser {

    String TOKEN_TASK_ID = "/id";
    String TOKEN_STATUS = "/s";
    String TOKEN_SLASH = "/";
    String TOKEN_END_TIME = "/end";
    String TOKEN_DATE = "/date";
    String TOKEN_PRIORITY = "/priority";
    String EMPTY_INPUT_STRING = "";

    String MESSAGE_MISSING_TOKEN = "Error: Missing token!";
    String MESSAGE_MISSING_INPUT = "Error: Missing input!";
    String MESSAGE_INVALID_DATE_FORMAT = "Invalid Date input !";


    Command parseCommand(String input) throws ParserException, ParseException;

    /**
     * Returns the task ID in the String input.
     *
     * @param input String input of user
     * @return taskID
     * @throws ParserException if the token (/id) or id number is missing
     */
    default int getTaskID(String input) throws ParserException {
        if (input.contains(TOKEN_TASK_ID)) {
            int startPoint = input.indexOf(TOKEN_TASK_ID);
            String taskIdInput = input.substring(startPoint);
            Scanner scanner = new Scanner(taskIdInput);
            scanner.next();
            if (!scanner.hasNext()) {
                throw new ParserException(MESSAGE_MISSING_INPUT);
            }
            int taskId = Integer.parseInt(scanner.next());
            return taskId;
        } else {
            throw new ParserException(MESSAGE_MISSING_TOKEN);
        }
    }

    /**
     * Returns the reminder status in the String input.
     *
     * @param input String input of user
     * @return reminder status
     * @throws ParserException if the token (/s) or reminder status is missing
     */
    default String getStatus(String input) throws ParserException {
        if (input.contains(TOKEN_STATUS)) {
            int startPoint = input.indexOf(TOKEN_STATUS);
            String statusInput = input.substring(startPoint);
            Scanner scanner = new Scanner(statusInput);
            scanner.next();
            if (!scanner.hasNext()) {
                throw new ParserException(MESSAGE_MISSING_INPUT);
            }
            String status = scanner.next();
            return status;
        } else {
            throw new ParserException(MESSAGE_MISSING_TOKEN);
        }
    }

    /**
     * Returns the description in the String input.
     *
     * @param input String input of user
     * @return description
     * @throws ParserException if the token (/) is missing or the description is empty
     */
    default String getDescription(String input) throws ParserException {
        if (!input.contains(TOKEN_SLASH)) {
            throw new ParserException(MESSAGE_MISSING_INPUT);
        }
        int splitPoint = input.indexOf(TOKEN_SLASH);
        String desc = input.substring(0, splitPoint).trim();
        if (desc.matches(EMPTY_INPUT_STRING)) {
            throw new ParserException(MESSAGE_MISSING_DESC);
        }
        return desc;
    }

    /**
     * Returns a priority string if specified in the task.
     *
     * @param input Input description after initial command word.
     * @return Priority of task.
     * @throws ParserException If priority field is empty.
     */
    default Task.Priority getPriority(String input) throws ParserException {
        if (input.contains(TOKEN_PRIORITY)) {
            int startPoint = input.indexOf(TOKEN_PRIORITY);
            String priorityStartInput = input.substring(startPoint);
            Scanner scanner = new Scanner(priorityStartInput);
            scanner.next();
            if (!scanner.hasNext()) {
                throw new ParserException(MESSAGE_MISSING_PRIORITY);
            }
            String priorityInput = scanner.next();
            Task.Priority priority = Task.Priority.valueOf(priorityInput.toLowerCase());
            return priority;
        } else {
            Task.Priority priority = Task.Priority.low;
            return priority;
        }
    }

    /**
     * Returns a date string if specified in the task.
     *
     * @param restOfInput Input description after initial command word.
     * @return Date in the form of a string.
     * @throws ParserException If date field is empty, date or date format is invalid,
     *                              date token (/date) is missing.
     */
    default String getDate(String restOfInput) throws ParserException {
        if (restOfInput.contains(TOKEN_DATE)) {
            int startPoint = restOfInput.indexOf(TOKEN_DATE);
            String dateStartInput = restOfInput.substring(startPoint);
            Scanner scanner = new Scanner(dateStartInput);
            scanner.next();
            if (!scanner.hasNext()) {
                throw new ParserException(MESSAGE_MISSING_DATE);
            }
            String dateInput = scanner.next();
            return inputDateValidation(dateInput);
        } else {
            throw new ParserException(MESSAGE_MISSING_DATE_ARG);
        }
    }

    /**
     * Parses through each date string input by the user and makes sure it is of the correct format.
     *
     * @param inputDateStr The date string input by the user, which may not be of correct format.
     * @return A date string with its format validated.
     * @throws ParserException If date format is invalid (not dd/MM/yyyy), year is before current year.
     */
    default String inputDateValidation(String inputDateStr) throws ParserException {
        String regex = "^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(inputDateStr);

        if (!matcher.matches()) {
            throw new ParserException(MESSAGE_INVALID_DATE_FORMATTING);
        }
        int inputSize = inputDateStr.length();

        String year = inputDateStr.substring(inputSize - 4, inputSize);
        int inputYear = Integer.parseInt(year);
        int currYear = Calendar.getInstance().get(Calendar.YEAR);

        if (inputYear < currYear) {
            throw new ParserException(MESSAGE_INVALID_YEAR);
        }
        return inputDateStr;
    }

    /**
     * Checks if input date and time is after current date time.
     *
     * @param inputDate The date of input
     * @param inputTime the time of input
     * @return True or false depending if the date and time is after or before.
     */
    default boolean isValidDateAndTime(String inputDate, String inputTime) throws ParseException {

        Calendar c = Calendar.getInstance();
        Date inputDateFormat = new SimpleDateFormat("dd/MM/yyyy").parse(inputDate);
        c.setTime(inputDateFormat);
        c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(inputTime.substring(0, 2)));
        c.set(Calendar.MINUTE, Integer.parseInt(inputTime.substring(2, 4)));
        Date inputDateAndTime = c.getTime();

        Date currentDate = Calendar.getInstance().getTime();
        c.setTime(currentDate);
        Date currDateAndTime = c.getTime();

        return inputDateAndTime.after(currDateAndTime);
    }

    /**
     * Returns end time string if specified in the task.
     *
     * @param restOfInput Input description after initial command word.
     * @return Time in the form of a string.
     * @throws ParserException If time field is empty or time token (/eTime) is missing.
     */
    default String getEndTime(String restOfInput) throws ParserException {
        if (restOfInput.contains(TOKEN_END_TIME)) {
            int startPoint = restOfInput.indexOf(TOKEN_END_TIME);
            String dateStartInput = restOfInput.substring(startPoint);
            Scanner scanner = new Scanner(dateStartInput);
            scanner.next();
            if (!scanner.hasNext()) {
                throw new ParserException(MESSAGE_MISSING_TIME);
            }
            String timeInput = scanner.next();
            return timeInput;
        } else {
            throw new ParserException(MESSAGE_MISSING_END_TIME_ARG);
        }
    }

    /**
     * Check if the date input is of valid format.
     *
     * @param date the string of the date input
     * @return true or false.
     */
    default boolean isDateValid(String date) throws ParserException {
        final  String DATE_FORMAT = "dd/MM/yyyy";
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            throw new ParserException(MESSAGE_INVALID_DATE_FORMAT);
        }
    }
}