package duke.commons;

/**
 * Messages used by duke.Duke.
 */
public class Messages {

    /**
     * Error messages.
     * Format is ERROR_TYPE_DESCRIPTION
     */
    public static final String ERROR_COMMAND_UNKNOWN = "☹ I'm sorry, but I don't know what that means :-(";
    public static final String ERROR_CONSTRAINT_UNKNOWN = "☹ I'm sorry, but I don't know what constraint this is :-(";
    public static final String ERROR_DESCRIPTION_EMPTY = "⛔ The description cannot be empty. :-(";
    public static final String ERROR_FIELD_UNKNOWN = "☹ I'm sorry, but I don't know what you want to edit :-(";

    public static final String ERROR_INPUT_INVALID_FORMAT = "⚡ Invalid format. Refer to help for command formats. :-(";
    public static final String ERROR_INDEX_OUT_OF_BOUNDS = "⚠ Index requested is out of bounds! :-(";

    public static final String ERROR_DATA_CORRUPTED = "☢ Data cannot be converted! :-( ";
    public static final String ERROR_FILE_NOT_FOUND = "☢ File not found! ";
    public static final String ERROR_FILE_NOT_SAVED = "✋ File not saved! :-(";

    public static final String ERROR_RESOURCE_NOT_FOUND = "☢ Resource not found! :-( Missing: ";

    public static final String ERROR_TASK_CORRUPTED = "✇ Task cannot be converted to String! :-(";
    public static final String ERROR_TASK_DUPLICATED = "✇ Task already exists! :-(";
    public static final String ERROR_TASK_NOT_FOUND = "☢ Task not found! :-(";

    public static final String ERROR_ROUTE_CORRUPTED = "✇ Route cannot be converted to String! :-(";
    public static final String ERROR_ROUTE_DUPLICATE = "✇ Route already exists! :-(";
    public static final String ERROR_ROUTE_NOT_FOUND = "☢ Route not found! :-(";

    public static final String ERROR_ROUTE_NODE_CORRUPTED = "✇ Route Node cannot be converted to String! :-(";
    public static final String ERROR_ROUTE_NODE_DUPLICATE = "✇ Node already exists! :-(";
    public static final String ERROR_ROUTE_NODE_NOT_FOUND = "☢ Route not found! :-(";

    public static final String ANOMALY_FOUND = "⚡ Anomaly detected! Please check your tasks. :-(";

    public static final String ERROR_API_REQUEST_FAILED =
            "☹ I'm sorry, something went wrong while fetching data. :-(";
    public static final String ERROR_API_DATA_NULL = "☹ I'm sorry, but nothing could be found. :-(";
    public static final String ERROR_API_TIMEOUT = "☹ I'm sorry, but the request has timed out... :-(";

    public static final String ERROR_TASK_NOT_HOLIDAY = "Sorry, the numbers you entered are not a holiday destination.";
    public static final String ERROR_RECOMMENDATION_FAIL = "☹ I'm sorry, our recommendation services are down";

    public static final String ERROR_PARSER_FAIL = "Parsing failed.";

    public static final String ERROR_FORMAT_INVALID = "☹ I'm sorry, numbers or words cannot be formatted.";

    public static final String ERROR_LOCATION_SELECTOR_NULL = "There is no locations to be selected!";

    /**
     * Messages sent via Prompt by Duke.
     * Format is PROMPT_TYPE_DESCRIPTION
     */
    public static final String PROMPT_UNKNOWN = "Sorry, but I do not understand that.";
    public static final String PROMPT_ERROR = "Sorry, but something went wrong...";
    public static final String PROMPT_TOO_MANY_ATTEMPTS = "Sorry, but you have exceeded 5 attempts...";
    public static final String PROMPT_SPACES = "Please do not include spaces in your search!";
    public static final String PROMPT_NOT_INT = "Please use a number!";
    public static final String PROMPT_NOT_STRING = "Please use a string!";
    public static final String PROMPT_NOT_DATE = "Please use a proper date!";

    public static final String PROMPT_SEARCH_STARTER = "Where would you like to find?";
    public static final String PROMPT_SEARCH_SUCCESS = "These are the coordinates of your search:";

