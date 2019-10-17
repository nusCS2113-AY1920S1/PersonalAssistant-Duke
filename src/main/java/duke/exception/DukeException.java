package duke.exception;

/**
 * The exception Duke throws upon encountering a problem that can be recovered from.
 */
public class DukeException extends Exception {
    public static final String MESSAGE_LOAD_FILE_FAILED = "The file at %s could not be loaded. "
        + "I will back it up and create a new file.";
    public static final String MESSAGE_SAVE_FILE_FAILED = "The file at %s could not be saved to. "
        + "Close other programs that may be accessing it.";
    public static final String MESSAGE_NO_ITEM_AT_INDEX = "There is no %s numbered %d!";
    public static final String MESSAGE_PARSER_TIME_INVALID = "%s is not a valid time!";
    public static final String MESSAGE_EXPENSE_AMOUNT_INVALID = "%s is not a valid amount!";
    public static final String MESSAGE_EXPENSE_TIME_INVALID = "%s is not a valid time!";
    public static final String MESSAGE_COMMAND_PARAM_UNKNOWN = "%s is not a valid parameter for this command!";
    public static final String MESSAGE_COMMAND_PARAM_MISSING_VALUE = "/%s needs a value!";
    public static final String MESSAGE_COMMAND_PARAM_MISSING = "This command needs /%s to be given!";
    public static final String MESSAGE_COMMAND_PARAM_DUPLICATE = "/%s cannot be specified twice!";
    public static final String MESSAGE_COMMAND_NAME_UNKNOWN = "I don't know what command that is!";
    public static final String MESSAGE_BUDGET_AMOUNT_INVALID = "%s is not a valid amount!";
    public static final String MESSAGE_EXPENSE_SORT_CRITERIA_INVALID = "%s is not a valid sort criteria!";
    public static final String MESSAGE_EXPENSE_VIEW_SCOPE_NAME_INVALID = "%s is not a valid view scope name!";
    public static final String MESSAGE_EXPENSE_VIEW_SCOPE_NUMBER_INVALID = "%s is not a valid view scope number!";

    public DukeException(String message) {
        super(message);
    }
}
