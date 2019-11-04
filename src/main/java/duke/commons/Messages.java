package duke.commons;

/**
 * Defines messages used by SGTravel.
 */
public class Messages {
    /**
     * Error messages.
     * Format is ERROR_TYPE_DESCRIPTION
     */
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
    public static final String ERROR_TASK_DUPLICATED = "Task already exists! :-(";
    public static final String ERROR_ROUTE_DUPLICATE = "Route already exists! :-(";
    public static final String ERROR_ROUTE_NOT_FOUND = "Route not found! :-(";
    public static final String ERROR_ROUTE_NODE_DUPLICATE = "Node already exists! :-(";
    public static final String ERROR_ROUTE_GENERATE_FAIL = "The Route failed to generate, try other locations :-(";
    public static final String ERROR_CATEGORY_NOT_FOUND = "Category does not exists! :-(";
    public static final String ERROR_API_FAIL = "I'm sorry, but nothing could be found. :-(";
    public static final String ERROR_VENUE_EMPTY = "There are no Venues. VenueList is empty.";
    public static final String ERROR_EVENT_NOT_SELECTED = "There are no events selected.";
    public static final String ERROR_RESULT_NOT_FOUND = "Nothing could be found.";
    public static final String ERROR_BUS_SERVICE_NOT_FOUND = "I'm sorry, there is no such bus service.";
    public static final String ERROR_BUS_STOP_NOT_FOUND = "I'm sorry, there is no such bus stop.";
    public static final String ERROR_DATE_INCONSISTENT = "Im sorry, there is some discrepancy between your dates";
    public static final String ERROR_DATE_BEFORE_NOW = "Im sorry, your dates must be in the future";
    public static final String ERROR_ITINERARY_EMPTY_TODOLIST = "I'm sorry, but entering todos for a day is compulsory";
    public static final String ERROR_ITINERARY_FAIL_CREATION = "I'm sorry, but you have not entered your "
            + "itinerary properly";
    public static final String ERROR_ITINERARY_INCORRECT_COMMAND = "I'm sorry, but your itinerary command syntax is wrong ";
    public static final String ERROR_ITINERARY_NOT_FOUND = "I'm sorry, but that itinerary list does not exist ";
    public static final String ERROR_ITINERARY_NO_RECENT = "I'm sorry, there are no recent itinerary to add.";
    public static final String ERROR_RECOMMENDATION_FAIL = "I'm sorry, the days of the trip is too long.\n"
            + "I couldn't provide a good recommendation.";

    /**
     * Messages sent via Prompt by Duke.
     * Format is PROMPT_TYPE_DESCRIPTION
     */
    public static final String PROMPT_ERROR = "Sorry, but something went wrong...";
    public static final String PROMPT_NOT_INT = "Please use a number!";
    public static final String PROMPT_NOT_DOUBLE = "Please use a number!";
    public static final String PROMPT_NOT_DATE = "Please use a proper date!";
    public static final String PROMPT_NOT_ROUTE_FIELD = "Please choose either name or description!";
    public static final String PROMPT_NOT_ROUTENODE_FIELD = "Please use a proper field!";
    public static final String PROMPT_SEARCH_STARTER = "Where would you like to find?";
    public static final String PROMPT_SEARCH_SUCCESS = "These are the coordinates of your search:";
    public static final String PROMPT_FIND_STARTER = "What task would you like to find?";
    public static final String PROMPT_DELETE_STARTER = "What would you like to delete?";
    public static final String PROMPT_GETBUSSTOP_STARTER = "Which bus stop do you want to find?";
    public static final String PROMPT_MARKDONE_STARTER = "Which task have you completed?";
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
    public static final String PROMPT_ROUTE_SELECTOR_DISPLAY = "Showing node:\n";
    public static final String PROMPT_CANCEL = "Current conversation has ended.";

    public static final String STARTUP_WELCOME_MESSAGE = "Welcome to SGTravel\n";
}
