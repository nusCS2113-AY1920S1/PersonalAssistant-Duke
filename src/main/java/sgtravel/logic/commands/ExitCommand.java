package sgtravel.logic.commands;

import sgtravel.commons.Messages;
import sgtravel.logic.commands.results.CommandResultExit;
import sgtravel.model.Model;

/**
 * Exits SGTravel.
 */
public class ExitCommand extends Command {
    /**
     * Executes this command and returns a text result.
     *
     * @param model The model object containing event list.
     */
    @Override
    public CommandResultExit execute(Model model) {
        return new CommandResultExit(Messages.EXIT_SUCCESS);
    }
}
