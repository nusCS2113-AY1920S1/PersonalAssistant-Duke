package compal.logic.command;

import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.TaskList;


public abstract class Command {
    /**
     * Executes the command and returns the result message.
     *
     * @param task {@code Model} which the command should operate on.
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult commandExecute(TaskList task) throws CommandException;
}