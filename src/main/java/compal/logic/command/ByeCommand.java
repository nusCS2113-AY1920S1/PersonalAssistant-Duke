package compal.logic.command;

import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;

import java.util.ArrayList;

public class ByeCommand extends Command {
    @Override
    public CommandResult commandExecute(TaskList task) throws CommandException {
        return new CommandResult("bye.");
    }
}
