package duke.logic.commands;

import duke.logic.commands.results.CommandResultExit;
import duke.model.Model;

/**
 * Exits SGTravel.
 */
public class ExitCommand extends Command {
    private static final String MESSAGE_BYE = "Bye. Hope to see you again soon!\n";

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultExit execute(Model model) {
        CommandResultExit commandResult = new CommandResultExit(MESSAGE_BYE);
        return commandResult;
    }
}
