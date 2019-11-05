package duke.commons;

/**
 * Defines messages used by SGTravel.
 */
public class Messages {

    /**
     * Error messages.
     * Format is ERROR_TYPE_DESCRIPTION
     */
    public static final String ERROR_COMMAND_UNKNOWN = "I'm sorry, but I don't know what that means :-(";
    public static final String ERROR_CONSTRAINT_UNKNOWN = "I'm sorry, but I don't know what constraint this is :-(";
    public static final String ERROR_DESCRIPTION_EMPTY = "The description cannot be empty. :-(";
    public static final String ERROR_FIELD_UNKNOWN = "I'm sorry, but I don't know what you want to edit :-(";
    public static final String ERROR_FIELDS_EMPTY = "I'm sorry, but one of the fields for this command is empty :-(";

    public static final String ERROR_INPUT_INVALID_FORMAT = "Invalid format. :-(";
    public static final String ERROR_INDEX_OUT_OF_BOUNDS = "Index requested is out of bounds! :-(";

    public static final String ERROR_DATA_CORRUPTED = "Data cannot be converted! :-( ";
    public static final String ERROR_FILE_NOT_FOUND = "\nFile not found! ";
    public static final String ERROR_FILE_NOT_SAVED = "File not saved! :-(";

    public static final String ERROR_RESOURCE_NOT_FOUND = "Resource not found! :-( Missing: ";
    public static final String ERROR_OBJECT_NOT_CREATED = "Item could not be created! :-(";

    public static final String ERROR_TASK_CORRUPTED = "Task cannot be converted to String! :-(";
    public static final String ERROR_TASK_DUPLICATED = "Task already exists! :-(";
    public static final String ERROR_TASK_NOT_FOUND = "Task not found! :-(";

    public static final String ERROR_ROUTE_CORRUPTED = "Route cannot be converted to String! :-(";
    public static final String ERROR_ROUTE_DUPLICATE = "Route already exists! :-(";
    public static final String ERROR_ROUTE_NOT_FOUND = "Route not found! :-(";

    public static final String ERROR_ROUTE_NODE_CORRUPTED = "Route Node cannot be converted to String! :-(";
    public static final String ERROR_ROUTE_NODE_DUPLICATE = "Node already exists! :-(";
    public static final String ERROR_ROUTE_NODE_NOT_FOUND = "☢ Route not found! :-(";

    public static final String ERROR_ROUTE_GENERATE_FAIL = "The Route failed to generate, try other locations :-(";

    public static final String ERROR_CATEGORY_NOT_FOUND = "Category does not exists! :-(";

    public static final String ANOMALY_FOUND = "⚡ Anomaly detected! Please check your tasks. :-(";

    public static final String ERROR_API_REQUEST_FAILED =
            "☹ I'm sorry, something went wrong while fetching data. :-(";
    public static final String ERROR_API_DATA_NULL = "I'm sorry, but nothing could be found. :-(";
    public static final String ERROR_API_TIMEOUT = "☹ I'm sorry, but the request has timed out... :-(";

    public static final String ERROR_TASK_NOT_HOLIDAY = "Sorry, the numbers you entered are not a holiday destination.";
    public static final String ERROR_RECOMMENDATION_FAIL = "☹ I'm sorry, our recommendation services are down";

    public static final String ERROR_PARSER_FAIL = "Parsing failed.";

    public static final String ERROR_FORMAT_INVALID = "☹ I'm sorry, numbers or words cannot be formatted.";

    public static final String ERROR_LOCATION_SELECTOR_NULL = "There is no locations to be selected!";
    public static final String ERROR_VENUE_EMPTY = "There are no Venues. VenueList is empty.";
    public static final String ERROR_EVENT_OUT_OF_BOUND = "The index is out of bounds for the EventList.";
    public static final String ERROR_EVENT_NOT_SELECTED = "There are no events selected.";
    public static final String ERROR_NULL_RESULT = "Nothing could be found.";
    /**
     * Messages sent via Prompt by Duke.
     * Format is PROMPT_TYPE_DESCRIPTION
     */
    public static final String PROMPT_UNKNOWN = "Sorry, but I do not understand that.";
    public static final String PROMPT_ERROR = "Sorry, but something went wrong...";
    public static final String PROMPT_TOO_MANY_ATTEMPTS = "Sorry, but you have exceeded 5 attempts...";
    public static final String PROMPT_SPACES = "Please do not include spaces in your search!";
    public static final String PROMPT_NOT_INT = "Please use a number!";
    public static final String PROMPT_NOT_DOUBLE = "Please use a number!";
    public static final String PROMPT_NOT_STRING = "Please use a string!";
    public static final String PROMPT_NOT_DATE = "Please use a proper date!";
    public static final String PROMPT_NOT_ROUTE_FIELD = "Please choose either name or description!";
    public static final String PROMPT_NOT_ROUTENODE_FIELD = "Please use a proper field!";


    public static final String PROMPT_SEARCH_STARTER = "Where would you like to find?";
    public static final String PROMPT_SEARCH_SUCCESS = "These are the coordinates of your search:";

    public static final String PROMPT_ADD_STARTER = "What would you like to add?";
    public static final String PROMPT_ADD_SUCCESS = "Task added!";

    public static final String PROMPT_DEADLINE_STARTER = "What deadline would you like to add?";
    public static final String PROMPT_DEADLINE_DATE = "Please key in the date that you want it done by";
    public static final String PROMPT_DEADLINE_SUCCESS = "Deadline added!";

    public static final String PROMPT_EVENT_STARTER = "What event would you like to add?";
    public static final String PROMPT_EVENT_DATE = "When is the event?";
    public static final String PROMPT_EVENT_SUCCESS = "Event added!";

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
    public static final String PROMPT_DELETE_SUCCESS = "Trying to delete task...";

