package duke.common;

/**
 * A class to store all the initialisation of the static error messages to String value.
 */
public class Messages {

    public static final int DISPLAYED_INDEX_OFFSET = 1;

    public static final String filePathRecipesTest = "\\data\\recipesTest.txt";
    public static final String filePathBookingTest = "\\data\\bookingsTest.txt";
    public static final String filePathInventoryTest = "\\data\\recipesTest.txt";

    public static final String filePathInventory = "C:\\Users\\Wen Jian\\Desktop\\main\\src\\main\\data\\inventories.txt";
    public static final String filePathBookings = "C:\\Users\\Wen Jian\\Desktop\\main\\src\\main\\data\\bookings.txt";
    public static final String filePathRecipes = "C:\\Users\\Wen Jian\\Desktop\\main\\src\\main\\data\\recipes.txt";

    public static final String MESSAGE_ADDED = "     Got it. I've added this task:\n";
    public static final String MESSAGE_DELETE = "     Noted. I've removed this task:\n";
    public static final String MESSAGE_FOLLOWUP_NUll = "     Kindly enter the command again with a description.";
    public static final String MESSAGE_ITEMS1 = "     Now you have ";
    public static final String MESSAGE_ITEMS2 = " tasks in the list.";

    public static final String ERROR_MESSAGE_GENERAL = "     OOPS!!! The description cannot be empty.\n";
    public static final String ERROR_MESSAGE_INVALID_INDEX = "     Invalid index entered.\n     "
            + "Kindly enter command with index not more than ";
    public static final String ERROR_MESSAGE_UNKNOWN_INDEX = "     Unknown index entered.\n     "
            + "Kindly enter an integer for the index.";
    public static final String ERROR_MESSAGE_LOADING = "     OOPS!!! Error loading file: ";
    public static final String ERROR_MESSAGE_RANDOM = "     OOPS!!! I'm sorry, but I don't know what that means :-(";

    public static final String COMMAND_BYE = "bye";
    public static final String COMMAND_HELP = "help";

    public static final String DIVIDER = "_______________________________\n";
}