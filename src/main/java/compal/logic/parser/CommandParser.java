package compal.logic.parser;

import compal.commons.CompalUtils;
import compal.logic.command.Command;
import compal.model.tasks.Task;
import compal.logic.parser.exceptions.ParserException;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import java.util.HashSet;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public interface CommandParser {

    /**
     * TOKENS FOR PARSING BELOW.
     */
    String TOKEN_TASK_ID = "/id";
    String TOKEN_STATUS = "/status";
    String TOKEN_SLASH = "/";
    String TOKEN_END_TIME = "/end";
    String TOKEN_DATE = "/date";
    String TOKEN_PRIORITY = "/priority";
    String TOKEN_DESCRIPTION = "/description";
    String TOKEN_START_TIME = "/start";
    String TOKEN_FINAL_DATE = "/final-date";
    char TOKEN_SLASH_CHAR = '/';
    String TOKEN_TYPE = "/type";
    String TOKEN_HOUR = "/hour";
    String TOKEN_MIN = "/min";
    String TOKEN_INTERVAL = "/interval";
    String TOKEN_FILE_NAME = "/file-name";


    String EMPTY_INPUT_STRING = "";
    int DEFAULT_WEEK_NUMBER_OF_DAYS = 7;
    int INDEX_ZERO = 0;

    /**
     * ERROR MESSAGES BELOW.
     */
    String MESSAGE_MISSING_TOKEN = "Error: Missing token!";
    String MESSAGE_MISSING_INPUT = "Error: Missing input!";
    String MESSAGE_INVALID_TIME_FORMAT = "Invalid Time input!";
    String MESSAGE_INVALID_DATE_FORMAT = "Invalid Date input!";
    String MESSAGE_MISSING_DATE_ARG = "ArgumentError: Missing /date";
    String MESSAGE_EXCESSIVE_DATES = "Too many dates! Please limit to less than 7.";
    String MESSAGE_MISSING_START_TIME_ARG = "ArgumentError: Missing /start";
    String MESSAGE_MISSING_END_TIME_ARG = "ArgumentError: Missing /end";
    String MESSAGE_MISSING_STATUS_ARG = "ArgumentError: Missing /status";
    String MESSAGE_MISSING_ID_ARG = "ArgumentError: Missing /id";
    String MESSAGE_MISSING_TYPE_ARG = "ArgumentError: Missing /type";
    String MESSAGE_INVALID_TYPE = "Error: The type does not exist!";
    String MESSAGE_INVALID_PRIORITY = "Invalid priority input";
    String MESSAGE_LIMIT_EXCEEDED = "Error: Input entered is out of range!";
    String MESSAGE_INVALID_FILE_NAME_FORMAT = "Error: Invalid file name for export!";
    String MESSAGE_INVALID_FINAL_TIME = "Error: Invalid final date time";
    String MESSAGE_MISSING_FILE_NAME_ARG = "ArgumentError: Missing /file-name";
    String MESSAGE_MISSING_FILE_NAME = "Error: Missing file name input!";
    String MESSAGE_INVALID_INTERVAL = "Invalid interval input";
    String MESSAGE_INVALID_PARAM = "Looks like there's an invalid parameter inserted!";

    /**
     * Method specification for different command parsers to parse user input.
     *
     * @param restOfInput String input of user after command word
     * @return a suitable Command object that will carry out the user's intention.
     * @throws ParserException Invalid input, varies for each command parser.
     */
    Command parseCommand(String restOfInput) throws ParserException, ParseException;

    /**
     * GETTERS FOR TOKENS BELOW
     */

    //@@author SholihinK

    /**
     * Returns the type of task.
     *
     * @param restOfInput String input of user after command word
     * @return type
     * @throws ParserException if the token (/task) is missing or task type
     *                         does not exist.
     * @author Sholihin
     */
    default String getType(String restOfInput) throws ParserException {
        if (restOfInput.contains(TOKEN_TYPE)) {
            int startPoint = restOfInput.indexOf(TOKEN_TYPE);
            String typeStartInput = restOfInput.substring(startPoint);
            Scanner scanner = new Scanner(typeStartInput);
            scanner.next();
            if (!scanner.hasNext()) {
                throw new ParserException(MESSAGE_MISSING_INPUT);
            }
            String typeInput = scanner.next();

            Set<String> taskTypes = new HashSet<>();

            taskTypes.add("deadline");
            taskTypes.add("event");

            if (taskTypes.contains(typeInput)) {
                return typeInput;
            }

            throw new ParserException(MESSAGE_INVALID_TYPE);

        } else {
            throw new ParserException(MESSAGE_MISSING_TYPE_ARG);
        }
    }

    //@@author Catherinetan99

    /**
     * Returns the reminder status in the String input.
     *
     * @param restOfInput String input of user after command word
     * @return reminder status
     * @throws ParserException if the token (/status) or reminder status is missing
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
            throw new ParserException(MESSAGE_MISSING_STATUS_ARG);
        }
    }

    /**
     * Returns the hour in the String input.
     *
     * @param restOfInput String input of user after command word
     * @return hour
     * @throws ParserException if the token (/hour) or hour input is missing
     */
    default int getHour(String restOfInput) throws ParserException {
        return getIntInput(restOfInput, TOKEN_HOUR);
    }

    /**
     * Returns the min in the String input.
     *
     * @param restOfInput String input of user after command word
     * @return min
     * @throws ParserException if the token (/min) or min input is missing
     */
    default int getMin(String restOfInput) throws ParserException {
        return getIntInput(restOfInput, TOKEN_MIN);
    }

    /**
     * Returns the task ID in the String input.
     *
     * @param restOfInput String input of user after command word
     * @return taskID
     * @throws ParserException if the token (/id) or id number is missing
     */
    default int getTaskID(String restOfInput) throws ParserException {
        return getIntInput(restOfInput, TOKEN_TASK_ID);
    }

    /**
     * Returns the integer input based on token input.
     *
     * @param restOfInput String input of user after command word
     * @param token       token to extract result from
     * @return result extracted based on token
     * @throws ParserException if token or input is missing or invalid
     */
    private int getIntInput(String restOfInput, String token) throws ParserException {
        if (restOfInput.contains(token)) {
            int startPoint = restOfInput.indexOf(token);
            String startInput = restOfInput.substring(startPoint);
            Scanner scanner = new Scanner(startInput);
            scanner.next();
            if (!scanner.hasNext()) {
                throw new ParserException(MESSAGE_MISSING_INPUT);
            }
            String input = scanner.next();
            if (input.length() >= 10) {
                throw new ParserException(MESSAGE_LIMIT_EXCEEDED);
            }
            if (input.equals(EMPTY_INPUT_STRING) || input.contains(TOKEN_SLASH)) {
                throw new ParserException(MESSAGE_MISSING_INPUT);
            }
            int intInput;
            if (Pattern.matches("[0-9]+", input)) {
                intInput = Integer.parseInt(input);
            } else {
                throw new ParserException("Invalid " + token.substring(1) + " input!");
            }
            if (scanner.hasNext() && (scanner.next().charAt(0) != TOKEN_SLASH_CHAR)) {
                throw new ParserException(MESSAGE_INVALID_PARAM);
            }
            return intInput;
        } else {
            throw new ParserException("ArgumentError: Missing " + token);
        }
    }

    /**
     * Check if there are more than 500 tasks to be added.
     *
     * @param startDate the start date of the tasks
     * @param endDate the end date of the task
     * @param interval the interval input by user.
     * @throws ParserException if more than 500 tasks are added at a time.
     */
    default void isValidAmountTaskToAdd(String startDate, String endDate, int interval) throws ParserException {
        Date startingDate = CompalUtils.stringToDate(startDate);
        Date endingDate = CompalUtils.stringToDate(endDate);

        long diff = endingDate.getTime() - startingDate.getTime();
        long dayAdded = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        int maxAddition = 500;

        int toAdd = (int) (dayAdded / interval);

        if (toAdd > maxAddition) {
            throw new ParserException("You cannot attempt to add more than 500 tasks at one go!");
        }
    }

    /**
     * Returns a date in the String input.
     *
     * @param restOfInput String input of user after command word
     * @return date
     * @throws ParserException If date field is empty, date or date format is invalid,
     *                         date token (/date) is missing.
     */
    default Date getDate(String restOfInput) throws ParserException, ParseException {
        if (restOfInput.contains(TOKEN_DATE)) {
            int startPoint = restOfInput.indexOf(TOKEN_DATE);
            String dateStartInput = restOfInput.substring(startPoint);
            Scanner scanner = new Scanner(dateStartInput);
            scanner.next();
            if (!scanner.hasNext()) {
                throw new ParserException(MESSAGE_MISSING_INPUT);
            }
            String dateField = scanner.next();
            if (dateField.equals(EMPTY_INPUT_STRING) || dateField.charAt(0) == TOKEN_SLASH_CHAR) {
                throw new ParserException(MESSAGE_MISSING_INPUT);
            }
            if (!isDateValid(dateField)) {
                throw new ParserException(MESSAGE_INVALID_DATE_FORMAT);
            }
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateField);
            return date;
        } else {
            throw new ParserException(MESSAGE_MISSING_DATE_ARG);
        }
    }

    /**
     * Returns if the input date is a future date.
     *
     * @param date input date
     * @return true if date is a future date, false otherwise
     */
    default boolean isFutureDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date currentDate = calendar.getTime();
        Calendar inputDate = Calendar.getInstance();
        inputDate.setTime(date);
        return date.after(currentDate) || date.equals(currentDate);
    }

    //@@author LTPZ

    /**
     * Parses through user input for description field, and returns the description if present.
     *
     * @param restOfInput String input of user after command word
     * @return Description without date and time.
     * @throws ParserException If description field is missing, if there are no tokens present.
     */
    default String getTokenDescription(String restOfInput) throws ParserException {
        if (!restOfInput.contains(TOKEN_SLASH)) {
            throw new ParserException(MESSAGE_MISSING_TOKEN);
        }
        int splitPoint = restOfInput.indexOf(" /");
        String desc = restOfInput.substring(0, splitPoint).trim();
        if (desc.matches(EMPTY_INPUT_STRING)) {
            throw new ParserException(MESSAGE_MISSING_INPUT);
        }
        return desc;
    }


    //@@author yueyeah

    /**
     * Parses user input for optional interval token, and returns the interval specified.
     *
     * @param restOfInput String input of user after command word.
     * @return Interval between each recurring Task if specified, 0 if not specified.
     * @throws ParserException if the token is present but interval is not specified.
     */
    default int getTokenInterval(String restOfInput) throws ParserException {
        int interval = DEFAULT_WEEK_NUMBER_OF_DAYS;
        if (restOfInput.contains(TOKEN_INTERVAL)) {
            int splitPoint = restOfInput.indexOf(TOKEN_INTERVAL);
            String intervalStartInput = restOfInput.substring(splitPoint);
            Scanner scanner = new Scanner(intervalStartInput);
            scanner.next();
            if (!scanner.hasNext()) {
                throw new ParserException(MESSAGE_MISSING_INPUT);
            } else {
                try {
                    String stringInterval = scanner.next();
                    interval = Integer.parseInt(stringInterval);
                    if (scanner.hasNext()) {
                        String leftInput = scanner.next();
                        if (!leftInput.substring(0, 1).equals(TOKEN_SLASH)) {
                            throw new ParserException(MESSAGE_INVALID_PARAM);
                        }
                    }
                } catch (Exception e) {
                    //float number
                    throw new ParserException(MESSAGE_INVALID_INTERVAL);
                }
            }
        }
        return interval;
    }

    //@@author yueyeah

    /**
     * Returns a date string if specified in the task.
     *
     * @param restOfInput Input description after initial command word.
     * @return Date in the form of a string.
     * @throws ParserException If date field is empty, date or date format is invalid,
     *                         date token (/date) is missing.
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
                if (eachDateString.charAt(0) == TOKEN_SLASH_CHAR) {
                    if (dateCount == 0) {
                        throw new ParserException(MESSAGE_MISSING_INPUT);
                    }
                    break;
                }
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

    //@@author LTPZ

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
            if (isPriorityValid(commandPriority)) {
                priorityField = Task.Priority.valueOf(commandPriority.toLowerCase());
            } else {
                throw new ParserException(MESSAGE_INVALID_PRIORITY);
            }
            if (scanner.hasNext()) {
                String leftInput = scanner.next();
                if (!leftInput.substring(0, 1).equals(TOKEN_SLASH)) {
                    throw new ParserException(MESSAGE_INVALID_PARAM);
                }
            }
        } else {
            priorityField = Task.Priority.low;
        }
        return priorityField;
    }

    //@@author yueyeah

    /**
     * Parses through user input for /start token and return the start time.
     *
     * @param restOfInput String input of user after command word
     * @return Start time in the form of a String
     * @throws ParserException if start time is not entered after the /start token, or /start token is missing
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
            if (isTimeValid(startTimeField)) {
                return startTimeField;
            } else {
                throw new ParserException(MESSAGE_INVALID_TIME_FORMAT);
            }
        } else {
            throw new ParserException(MESSAGE_MISSING_START_TIME_ARG);
        }
    }

    //@@author LTPZ

    /**
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
            if (isTimeValid(endTimeField)) {
                if (scanner.hasNext()) {
                    String leftInput = scanner.next();
                    if (!leftInput.substring(0, 1).equals(TOKEN_SLASH)) {
                        throw new ParserException(MESSAGE_INVALID_PARAM);
                    }
                }
                return endTimeField;
            } else {
                throw new ParserException(MESSAGE_INVALID_TIME_FORMAT);
            }
        } else {
            throw new ParserException(MESSAGE_MISSING_END_TIME_ARG);
        }
    }

    //@@author yueyeah

    /**
     * Parses through the user input for /final-date token and return the final date of iteration of events/deadline.
     * The presence of the /final-date token must be checked first in the specialised
     * command parser calling this method.
     *
     * @param restOfInput String input of user after command word
     * @return Final date of iteration in the form of a String
     * @throws ParserException if final date is not entered after the /final-date token
     */
    default String getTokenFinalDate(String restOfInput) throws ParserException {
        int startPoint = restOfInput.indexOf(TOKEN_FINAL_DATE);
        String finalDateStartInput = restOfInput.substring(startPoint);
        Scanner scanner = new Scanner(finalDateStartInput);
        scanner.next();
        if (!scanner.hasNext()) {
            throw new ParserException(MESSAGE_MISSING_INPUT);
        }
        String finalDateField = scanner.next();
        if (isDateValid(finalDateField)) {
            if (scanner.hasNext()) {
                String leftInput = scanner.next();
                if (!leftInput.substring(0, 1).equals(TOKEN_SLASH)) {
                    throw new ParserException(MESSAGE_INVALID_PARAM);
                }
            }
            return finalDateField;
        } else {
            throw new ParserException(MESSAGE_INVALID_DATE_FORMAT);
        }
    }

    //@@author SholihinK

    /**
     * check if file name to read/write is valid and if file-name tag exist.
     *
     * @param restOfInput the rest of input
     * @return string of file name to write
     * @throws ParserException if fileName is not valid
     */
    default String getFileName(String restOfInput) throws ParserException {
        if (restOfInput.contains(TOKEN_FILE_NAME)) {
            int startPoint = restOfInput.indexOf(TOKEN_FILE_NAME);
            String startInput = restOfInput.substring(startPoint);
            Scanner scanner = new Scanner(startInput);
            scanner.next();
            if (!scanner.hasNext()) {
                throw new ParserException(MESSAGE_MISSING_FILE_NAME);
            }
            String fileName = scanner.next();
            File f = new File(fileName);
            try {
                f.getCanonicalPath();
                return fileName;
            } catch (IOException e) {
                throw new ParserException(MESSAGE_INVALID_FILE_NAME_FORMAT);
            }
        } else {
            throw new ParserException(MESSAGE_MISSING_FILE_NAME_ARG);
        }
    }


    /**
     * MISCELLANEOUS METHODS BELOW
     */


    //@@author SholihinK

    /**
     * Check if the date input is of valid format.
     *
     * @param date the string of the date input
     * @return true or false.
     * @author Sholihin
     */
    default boolean isDateValid(String date) throws ParserException {
        String regex = "^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);

        if (!matcher.matches()) {
            throw new ParserException(MESSAGE_INVALID_DATE_FORMAT);
        }

        final String DATE_FORMAT = "dd/MM/yyyy";

        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            throw new ParserException(MESSAGE_INVALID_DATE_FORMAT);
        }
    }

    //@@author yueyeah

    /**
     * Check if the time input is of valid format.
     *
     * @param time The time input in the form of a String.
     * @return True if the time is of valid format, false if not.
     */
    default boolean isTimeValid(String time) {
        String regex = "^(0[0-9]|1[0-9]|2[0-3])[0-5][0-9]$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(time);
        return matcher.matches();
    }

    //@@author yueyeah

    /**
     * Check if the priority input is valid.
     *
     * @param priority The priority input in the form of a String.
     * @return True if the priority is valid, false if not.
     */
    default boolean isPriorityValid(String priority) {
        String regex = "^(low|medium|high)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(priority);
        return matcher.matches();
    }

    //@author LTPZ

    /**
     * Check if the final date is after start date.
     *
     * @param startDate The final date string
     * @param finalDate The start date string
     * @throws ParserException if final date is not after the start date
     */
    default void isFinalDateAfterStartDate(String startDate, String finalDate) throws ParserException {
        Calendar c = Calendar.getInstance();
        Date dayStart = CompalUtils.stringToDate(startDate);
        c.setTime(dayStart);
        Date dateStart = c.getTime();
        Date dayEnd = CompalUtils.stringToDate(finalDate);
        c.setTime(dayEnd);
        Date dateEnd = c.getTime();
        if (dateStart.after(dateEnd)) {
            throw new ParserException(MESSAGE_INVALID_FINAL_TIME);
        }
    }

    //@author LTPZ

    /**
     * Check if the interval is positive.
     *
     * @param interval The interval input
     * @throws ParserException if the interval is not positive
     */
    default void isValidInterval(int interval) throws ParserException {
        if (interval <= 0) {
            throw new ParserException(MESSAGE_INVALID_INTERVAL);
        }
    }

    //@author LTPZ

    /**
     * Check if there is any invalid key.
     *
     * @param key         The set of valid keys of the command
     * @param restOfInput The input command
     * @throws ParserException if the command contains invalid keys
     */
    default void isValidKey(ArrayList<String> key, String restOfInput, String invalidParam)
        throws ParserException {
        ArrayList<String> copyofKeys = new ArrayList<>(key);
        Scanner scanner = new Scanner(restOfInput);
        while (scanner.hasNext()) {
            String param = scanner.next();
            if (param.substring(0, 1).equals(TOKEN_SLASH) && !copyofKeys.contains(param)) {
                throw new ParserException(invalidParam);
            } else if (param.substring(0, 1).equals(TOKEN_SLASH)) {
                copyofKeys.remove(param);
            }

        }
    }

    //@@author yueyeah

    /**
     * Check if the user input contains the token. Used to check for optional arguments like /final-date.
     *
     * @param restOfInput String input of user after command word
     * @param token       The token to be checked
     * @return True if the token exists in the user input, False if not.
     */
    default boolean hasToken(String restOfInput, String token) {
        return (restOfInput.contains(token));
    }
}