    public static final String PROMPT_ADD_STARTER = "What would you like to add?";
    public static final String PROMPT_ADD_SUCCESS = "Task added!";

    public static final String PROMPT_DEADLINE_STARTER = "What deadline would you like to add?";
    public static final String PROMPT_DEADLINE_DATE = "Please key in the date that you want it done by";
    public static final String PROMPT_DEADLINE_SUCCESS = "Deadline added!";

    public static final String PROMPT_EVENT_STARTER = "What event would you like to add?";
    public static final String PROMPT_EVENT_DATE = "When is the event?";
    public static final String EVENT_PROMPT_SUCCESS = "Event added!";

    public static final String PROMPT_FIND_STARTER = "What task would you like to find?";
    public static final String PROMPT_FIND_SUCCESS = "Here are the tasks:";

    public static final String PROMPT_FIXED_STARTER = "What task would you like it to be done by?";
    public static final String PROMPT_FIXED_HOUR = "How many hours must it be done by?";
    public static final String PROMPT_FIXED_MIN = " How many minutes must it be done by?";
    public static final String PROMPT_FIXED_SUCCESS = "Fixed task added!";

    public static final String PROMPT_HOLIDAY_STARTER = "What holiday would you like to add?";
    public static final String PROMPT_HOLIDAY_STARTDATE = "When does it start?";
    public static final String PROMPT_HOLIDAY_ENDDATE = "When does it end?";
    public static final String PROMPT_HOLIDAY_SUCCESS = "Event added!";

    public static final String PROMPT_TODO_STARTER = "What would you like to do?";
    public static final String PROMPT_TODO_SUCCESS = "To Do added!";

    public static final String PROMPT_WITHIN_STARTER = "What task with a fixed timing would you like to add?";
    public static final String PROMPT_WITHIN_ENDDATE = "When must it be done by?";
    public static final String PROMPT_WITHIN_SUCCESS = "Within added!";

    public static final String PROMPT_REPEAT_STARTER = "What task that repeats every X days would you like to add?";
    public static final String PROMPT_REPEAT_DATE = "When does the task start?";
    public static final String PROMPT_REPEAT_REPEAT = "How many days does it repeat?";
    public static final String PROMPT_REPEAT_SUCCESS = "Repeating task added!";

    public static final String PROMPT_DELETE_STARTER = "What would you like to delete?";
    public static final String PROMPT_DELETESUCCESS = "Trying to delete task...";

    public static final String FINDPATH_PROMPT_STARTER = "Where do you want to go?";
    public static final String FINDPATH_PROMPT_SUCCESS = "Here is the path:";

    public static final String PROMPT_FREETIME_STARTER = "How long of free time are you looking for?";
    public static final String PROMPT_FREETIME_SUCCESS = "Here are the results:";

    public static final String PROMPT_GETBUSROUTE_STARTER = "Which bus route do you want to get?";
    public static final String PROMPT_GETBUSROUTE_SUCCESS = "Here is the bus route:";

    public static final String PROMPT_GETBUSSTOP_STARTER = "Which bus stop do you want to find?";
    public static final String PROMPT_GETBUSSTOP_SUCCESS = "Here is the bus stop";

    public static final String PROMPT_MARKDONE_STARTER = "Which task have you completed?";
    public static final String PROMPT_MARKDONE_SUCCESS = "The task has been marked as done!";

    public static final String PROMPT_RESCHEDULE_STARTER = "Which event would you like to reschedule?";
    public static final String PROMPT_RESCHEDULE_SUCCESS = "Rescheduled!";

    public static final String PROMPT_VIEWSCHEDULE_STARTER = "Which date would you like to find?";
    public static final String PROMPT_VIEWSCHEDULE_SUCCESS = "Here are the events happening in this day:";

    public static final String PROMPT_RECOMMEND_STARTER = "How many days will you be visiting SG?";
    public static final String PROMPT_RECOMMEND_SUCCESS = "Recommended Attractions:";

    public static final String PROMPT_CANCEL = "Current conversation has ended.";
    public static final String PROMPT_ROUTE_STARTING_POINT = "Choose your starting point.";
    public static final String PROMPT_ROUTE_ENDING_POINT = "Choose your end point.";

}
