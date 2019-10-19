package compal.logic.command;

import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.TaskList;

public class DeadlineCommand extends Command {

    @Override
    public CommandResult commandExecute(TaskList task) throws CommandException {
        return null;
    }
}
