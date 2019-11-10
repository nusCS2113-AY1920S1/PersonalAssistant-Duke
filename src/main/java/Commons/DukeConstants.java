package Commons;

import java.text.SimpleDateFormat;

public class DukeConstants {
    //DEADLINE
    public static final String ADD_DEADLINE_HEADER = "add/d";
    public static final String DELETE_DEADLINE_HEADER = "delete/d";
    public static final String DONE_DEADLINE_HEADER = "done/d";
    public static final String DEADLINE_DATE_DESCRIPTION_SPLIT_KEYWORD = "/by";
    public static final String DEADLINE_LIST = "deadline";
    public static final String DEADLINE_EMPTY_MODCODE_DESCRIPTION_ERROR = " OOPS!!! The ModCode + description of a "
            + "deadline cannot be empty.";
    public static final String DEADLINE_EMPTY_DESCRIPTION_ERROR = " OOPS!!! The description of a deadline "
            + "cannot be empty.";
    public static final String DEADLINE_TIME_FORMAT_ERROR = " OOPS!!! The time of a deadline can only contain digits "
            + "and the time has to be 4 digits.\n"
            + "Please enter the time in a 24-hour time format";
    public static final String DEADLINE_FORMAT = " OOPS!!! Please enter deadline as follows:\n"
            + "(delete/d or add/d or done/d) mod_code name_of_deadline /by dd/MM/yyyy HHmm\n"
            + "or (delete/d or add/d or done/d) mod_code name_of_deadline /by week x day HHmm\n";

    //EVENT
    public static final String DELETE_EVENT_HEADER = "delete/e";
    public static final String ADD_EVENT_HEADER = "add/e";
    public static final String DONE_EVENT_HEADER = "done/e";
    public static final String EVENT_LIST = "event";
    public static final String EVENT_DATE_DESCRIPTION_SPLIT_KEYWORD = "/at";
    public static final String EVENT_DATE_SPLIT_KEYWORD = "/from";
    public static final String EVENT_TIME_SPLIT_KEYWORD = "/to";
    public static final String EVENT_EMPTY_MODCODE_DESCRIPTION_ERROR = " OOPS!!! The ModCode + description of an event "
            + "cannot be empty.";
    public static final String EVENT_EMPTY_DESCRIPTION_ERROR = " OOPS!!! The description of an event cannot be empty.";
    public static final String EVENT_TIME_FORMAT_ERROR = " OOPS!!! The time of an event can only contain digits and "
            + "the time has to be 4 digits.\n"
            + "Please enter the time in a 24-hour time format";
    public static final String EVENT_FORMAT = "OOPS!!! Please enter event as follows:\n"
            + "(add/e or delete/e or done/e) modCode name_of_event /at dd/MM/yyyy /from HHmm /to HHmm\n"
            + "or (add/e or delete/e or done/e) modCode name_of_event /at week x day /from HHmm /to HHmm\n "
            + "For example: add/e CS1231 project meeting /at 1/1/2020 /from 1500 /to 1700";

    //RECUR
    public static final String RECUR_WEEKLY_HEADER = "recur/weekly";
    public static final String RECUR_BIWEEKLY_HEADER = "recur/biweekly";
    public static final String REMOVE_RECUR_WEEKLY_HEADER = "recur/rmweekly";
    public static final String REMOVE_RECUR_BIWEEKLY_HEADER = "recur/rmbiweekly";
    public static final String RECUR_DATE_DESCRIPTION_SPLIT_KEYWORD = "/start";
    public static final String RECUR_BIWEEKLY_KEYWORD = "/biweekly";
    public static final String RECUR_RMBIWEEKLY_KEYWORD = "/rmbiweekly";
    public static final String RECUR_RMWEEKLY_KEYWORD = "/rmweekly";
    public static final String RECUR_EVENT_DATE_FORMAT_ERROR = " OOPS!!! The start date of the recurring event is "
            + "after the end date of the recurring event.\n"
            + "Please ensure the end date is after the start date.";
    public static final String RECUR_EVENT_FORMAT = "OOPS!!! Please enter recurring event as follows:\n"
            + "recur/(fill) modCode name_of_event /start dd/MM/yyyy to dd/MM/yyyy /from HHmm /to HHmm\n"
            + "Note: replace (fill) with either: weekly, biweekly, rmweekly, rmbiweekly\n"
            + "For example: recur/weekly CS1231 project meeting /start 1/10/2019 to 15/11/2019 /from 1500 /to 1700";

