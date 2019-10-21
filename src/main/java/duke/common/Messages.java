package duke.common;

/**
 * A class to store all the initialisation of the static error messages to String value.
 */
public class Messages {

    public static final int DISPLAYED_INDEX_OFFSET = 1;

    public static final String filePath = "/Users/jiawei/Desktop/main/src/main/data/duke.txt";
    public static final String filePathInventory = "/Users/jiawei/Desktop/main/src/main/data/ingredients.txt";
    public static final String filePathRecipeIngredients = "/Users/jiawei/Desktop/main/src/main/data/recipeIngredients.txt";
    public static final String filePathRecipeTitle = "/Users/jiawei/Desktop/main/src/main/data/recipeTitle.txt";
    public static final String filePathBookings = "/Users/jiawei/Desktop/main/src/main/data/bookings.txt";
    public static final String filePathRecipes = "/Users/jiawei/Desktop/main/src/main/data/recipes.txt";

    public static final String MESSAGE_ADDED = "     Got it. I've added this task:\n";
    public static final String MESSAGE_BYE = "     Bye. Hope to see you again soon!";
    public static final String MESSAGE_DELETE = "     Noted. I've removed this task:\n";
    public static final String MESSAGE_FIND = "     Here are the matching tasks in your list:";
    public static final String MESSAGE_FOLLOWUP_EMPTY_INDEX = "     Kindly enter the command again with an index.";
    public static final String MESSAGE_FOLLOWUP_NUll = "     Kindly enter the command again with a description.";
    public static final String MESSAGE_FOLLOWUP_CONFIRM = "      "
            + "Kindly enter command with the index of the tasks. Eg: confirm 2";
    public static final String MESSAGE_ITEMS1 = "     Now you have ";
    public static final String MESSAGE_ITEMS2 = " tasks in the list.";
    public static final String MESSAGE_MARKED = "     Nice! I've marked this task as done:\n";
    public static final String MESSAGE_SCHEDULED = "     Nice! This task has been scheduled:\n";
    public static final String MESSAGE_TASKED = "     Here are the tasks in your list:";
    public static final String MESSAGE_SNOOZE = "     Noted. I've rescheduled this task:\n";
    public static final String MESSAGE_REMIND = "     Here are your upcoming deadlines:";
    public static final String MESSAGE_TENTATIVE = "     Here are your tentative scheduled tasks: ";

    public static final String ERROR_MESSAGE_DEADLINE = "       "
            + "OOPS!!! Please specify the deadline in this format: \n       "
            + "deadline [event description] /by [day/month/year time]\n         "
            + "Eg: 28/8/2019 2359 (For date and time format)";
    public static final String ERROR_MESSAGE_EMPTY_INDEX = "     OOPS!!! The index cannot be empty.\n";
    public static final String ERROR_MESSAGE_EMPTY_LIST = "     OOPS!!! The list is empty.\n     Kindly add a task.";
    public static final String ERROR_MESSAGE_EVENT = "       "
            + "OOPS!!! Please specify the event details in this format: \n       "
            + "event [event description] /at [event time or venue]";
    public static final String ERROR_MESSAGE_DO_AFTER = "       "
            + "OOPS!!! Please specify the todo details in this format: \n       "
            + "todo [do after description] /after [specific time or task]";
    public static final String ERROR_MESSAGE_GENERAL = "     OOPS!!! The description cannot be empty.\n";
    public static final String ERROR_MESSAGE_INVALID_DATE = "       "
            + "OOPS!!! Please specify the date and time in this format: \n       "
            + "[day/month/year time] Eg: 28/8/2019 2359";
    public static final String ERROR_MESSAGE_INVALID_INDEX = "     Invalid index entered.\n     "
            + "Kindly enter command with index not more than ";
    public static final String ERROR_MESSAGE_UNKNOWN_INDEX = "     Unknown index entered.\n     "
            + "Kindly enter an integer for the index.";
    public static final String ERROR_MESSAGE_LOADING = "     OOPS!!! Error loading file: ";
    public static final String ERROR_MESSAGE_NOTFOUND = "     "
            + "OOPS!!! I'm sorry, but there is no matching tasks in your list.";
    public static final String ERROR_MESSAGE_RANDOM = "     OOPS!!! I'm sorry, but I don't know what that means :-(";
    public static final String ERROR_MESSAGE_DURATION = "       "
            + "☹ OOPS!!! Please specify the fix duration task details in this format: \n         "
            + "fixed [task description] /need [task duration]";
    public static final String ERROR_MESSAGE_PERIOD = "     Please include the time period for this task.\n"
            + "     Tasks to be completed within a time period should be entered in this format:\n"
            + "     period [task description] /between [start date] /and [end date]";
    public static final String ERROR_MESSAGE_PERIOD2 = "     Please provide both the start and end date for the task.";
    public static final String ERROR_MESSAGE_VIEWSCHEDULE = "     Please specify date [day/month/year] in this format.";
    public static final String ERROR_MESSAGE_TENTATIVESCHEDULE = "       "
            + "OOPS!!! Please specify tentative scheduling in this format: \n       "
            + "tentativeschedule [task description] /on [day/month/year time], [day/month/year time], ......";
    public static final String ERROR_MESSAGE_RECURRING_FREQUENCY_NULL = "     "
            + "Please include the frequency of the recurrent task.\n"
            + "     Duke supports recurring tasks in this format:\n"
            + "     1. recurring [task description] /daily\n"
            + "     2. recurring [task description] /weekly [day of week] Eg: thurs\n"
            + "     3. recurring [task description] /monthly [day of month 'dd'] Eg: 01\n";
    public static final String ERROR_MESSAGE_RECURRING_DETAILS_NULL = "     "
            + "Please provide the task description and day or date of recurrence.";

    public static final String COMMAND_BYE = "bye";
    public static final String COMMAND_HELP = "help";
    public static final String COMMAND_DEADLINE = "deadline";
    public static final String COMMAND_DELETE = "delete";
    public static final String COMMAND_DONE = "done";
    public static final String COMMAND_EVENT = "event";
    public static final String COMMAND_FIND = "find";
    public static final String COMMAND_LIST = "list";
    public static final String COMMAND_TODO = "todo";
    public static final String COMMAND_DURATION = "fixed";
    public static final String COMMAND_SNOOZE = "snooze";
    public static final String COMMAND_PERIOD = "period";
    public static final String COMMAND_REMIND = "reminders";
    public static final String COMMAND_VIEWSCHEDULE = "viewschedule";
    public static final String COMMAND_TENTATIVESCHEDULE = "tentativeschedule";
    public static final String COMMAND_CONFIRMSCHEDULE = "confirmschedule";
    public static final String COMMAND_CONFIRM = "confirm";
    public static final String COMMAND_RECURRING = "recurring";
    public static final String COMMAND_DETECTCLASHES = "detectclashes";

    public static final String DIVIDER = "   ____________________________________________________________\n";
}