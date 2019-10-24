package duke.commons;

/**
 * Defines messages sent via Prompt by Duke.
 */
public class MessagesPrompt {

    public static final String PROMPT_UNKNOWN = "Sorry, but I do not understand that.";
    public static final String PROMPT_ERROR = "Sorry, but something went wrong...";
    public static final String PROMPT_TOO_MANY_ATTEMPTS = "Sorry, but you have exceeded 5 attempts...";
    public static final String PROMPT_SPACES = "Please do not include spaces in your search!";
    public static final String PROMPT_NOT_INT = "Please use a number!";
    public static final String PROMPT_NOT_STRING = "Please use a string!";
    public static final String PROMPT_NOT_DATE = "Please use a proper date!";

    public static final String SEARCH_PROMPT_STARTER = "Where would you like to find?";
    public static final String SEARCH_PROMPT_SUCCESS = "These are the coordinates of your search:";

    public static final String ADD_PROMPT_STARTER = "What would you like to add?";
    public static final String ADD_PROMPT_SUCCESS = "Task added!";

    public static final String DEADLINE_PROMPT_STARTER = "What deadline would you like to add?";
    public static final String DEADLINE_PROMPT_DATE = "Please key in the date that you want it done by";
    public static final String DEADLINE_PROMPT_SUCCESS = "Deadline added!";

    public static final String EVENT_PROMPT_STARTER = "What event would you like to add?";
    public static final String EVENT_PROMPT_DATE = "When is the event?";
    public static final String EVENT_PROMPT_SUCCESS = "Event added!";

    public static final String FIND_PROMPT_STARTER = "What task would you like to find?";
    public static final String FIND_PROMPT_SUCCESS = "Here are the tasks:";

    public static final String FIXED_PROMPT_STARTER = "What task would you like it to be done by?";
    public static final String FIXED_PROMPT_HOUR = "How many hours must it be done by?";
    public static final String FIXED_PROMPT_MIN = " How many minutes must it be done by?";
    public static final String FIXED_PROMPT_SUCCESS = "Fixed task added!";

    public static final String HOLIDAY_PROMPT_STARTER = "What holiday would you like to add?";
    public static final String HOLIDAY_PROMPT_STARTDATE = "When does it start?";
    public static final String HOLIDAY_PROMPT_ENDDATE = "When does it end?";
    public static final String HOLIDAY_PROMPT_SUCCESS = "Event added!";

    public static final String TODO_PROMPT_STARTER = "What would you like to do?";
    public static final String TODO_PROMPT_SUCCESS = "To Do added!";

    public static final String WITHIN_PROMPT_STARTER = "What task with a fixed timing would you like to add?";
    public static final String WITHIN_PROMPT_ENDDATE = "When must it be done by?";
    public static final String WITHIN_PROMPT_SUCCESS = "Within added!";

    public static final String REPEAT_PROMPT_STARTER = "What task that repeats every X days would you like to add?";
    public static final String REPEAT_PROMPT_DATE = "When does the task start?";
    public static final String REPEAT_PROMPT_REPEAT = "How many days does it repeat?";
    public static final String REPEAT_PROMPT_SUCCESS = "Repeating task added!";

    public static final String DELETE_PROMPT_STARTER = "What would you like to delete?";
    public static final String DELETE_PROMPT_SUCCESS = "Trying to delete task...";

    public static final String FINDPATH_PROMPT_STARTER = "Where do you want to go?";
    public static final String FINDPATH_PROMPT_SUCCESS = "Here is the path:";

    public static final String FREETIME_PROMPT_STARTER = "How long of free time are you looking for?";
    public static final String FREETIME_PROMPT_SUCCESS = "Here are the results:";

    public static final String GETBUSROUTE_PROMPT_STARTER = "Which bus route do you want to get?";
    public static final String GETBUSROUTE_PROMPT_SUCCESS = "Here is the bus route:";

    public static final String GETBUSSTOP_PROMPT_STARTER = "Which bus stop do you want to find?";
    public static final String GETBUSSTOP_PROMPT_SUCCESS = "Here is the bus stop";

    public static final String MARKDONE_PROMPT_STARTER = "Which task have you completed?";
    public static final String MARKDONE_PROMPT_SUCCESS = "The task has been marked as done!";

    public static final String RESCHEDULE_PROMPT_STARTER = "Which event would you like to reschedule?";
    public static final String RESCHEDULE_PROMPT_SUCCESS = "Rescheduled!";

    public static final String VIEWSCHEDULE_PROMPT_STARTER = "Which date would you like to find?";
    public static final String VIEWSCHEDULE_PROMPT_SUCCESS = "Here are the events happening in this day:";

    public static final String RECOMMEND_PROMPT_STARTER = "How many days will you be visiting SG?";
    public static final String RECOMMEND_PROMPT_SUCCESS = "Recommended Attractions:";

    public static final String CANCEL_PROMPT = "Current conversation has ended.";
    public static final String STARTING_POINT_PROMPT = "Choose your starting point.";
    public static final String ENDPOINT_PROMPT = "Choose your end point.";
}
