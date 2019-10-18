package compal.logic.command;

import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Task;

import java.util.ArrayList;

public class ByeCommand extends Command {
    @Override
    public CommandResult commandExecute(ArrayList<Task> task) throws CommandException {
        return new CommandResult("bye.");
    }
}
