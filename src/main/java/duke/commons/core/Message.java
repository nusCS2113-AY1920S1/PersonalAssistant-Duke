package duke.commons.core;

/**
 * Container for user visible messages.
 */
public class Message {
    public static final String MESSAGE_UNKNOWN_COMMAND = "This is an unknown command.";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Command format is wrong.";
    public static final String MESSAGE_INVALID_PREFIX = "Prefix %s does not exist.";
    public static final String MESSAGE_INVALID_DATE = "Date is invalid.";
    public static final String MESSAGE_ITEM_MISSING_NAME_OR_QUANTITY = "Item name or quantity is not specified.";
    public static final String MESSAGE_INVALID_NUMBER_FORMAT = "Please enter a valid number";
    public static final String MESSAGE_INVALID_QUANTITY = "Please enter a valid quantity";
    public static final String MESSAGE_INVALID_INDEX = "Index is invalid.";
    public static final String MESSAGE_INVALID_RANGE = "Range is invalid.";
    public static final String MESSAGE_INVALID_STATUS = "Status is invalid.";
    public static final String MESSAGE_INVALID_CRITERIA = "This is not a valid sorting criteria.";
    public static final String MESSAGE_INDEX_OUT_OF_BOUND = "Index does not exist.";
    public static final String MESSAGE_ORDER_ALREADY_COMPLETED = "Order at index [%d] has already been completed.";
    public static final String MESSAGE_INVALID_PREFIX_VALUE = "[%s] is an invalid parameter value.";

    private Message() {

    }// prevent initialization
}