    public static final String PROMPT_FINDPATH_STARER = "What transport do you like?";
    public static final String PROMPT_FINDPATH_SUCCESS = "Here is the path:";

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

    public static final String PROMPT_SETUP_PROFILE = "Welcome to SGTravel.\n Please enter your name.";
    public static final String PROMPT_BIRTHDAY = "Enter your birthday.";

    public static final String PROMPT_ROUTE_ADD_STARTER = "Please enter the name of the route!";
    public static final String PROMPT_ROUTE_ADD_DESCRIPTION = "Please enter the description of the route!";
    public static final String PROMPT_ROUTE_ADD_SUCCESS = "Route generated successfully: ";

    public static final String PROMPT_ROUTE_DELETE_STARTER = "Which route will you like to delete?";
    public static final String PROMPT_ROUTE_DELETE_SUCCESS = "Route deleted successfully!";

    public static final String PROMPT_ROUTE_EDIT_STARTER = "Which route will you like to edit?";
    public static final String PROMPT_ROUTE_EDIT_FIELD = "What is the field that you want to edit?";
    public static final String PROMPT_ROUTE_EDIT_SUCCESS = "Route edited successfully!";

    public static final String PROMPT_ROUTE_GENERATE_STARTER = "Where do you want to start from?";
    public static final String PROMPT_ROUTE_GENERATE_ENDLOCATION = "Where is your destination?";
    public static final String PROMPT_ROUTE_GENERATE_CONSTRAINT = "How do you want to travel by?";
    public static final String PROMPT_ROUTE_GENERATE_SUCCESS = "Route generated successfully!";

    public static final String PROMPT_ROUTENODE_ADD_STARTER = "Which route do you want to add the node to?";
    public static final String PROMPT_ROUTENODE_ADD_NODEINDEX =
            "What is the index of the node? (Enter 0 to add to the end of the route)";
    public static final String PROMPT_ROUTENODE_ADD_TYPE = "Is it a bus stop or a train station?";
    public static final String PROMPT_ROUTENODE_ADD_INPUT = "What is the bus stop number or train station name?";
    public static final String PROMPT_ROUTENODE_ADD_SUCCESS = "Route node added successfully!";

    public static final String PROMPT_ROUTENODE_DELETE_STARTER = "Which route does the node belong to?";
    public static final String PROMPT_ROUTENODE_DELETE_NODEINDEX = "What is the index of the node?";
    public static final String PROMPT_ROUTENODE_DELETE_SUCCESS = "Route node deleted successfully!";

    public static final String PROMPT_ROUTENODE_EDIT_STARTER = "Which route does the edited node belong to?";
    public static final String PROMPT_ROUTENODE_EDIT_NODEINDEX = "What is the index of the node?";
    public static final String PROMPT_ROUTENODE_EDIT_FIELD = "What is the field that you want to edit?";
    public static final String PROMPT_ROUTENODE_EDIT_VALUE = "What would you want to change it to?";
    public static final String PROMPT_ROUTENODE_EDIT_SUCCESS = "Route node edited successfully!";

    public static final String PROMPT_ROUTENODE_SHOW_STARTER = "Which route does the node belong to?";
    public static final String PROMPT_ROUTENODE_SHOW_NODEINDEX = "What is the index of the node?";
    public static final String PROMPT_ROUTENODE_SHOW_SUCCESS = "Here is the route node";

    public static final String PROMPT_ROUTE_LIST_STARTER = "Which route would you like to see?";
    public static final String PROMPT_ROUTE_LIST_SUCCESS = "Here is the route:";

    public static final String PROMPT_ROUTE_SELECTOR_DISPLAY = "Showing node:\n";
    public static final String PROMPT_ROUTE_SELECTOR_NODE = "Currently at:\n";

    public static final String PROMPT_ROUTE_SELECTOR_SELECT_STARTER = "Please select a route!\n";
    public static final String PROMPT_ROUTE_SELECTOR_SELECT_SUCCESS = "The route has been selected:\n";

    public static final String PROMPT_ROUTE_SELECTOR_NODESELECT_STARTER = "Please select a node!\n";
    public static final String PROMPT_ROUTE_SELECTOR_NODESELECT_SUCCESS = "The node has been selected\n";

    public static final String PROMPT_ROUTE_STARTING_POINT = "Choose your starting point.";
    public static final String PROMPT_ROUTE_ENDING_POINT = "Choose your end point.";

    public static final String STARTUP_WELCOME_MESSAGE = "Welcome to SGTravel\n";
    public static final String PROMPT_CANCEL = "Current conversation has ended.";

    public static final String ITINERARY_INSUFFICIENT_AGENDAS = "I'm sorry, but you have entered wrong number of"
            + " days :-(";
    public static final String ITINERARY_EMPTY_TODOLIST = "I'm sorry, but entering todos for a day is compulsory";
    public static final String ITINERARY_FAIL_CREATION = "I'm sorry, but you have not entered your "
            + "itinerary properly";
    public static final String ITINERARY_INCORRECT_COMMAND = "I'm sorry, but your command syntax is wrong ";
    public static final String ITINERARY_NOT_FOUND = "I'm sorry, but that list does not exist ";


    public static final String RECOMMENDATION_DAY_EXCEEDED = "I'm sorry, but your stay is too long ";

    public static final String START_END_DATE_DISCORD = "Im sorry, there is some discrepancy between your dates";
    public static final String START_END_DATE_BEFORE_NOW = "Im sorry, your dates must be in the future";
    public static final String PROFILE_BIRTHDAY_IN_FUTURE = "Im sorry, your birthday must be in the past";
}
