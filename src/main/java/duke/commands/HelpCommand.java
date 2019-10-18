package duke.commands;

import duke.commands.results.CommandResultText;
import duke.model.Model;

/**
 * Class representing a command to show the help message.
 */
public class HelpCommand extends Command {
    private static final String MESSAGE_HELP = "Here is the list of commands:\n"
            + "Add Tasks:\n"
            + "    To Do: todo <desc>\n"
            + "    Event: event <desc> /at <time>\n"
            + "    Deadline: deadline <desc> /by <time>\n"
            + "    Recurring Task: repeat <desc> /by <time> /every <num of days>\n"
            + "    Do Within Task: within <desc> /between <time> /and <time>\n"
            + "\n"
            + "Modifying Tasks:\n"
            + "    Snooze: snooze <index> /to <date>\n"
            + "\n"
            + "Task Querying\n"
            + "    Reminder: reminder\n"
            + "    View by Date: fetch <date>\n";

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultText execute(Model model) {
        return new CommandResultText(MESSAGE_HELP);
    }
}