    //SHOW
    public static final String SHOW_WORKLOAD_HEADER = "show/workload";
    public static final String SHOW_FILTER_HEADER = "show/filter";
    public static final String HELP_HEADER = "show/help";
    public static final String FIND_TIME_HEADER = "find/time";
    public static final String FIND_TIME_KEYWORD_HOUR = "hour";
    public static final String FIND_TIME_KEYWORD_HOURS = "hours";
    public static final Integer FIND_TIME_LOWER_BOUNDARY = 1;
    public static final Integer FIND_TIME_UPPER_BOUNDARY = 16;
    public static final String SHOW_PREVIOUS_HEADER = "show/previous";
    public static final String SHOW_WEEK_HEADER = "show/week";
    public static final String SHOW_WORKLOAD_FORMAT = "OOPS!!! Please enter show workload as follows:\n"
            + "show/workload";
    public static final String SHOW_FILTER_FORMAT = " OOPS!!! Please enter filter command as follows\n"
            + "show/filter keyword\n";
    public static final String SHOW_PREVIOUS_FORMAT = "Invalid input. Please enter format: show/previous <x>"
            + " where x is an integer OR show/previous <command type>";
    public static final String INVALID_NEGATIVE_NUMBER = "Invalid Input. Cannot enter negative number. "
            + "Please enter a valid integer greater than 0";
    public static final String INVALID_DECIMAL_NUMBER = "Please enter an integer for x for the "
            + "command show/previous <x>.";
    public static final String NO_AND_INVALID_COMMAND_TYPE = "Invalid Input. There is no such "
            + "command type in previous input";
    public static final String INVALID_NUMBER_ZERO = "Please enter a valid integer greater than 0";



    //RETRIEVE
    public static final String RETRIEVE_TIME_HEADER = "retrieve/time";
    public static final Integer RETRIEVE_TIME_LOWER_BOUNDARY = 1;
    public static final Integer RETRIEVE_TIME_UPPER_BOUNDARY = 5;
    public static final String RETRIEVE_PREVIOUS_HEADER = "retrieve/previous";
    public static final String INVALID_WITHOUT_SPACE = "There should be a space between the command retrieve/previous"
            + " and <x>, where x is an integer";
    public static final String INVALID_EMPTY_NUMBER = "<x> cannot be empty. "
            + "Please enter the valid command as retrieve/previous <x>, "
            + "where x is an integer.";
    public static final String NO_PREVIOUS_COMMAND_TO_GET_LIST = "You did not enter Show Previous Command yet. \n"
            + "Format: show previous <x>, where x is an integer OR show previous <Command type>";

    public static final String INVALID_STRING_SHOULD_BE_INTEGER = "The x in retrieve/previous <x> must be an integer "
            + "and not a string.";

    public static final String INVALID_OPTION = "Invalid option. Please enter the command as follows. \n"
            + "retrieve/time <x>, where x is a digit between 1 - 5, inclusive";
    public static final String INVALID_EMPTY_OPTION = "Invalid input.\n"
            + "Option cannot be blank.\nPlease enter the command as follows.\n"
            + "retrieve/time <x>, where x is a digit between 1 - 5, inclusive";
    public static final String INVALID_NO_FREE_TIME_FOUND
            = "Please find free times by invoking the command shown below\n"
            + "find/time <x> hours, where x is a digit between 1 - 16, inclusive\n"
            + "Followed by the command\n"
            + "retrieve/time <x>, where x is a digit between 1- 5, inclusive";

    public static final String STR_RANGE_FOR_BETWEEN = "Please enter a valid integer x between 0 and ";
    public static final String STR_RANGE_FOR_LESS_THAN = "Please enter a valid number less than or equal to ";


    //FIND
    public static final String INVALID_INPUT = "Invalid input. Please enter the command as follows. \n"
            + "find/time <x> hours , where x is a digit between 1 - 16, inclusive";
    public static final String INVALID_DURATION = "Invalid duration. Please enter the command as follows. \n"
            + "find/time <x> hours , where x is a digit between 1 - 16, inclusive";
    public static final String INVALID_EMPTY_DURATION = "Invalid input."
            + "\nDuration cannot be blank.\nPlease enter the command as follows.\n"
            + "find/time <x> hours , where x is a digit between 1 - 16, inclusive";


