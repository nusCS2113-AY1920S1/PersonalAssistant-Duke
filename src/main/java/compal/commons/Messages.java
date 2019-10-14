package compal.commons;

import java.util.Date;

/**
 * Container for user visible messages.
 */

public class Messages {


    public static final String MESSAGE_INVALID_COMMAND = "CommandError: Unknown command input detected!";
    public static final String MESSAGE_INVALID_RANGE = "RangeError: Invalid range detected for execution of command!";
    public static final String MESSAGE_INVALID_DATE_FORMATTING = "DateFormattingError: Date format input is invalid! "
            + "Please make sure is dd/mm/yyyy format.";
    public static final String MESSAGE_INVALID_YEAR = "YearRangeError: You can only put input "
            + "schedule of the current year onwards!";
    public static final String MESSAGE_INVALID_MINUTE = "MinuteRangeError: You can only input "
            + "a value between 0 and 59 inclusive!";
    public static final String MESSAGE_INVALID_TASK_NUMBER = "TaskNumberError: You can only input "
            + "a valid task number!";
    public static final String MESSAGE_INVALID_TASK = "TaskError: You can only input a task with a future due date!";
    public static final String MESSAGE_INVALID_TIME_RANGE = "TimeRangeError: End time is before start time!";
    public static final String MESSAGE_INVALID_REP_RANGE = "RepRangeError: The number of reps of a Recurring Task"
            + " has to be greater than 1. Please use event command if your Task does not recur more than once.";
    public static final String MESSAGE_MISSING_DESC = "DescError: Description field cannot be empty."
            + " Please enter a description";
    public static final String MESSAGE_MISSING_HOUR = "Empty Hour Error: Required hour input!";
    public static final String MESSAGE_MISSING_MIN = "Empty Min Error: Required minute input!";
    public static final String MESSAGE_MISSING_DATE = "Empty Date Error: Required date input!";
    public static final String MESSAGE_MISSING_INPUT = "Empty Input: Empty input detected!";
    public static final String MESSAGE_MISSING_TIME = "MissingTimeError: Time field cannot be empty."
            + " Please enter a valid time.";
    public static final String MESSAGE_MISSING_PRIORITY = "MissingPriorityError: Priority "
            + "field cannot be empty.";
    public static final String MESSAGE_MISSING_REP = "MissingRepError: Rep field cannot be empty."
            + "Please enter a valid number of repetitions greater than 1.";
    public static final String MESSAGE_MISSING_EDATE = "MissingEndDateError: End date cannot be empty. "
            + "Please enter a valid end date for your semester.";
    public static final String MESSAGE_MISSING_REP_EDATE_ARG = "ArgumentError: Missing /rep and /edate";
    public static final String MESSAGE_MISSING_DATE_ARG = "ArgumentError: Missing /date";
    public static final String MESSAGE_MISSING_START_TIME_ARG = "ArgumentError: Missing /start";
    public static final String MESSAGE_MISSING_END_TIME_ARG = "ArgumentError: Missing /end";
    public static final String MESSAGE_MISSING_HOUR_ARG = "ArgumentError: Missing /hour";
    public static final String MESSAGE_MISSING_MIN_ARG = "ArgumentError: Missing /min";
    public static final String MESSAGE_MISSING_PRIORITY_ARG = "ArgumentError: Missing /priority";
    public static final String MESSAGE_MISSING_NUM = "Empty number error: Required command number input!";
    public static final String MESSAGE_MISSING_SEARCH = "Empty search content error: Required search content input!";
    public static final String MESSAGE_INVALID_HELP_INPUT = "Invalid input for help function";
    public static final String MESSAGE_MISSING_COMMAND_ARG = "ArgumentError: Missing argument detected!";
    public static final String MESSAGE_MISSING_EDATE_ARG = "ArgumentError: Missing /edate";
    public static final String MESSAGE_INIT_REMINDER = "view-reminder";
    private static Date currentDate = java.util.Calendar.getInstance().getTime();
    public static final String MESSAGE_INVALID_DATE_TIME_INPUT = "DateTimeError: You can only add "
            + "task after current date and time:\n" + currentDate;
}
