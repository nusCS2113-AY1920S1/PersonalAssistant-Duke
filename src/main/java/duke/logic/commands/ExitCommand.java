package duke.logic.commands;

import duke.logic.commands.results.CommandResultExit;
import duke.model.Model;

/**
 * Exits SGTravel.
 */
public class ExitCommand extends Command {
    private static final String MESSAGE_BYE = "Bye. Hope to see you again soon!\n";

    /**
     * Executes this command and returns a text result.
     *
     * @param model The model object containing event list.
     */
    @Override
    public CommandResultExit execute(Model model) {
        return new CommandResultExit(MESSAGE_BYE);
    }
}