    //REMIND
    public static final String REMIND_CHECK_HEADER = "remind/check";
    public static final String REMIND_SET_HEADER = "remind/set";
    public static final String REMOVE_REMIND_HEADER = "remind/rm";
    public static final String REMIND_SET_KEYWORD = "/set";
    public static final String REMIND_CHECK_KEYWORD = "/check";
    public static final String REMIND_DATE_DEADLINE_DATE_SPLIT_KEYWORD = " /on ";
    public static final String REMIND_TIME_FORMAT_ERROR = " OOPS!!! The time of a reminder can only contain digits and"
            + " the time has to be 4 digits.\n"
            + "Please enter the time in a 24-hour time format";
    public static final String REMIND_FORMAT = "OOPS!!! Please enter remind as follows:\n"
            + "remind/(set/rm) mod_code description /by week n.o day time /on week n.o day time\n"
            + "For example: remind/set cs2100 hand in homework /by week 9 fri 1500 /on week 9 thu 1500";
    public static final String REPEATED_REMINDER = "You already have a reminder set at that time. "
            + "Please remove reminder and set again";

    
    //EXCEPTIONS
    public static final String SAD_FACE = "\u2639";
    public static final String UNKNOWN_MEANING = " OOPS!!! I'm sorry, but I don't know what that means :-(";
    public static final String INVALID_MODCODE_ERROR = " OOPS!!! The ModCode is invalid";
    public static final String INVALID_INPUT_ERROR = "Invalid input. Please type help to see all commands";
    public static final String CONFLICTING_EVENT = "Sorry, you have conflicting events \n";
    public static final String CONFLICTING_DEADLINE = "Sorry, you have conflicting deadlines \n";
    public static final String NO_MOD_ERROR = "Sorry, you have no such mod in the system";
    public static final String NO_DATE_ERROR = "Sorry, you have no such date of the mod in the system";
    public static final String MISMATCH_DESCRIPTION = "Sorry, the description of your task mismatches";
    public static final String NO_TASK_TIMING_ERROR = "Sorry, you have no timing of the task in the system";
    public static final String REMINDER_TIME_PASSED_ERROR = "Sorry, your selected task has already passed!";
    public static final String NO_REMINDER_TIME_ERROR = "Sorry, you have no such reminder at that inputted time.";
    public static final String NO_REMINDER_DESCRIPTION_ERROR = "Sorry, you have no such reminder with "
            + "inputted description at that time.";
    public static final String NO_REMINDER_ERROR = "Sorry you have no such reminder task with inputted date and time.";
    public static final String TASK_TIME_PASSED_ERROR = "Sorry, you cannot set a time that has already passed!";
    public static final String NO_MODULE_ERROR = "Sorry, you have no such mod entered in your deadline table!";
    public static final String NO_TIMING_ERROR = "Sorry, you have no such timing entered in your deadline table!";
    public static final String REMINDER_AFTER_TASK_ERROR = "Sorry, you cannot set a reminder "
            + "after the date of the task.";
    public static final String INVALID_DATE_ERROR = "Sorry, please enter the correct date format";
    public static final String INVALID_ACADEMIC_YEAR_DATE = "Sorry, please check if date entered exists or "
            + "its in the academic year, or its in the format of DD/MM/YYYY";
    public static final String HAS_REMINDER_INDICATOR = "[HR]";
    public static final String NO_REMINDER_INDICATOR = "[NR]";


    //WEEK
    public static final String INVALID_EMPTY_WEEK = "Invalid Input.\n"
            + "The week cannot be blank.\nPlease enter the command as follows.\n"
            + "show/week <x> , where x is a digit between 1 - 13, inclusive or \n"
            + "x is either 'recess', 'reading', or 'exam'";
    public static final String INVALID_WEEK = "Invalid Week. Please enter the command as follows. \n"
            + "show/week <x> , where x is a digit between 1 - 13, inclusive or \n"
            + "x is either 'recess', 'reading', or 'exam'";


    //MISC
    public static final String BLANK_SPACE = " ";
    public static final String NO_FIELD = "";
    public static final String WEEK_FORMAT_KEYWORD_RECESS = "recess";
    public static final String WEEK_FORMAT_KEYWORD_READING = "reading";
    public static final String WEEK_FORMAT_KEYWORD_EXAM = "exam";
    public static final String WEEK_FORMAT_KEYWORD = "Week";
    public static final String BYE_HEADER = "bye";
    public static final String DATA_TIME_STRING_TERMINATOR = ")";
    public static final String DONE_INDICATOR = "\u2713";
    public static final String NOT_DONE_INDICATOR = "\u2718";


    //DATE
    //format date for event
    public static final SimpleDateFormat EVENT_DATE_INPUT_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    //format time for event
    public static final SimpleDateFormat EVENT_TIME_INPUT_FORMAT = new SimpleDateFormat("HHmm");
    public static final SimpleDateFormat DAY_DATE_FORMAT = new SimpleDateFormat("E dd/MM/yyyy");
    public static final SimpleDateFormat TWELVE_HOUR_TIME_FORMAT = new SimpleDateFormat("hh:mm a");
    public static final SimpleDateFormat DEADLINE_INPUT_FORMAT = new SimpleDateFormat("dd/MM/yyyy HHmm");
    public static final SimpleDateFormat DEADLINE_DATE_FORMAT = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
    public static final SimpleDateFormat DAY_FORMAT = new SimpleDateFormat("E");
    public static final SimpleDateFormat TWENTYFOUR_HOUR_DATE_FORMAT = new SimpleDateFormat("E dd/MM/yyyy HHmm");
    public static final int LENGTH_OF_TIME_FORMAT = 4;

    //Storage
    public static final String NO_PRELOAD_EVENT_TXT = "There is no preload event.txt to read from. Please create one.";
    public static final String NO_PRELOAD_DEADLINE_TXT = "There is no preload deadline.txt to read from. "
            + "Please create one.";
    public static final String NO_EVENT_TXT = "There is no event.txt file to read from. Please create one.";
    public static final String NO_DEADLINE_TXT = "There is no deadline.txt file to read from. Please create one.";
    public static final String DEADLINE_INDICATOR = "[D]";
    public static final String EVENT_INDICATOR = "[E]";
    public static final String REMINDER_TIME_START_KEYWORD = "[<R";
    public static final String REMINDER_TIME_END_KEYWORD = "/R>]";
}