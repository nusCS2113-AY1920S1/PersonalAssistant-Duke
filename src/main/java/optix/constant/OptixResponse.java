package optix.constant;

public class OptixResponse {

    public final String SPACES = "__________________________________________________________________________________\n";

    public final String GREET = "Hello! I'm Optix\n"
            + "What can I do for you?\n";

    public final String BYE = "Bye. Hope to see you again soon!\n";

    public final String LIST_FOUND = "Here are the list of shows:\n";

    public final String LIST_NOT_FOUND = "☹ OOPS!!! There are no shows in the near future.\n";

    public final String DONE_FOUND = "Nice! I've marked this task as done:\n";

    public final String DONE_COMPLETED = "☹ OOPS!!! This task has already been completed\n";

    public final String DELETE_FOUND = "Noted. I've removed this task:\n";

    public final String FIND_FOUND = "Here are the matching tasks in your list:\n";

    public final String NOT_FOUND = "☹ OOPS!!! The task cannot be found. \n";

    public final String EXCEPTION = "☹ OOPS!!! That is an invalid input\n"
            + "Please try again. \n";

    public final String ADD = "Got it. I've added this show:\n";

    public final String REMINDERS_FOUND = "Here are your reminders:\n";

    public final String REMINDERS_NOT_FOUND = "☹ OOPS!!! You currently do not have deadlines.\n";

    public final String FREE_TIME_FOUND = "You next free time for the required period is:\n";

    public final String WRONG_TASKTYPE = "☹ OOPS!!! Snooze cannot be used for Todos.\n";

    public final String SNOOZE_SUCCESS = "Noted. I've snoozed this task:\n";

    public final String MENU = "Here are the Commands to use Optix: \n"
                                + "To add a new show:                               add SHOW_NAME|SCHEDULED_DATE|PRICE|SEATS_BASE_PRICE\n"
                                + "To delete shows with particular name:            delete-all SHOW_NAME_1|SHOW_NAME_2 | ...\n"
                                + "To delete shows with specific name and date:     delete SHOW_NAME|SHOW_DATE\n"
                                + "To list all shows for viewing:                   list\n"
                                + "To list all dates for a specific show:           list SHOW_NAME\n"
                                + "To postpone a show to a later date:              postpone SHOW_NAME | OLD_DATE | NEW_DATE\n"
                                + "To sell seats to the audience:                   sell SHOW_NAME | SHOW_DATE | BUYER_NAME\n"
                                + "To view availability of seats for a show:        view SHOW_NAME | SHOW_DATE\n";
}
