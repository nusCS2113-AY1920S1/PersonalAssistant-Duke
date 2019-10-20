package compal.logic.parser;

import compal.logic.command.Command;
import compal.model.tasks.Task;
import compal.logic.parser.exceptions.ParserException;
import compal.model.tasks.Task;

import javax.swing.text.html.parser.Parser;
import java.text.DateFormat;
import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
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

    /**
     * TOKENS FOR PARSING BELOW
     */
    String TOKEN_TASK_ID = "/id";
    String TOKEN_STATUS = "/s";
    String TOKEN_SLASH = "/";
    String TOKEN_END_TIME = "/end";
    String TOKEN_DATE = "/date";
    String TOKEN_PRIORITY = "/priority";
    String TOKEN_START_TIME = "/start";
    String TOKEN_FINAL_DATE = "/final-date";
    String EMPTY_INPUT_STRING = "";
    int DEFAULT_WEEK_NUMBER_OF_DAYS = 7;

    /**
     * ERROR MESSAGES BELOW
     */
    String MESSAGE_MISSING_TOKEN = "Error: Missing token!";
    String MESSAGE_MISSING_INPUT = "Error: Missing input!";
    String MESSAGE_INVALID_DATE_FORMAT = "Invalid Date input!";
    String MESSAGE_MISSING_DATE_ARG = "ArgumentError: Missing /date";
    String MESSAGE_EXCESSIVE_DATES = "Too many dates! Please limit to less than 7.";
    String MESSAGE_MISSING_START_TIME_ARG = "ArgumentError: Missing /start";
    String MESSAGE_MISSING_END_TIME_ARG = "ArgumentError: Missing /end";
    String MESSAGE_MISSING_FINAL_DATE_ARG = "ArgumentError: Missing /final-date";

    /**
     * Method specification for different command parsers to parse user input.
     *
     * @param restOfInput String input of user after command word
     * @return a suitable Command object that will carry out the user's intention.
     * @throws ParserException Invalid input, varies for each command parser.
     */
    Command parseCommand(String restOfInput) throws ParserException;

    /**
     * GETTERS FOR TOKENS BELOW
     */

    /**
     * Returns the task ID in the String input.
     *
     * @param restOfInput String input of user after command word
     * @return taskID
     * @throws ParserException if the token (/id) or id number is missing
     */
    default int getTokenTaskID(String restOfInput) throws ParserException {
        if (restOfInput.contains(TOKEN_TASK_ID)) {
            int startPoint = restOfInput.indexOf(TOKEN_TASK_ID);
            String taskIdStartInput = restOfInput.substring(startPoint);
            Scanner scanner = new Scanner(taskIdStartInput);
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
     * @param restOfInput String input of user after command word
     * @return reminder status
     * @throws ParserException if the token (/s) or reminder status is missing
     */
    default String getTokenStatus(String restOfInput) throws ParserException {
        if (restOfInput.contains(TOKEN_STATUS)) {
            int startPoint = restOfInput.indexOf(TOKEN_STATUS);
            String statusStartInput = restOfInput.substring(startPoint);
            Scanner scanner = new Scanner(statusStartInput);
            scanner.next();
            if (!scanner.hasNext()) {
                throw new ParserException(MESSAGE_MISSING_INPUT);
            }
            String statusField = scanner.next();
            return statusField;
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
          
     * Parses through user input for date token, and returns the date if the date is
     * in the correct format.
     *
     * @param restOfInput String input of user after command word
     * @return An ArrayList of date strings given by the user.
     * @throws ParserException if the date token is missing, if the date is not in correct format,
     * if the date is not given after the date token.
     */
    default ArrayList<String> getTokenDate(String restOfInput) throws ParserException {
        if (restOfInput.contains(TOKEN_DATE)) {
            int startPoint = restOfInput.indexOf(TOKEN_DATE);
            String dateStartInput = restOfInput.substring(startPoint);
            Scanner scanner = new Scanner(dateStartInput);
            scanner.next();
            if (!scanner.hasNext()) {
                throw new ParserException(MESSAGE_MISSING_INPUT);
            }
            ArrayList<String> startDateList = new ArrayList<>();
            int dateCount = 0;
            while (scanner.hasNext()) {
                String eachDateString = scanner.next();
                dateCount++;
                if (dateCount == DEFAULT_WEEK_NUMBER_OF_DAYS) {
                    throw new ParserException(MESSAGE_EXCESSIVE_DATES);
                }
                if (isDateValid(eachDateString)) {
                    startDateList.add(eachDateString);
                } else {
                    throw new ParserException(MESSAGE_INVALID_DATE_FORMAT);
                }
            }
            return startDateList;
        } else {
            throw new ParserException(MESSAGE_MISSING_DATE_ARG);
        }
    }

    /**
     * Parses through user input for priority token, and returns the enum priority if present.
     *
     * @param restOfInput String input of user after command word
     * @return Task.Priority enum
     * @throws ParserException if the priority is not given after the priority token
     */
    default Task.Priority getTokenPriority(String restOfInput) throws ParserException {
        Task.Priority priorityField;
        if (restOfInput.contains(TOKEN_PRIORITY)) {
            int startPoint = restOfInput.indexOf(TOKEN_PRIORITY);
            String priorityStartInput = restOfInput.substring(startPoint);
            Scanner scanner = new Scanner(priorityStartInput);
            scanner.next();
            if (!scanner.hasNext()) {
                throw new ParserException(MESSAGE_MISSING_INPUT);
            }
            String commandPriority = scanner.next();
            priorityField = Task.Priority.valueOf(commandPriority.toLowerCase());
        } else {
            priorityField = Task.Priority.low;
        }
        return priorityField;
    }

    /**
     * Parses through user input for /start token and return the start time.
     * @param restOfInput String input of user after command word
     * @return Start time in the form of a String
     * @throws ParserException if start time is not entered after the /start token, or /start token is missing
     * @author: Yue Jun Yi, yueyeah
     */
    default String getTokenStartTime(String restOfInput) throws ParserException {
        if (restOfInput.contains(TOKEN_START_TIME)) {
            int startPoint = restOfInput.indexOf(TOKEN_START_TIME);
            String startTimeStartInput = restOfInput.substring(startPoint);
            Scanner scanner = new Scanner(startTimeStartInput);
            scanner.next();
            if (!scanner.hasNext()) {
                throw new ParserException(MESSAGE_MISSING_INPUT);
            }
            String startTimeField = scanner.next();
            return startTimeField;
        } else {
            throw new ParserException(MESSAGE_MISSING_START_TIME_ARG);
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

     * Parses through user input for /end token and return the end time.
     *
     * @param restOfInput String input of user after command word
     * @return End time in the form of a String
     * @throws ParserException if end time is not entered after the /end token, or /end token is missing
     */
    default String getTokenEndTime(String restOfInput) throws ParserException {
        if (restOfInput.contains(TOKEN_END_TIME)) {
            int startPoint = restOfInput.indexOf(TOKEN_END_TIME);
            String endTimeStartInput = restOfInput.substring(startPoint);
            Scanner scanner = new Scanner(endTimeStartInput);
            scanner.next();
            if (!scanner.hasNext()) {
                throw new ParserException(MESSAGE_MISSING_INPUT);
            }
            String endTimeField = scanner.next();
            return endTimeField;
        } else {
            throw new ParserException(MESSAGE_MISSING_END_TIME_ARG);
        }
    }

    /**
     * Parses through the user input for /final-date token and return the final date of iteration of events/deadline.
     * The presence of the /final-date token must be checked first in the specialised
     * command parser calling this method.
     *
     * @param restOfInput String input of user after command word
     * @return Final date of iteration in the form of a String
     * @throws ParserException if final date is not entered after the /final-date token
     * @author Yue Jun Yi, yueyeah
     */
    default String getTokenFinalDate(String restOfInput) throws ParserException {
        int startPoint = restOfInput.indexOf(TOKEN_FINAL_DATE);
        String finalDateStartInput = restOfInput.substring(startPoint);
        Scanner scanner = new Scanner (finalDateStartInput);
        scanner.next();
        if (!scanner.hasNext()) {
            throw new ParserException(MESSAGE_MISSING_INPUT);
        }
        String finalDateField = scanner.next();
        if (isDateValid(finalDateField)) {
            return finalDateField
        } else {
            throw new ParserException(MESSAGE_INVALID_DATE_FORMAT);
        }
    }

    /**
     * MISCELLANEOUS METHODS BELOW
     */


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

    /**
     * Check if the user input contains the token.
     *
     * @param restOfInput String input of user after command word
     * @param token The token to be checked
     * @return True if the token exists in the user input, False if not.
     */
    default boolean hasToken(String restOfInput, String token) {
        return (restOfInput.contains(token));
    }
}