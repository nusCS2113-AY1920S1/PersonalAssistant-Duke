package compal.logic.command;

import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.TaskList;


public class ByeCommand extends Command {
    @Override
    public CommandResult commandExecute(TaskList task) {
        return new CommandResult("bye.",true);
    }
}
