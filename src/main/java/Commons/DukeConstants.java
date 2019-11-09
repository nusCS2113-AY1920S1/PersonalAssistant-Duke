package Commons;

public class DukeConstants {
    //DEADLINE
    public static final String ADD_DEADLINE_HEADER = "add/d";
    public static final String DELETE_DEADLINE_HEADER = "delete/d";
    public static final String DONE_DEADLINE_HEADER = "done/d";
    public static final String DEADLINE_DATE_DESCRIPTION_SPLIT_KEYWORD = "/by";
    public static final String DEADLINE_LIST = "deadline";
    public static final String DEADLINE_EMPTY_MODCODE_DESCRIPTION_ERROR =" OOPS!!! The ModCode + description of a deadline cannot be empty.";
    public static final String DEADLINE_EMPTY_DESCRIPTION_ERROR =" OOPS!!! The description of a deadline cannot be empty.";
    public static final String DEADLINE_TIME_FORMAT_ERROR = " OOPS!!! The time of a deadline can only contain digits and the time has to be 4 digits.\n" +
            "Please enter the time in a 24-hour time format";
    public static final String DEADLINE_FORMAT = " OOPS!!! Please enter deadline as follows:\n" +
            "(delete/d or add/d or done/d) mod_code name_of_deadline /by dd/MM/yyyy HHmm\n" +
            "or (delete/d or add/d or done/d) mod_code name_of_deadline /by week x day HHmm\n";

    //EVENT
    public static final String DELETE_EVENT_HEADER = "delete/e";
    public static final String ADD_EVENT_HEADER = "add/e";
    public static final String DONE_EVENT_HEADER = "done/e";
    public static final String EVENT_LIST = "event";
    public static final String EVENT_DATE_DESCRIPTION_SPLIT_KEYWORD = "/at";
    public static final String EVENT_DATE_SPLIT_KEYWORD = "/from";
    public static final String EVENT_TIME_SPLIT_KEYWORD = "/to";
    public static final String EVENT_EMPTY_MODCODE_DESCRIPTION_ERROR =" OOPS!!! The ModCode + description of an event cannot be empty.";
    public static final String EVENT_EMPTY_DESCRIPTION_ERROR =" OOPS!!! The description of an event cannot be empty.";
    public static final String EVENT_TIME_FORMAT_ERROR = " OOPS!!! The time of an event can only contain digits and the time has to be 4 digits.\n" +
            "Please enter the time in a 24-hour time format";
    public static final String EVENT_FORMAT = "OOPS!!! Please enter event as follows:\n" +
            "(add/e or delete/e or done/e) modCode name_of_event /at dd/MM/yyyy /from HHmm /to HHmm\n" +
            "or (add/e or delete/e or done/e) modCode name_of_event /at week x day /from HHmm /to HHmm\n " +
            "For example: add/e CS1231 project meeting /at 1/1/2020 /from 1500 /to 1700";

   //RECUR
    public static final String RECUR_WEEKLY_HEADER = "recur/weekly";
    public static final String RECUR_BIWEEKLY_HEADER = "recur/biweekly";
    public static final String REMOVE_RECUR_WEEKLY_HEADER ="recur/rmweekly";
    public static final String REMOVE_RECUR_BIWEEKLY_HEADER ="recur/rmbiweekly";
    public static final String RECUR_DATE_DESCRIPTION_SPLIT_KEYWORD = "/start";
    public static final String RECUR_BIWEEKLY_KEYWORD = "/biweekly";
    public static final String RECUR_RMBIWEEKLY_KEYWORD = "/rmbiweekly";
    public static final String RECUR_RMWEEKLY_KEYWORD = "/rmweekly";
    public static final String RECUR_EVENT_FORMAT = "OOPS!!! Please enter recurring event as follows:\n" +
            "recur/(fill) modCode name_of_event /start dd/MM/yyyy to dd/MM/yyyy /from HHmm /to HHmm\n" +
            "Note: replace (fill) with either: weekly, biweekly, rmweekly, rmbiweekly\n" +
            "For example: recur/weekly CS1231 project meeting /start 1/10/2019 to 15/11/2019 /from 1500 /to 1700";

    //SHOW
    public static final String SHOW_WORKLOAD_HEADER = "show/workload";
    public static final String SHOW_FILTER_HEADER ="show/filter";
    public static final String HELP_HEADER = "help";
    public static final String FIND_TIME_HEADER ="find/time";
    public static final String FIND_TIME_KEYWORD_HOUR = "hour";
    public static final String FIND_TIME_KEYWORD_HOURS = "hours";
    public static final Integer FIND_TIME_LOWER_BOUNDARY = 1;
    public static final Integer FIND_TIME_UPPER_BOUNDARY = 16;
    public static final String SHOW_PREVIOUS_HEADER = "show/previous";
    public static final String SHOW_WEEK_HEADER = "show/week";
    public static final String SHOW_WORKLOAD_FORMAT = "OOPS!!! Please enter show workload as follows:\n" +
            "show/workload";
    public static final String SHOW_FILTER_FORMAT = " OOPS!!! Please enter filter command as follows\n" +
            "show/filter keyword\n";

    //RETRIEVE/FIND
    public static final String RETRIEVE_TIME_HEADER = "retrieve/time";
    public static final Integer RETRIEVE_TIME_LOWER_BOUNDARY = 1;
    public static final Integer RETRIEVE_TIME_UPPER_BOUNDARY = 5;
    public static final String RETRIEVE_PREVIOUS_HEADER ="retrieve/previous";

    //REMIND
    public static final String REMIND_CHECK_HEADER = "remind/check";
    public static final String REMIND_SET_HEADER ="remind/set";
    public static final String REMOVE_REMIND_HEADER = "remind/rm";
    public static final String REMIND_SET_KEYWORD = "/set";
    public static final String REMIND_CHECK_KEYWORD = "/check";
    public static final String REMIND_DATE_DEADLINE_DATE_SPLIT_KEYWORD = " /on ";
    public static final String REMIND_FORMAT ="OOPS!!! Please enter remind as follows:\n" +
            "remind/(set/rm) mod_code description /by week n.o day time /on week n.o day time\n" +
            "For example: remind/set cs2100 hand in homework /by week 9 fri 1500 /on week 9 thu 1500";

   //EXCEPTIONS
    public static final String SAD_FACE = "\u2639";
    public static final String UNKNOWN_MEANING =" OOPS!!! I'm sorry, but I don't know what that means :-(";
    public static final String INVALID_MODCODE_ERROR =" OOPS!!! The ModCode is invalid";
    public static final String INVALID_INPUT_ERROR = "Invalid input. Please type help to see all commands";
    //MISC
    public static final String STRING_SPACE_SPLIT_KEYWORD = " ";
    public static final String EMPTY_ERROR = "";
    public static final String NO_FIELD = "void";
    public static final String WEEK_FORMAT_KEYWORD_RECESS = "recess";
    public static final String WEEK_FORMAT_KEYWORD_READING = "reading";
    public static final String WEEK_FORMAT_KEYWORD_EXAM = "exam";
    public static final String WEEK_FORMAT_KEYWORD = "Week";
    public static final String BYE_HEADER ="bye";




}
