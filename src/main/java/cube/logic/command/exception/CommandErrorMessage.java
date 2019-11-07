package cube.logic.command.exception;

public class CommandErrorMessage {
    public static final String FOOD_ALREADY_EXISTS
        = "OOPS!!! The food already exists";
    public static final String FOOD_NOT_EXISTS
        = "OOPS!!! The food does not exists";
    public static final String INVALID_QUANTITY_SOLD
        = "OOPS!!! The quantity sold is negative or too large";
    public static final String INVALID_INDEX
        = "OOPS!!! The index is out of the range of food list";
    public static final String INVALID_TYPE
        = "OOPS!!! The food type does not exist";
    public static final String INVALID_EXPIRY_DATE
        = "OOPS!!! The food expiry date cannot be before today";
    public static final String INVALID_COMMAND_FORMAT
        = "OOPS!!! There are some issues with the command format.\n" +
        "Please view 'help' or our user guide for more info!";
}