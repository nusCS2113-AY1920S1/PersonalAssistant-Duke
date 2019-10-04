package duke.logic.command.commons;

import duke.logic.command.exceptions.CommandException;
import duke.model.Model;

/**
 * A command that is executable by the user.
 */
public abstract class Command {

    /**
     * Executes the command.
     *
     * @param model A BakingList object containing application data.
     * @throws CommandException If the execution fails.
     */
    public abstract CommandResult execute(Model model) throws CommandException;
}
