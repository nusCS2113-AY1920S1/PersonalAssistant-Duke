package compal.logic.command;

import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.TaskList;

//@@author SholihinK
public class ByeCommand extends Command {
    public static final String MESSAGE_USAGE = "Bye\n\t"
            + "Format: bye\n\t"
            + "e.g. \"bye\"\n\n"
            + "This command will exit COMPal. Have a nice day! :)";

    @Override
    public CommandResult commandExecute(TaskList task) {
        return new CommandResult("bye.", true);
    }
}
