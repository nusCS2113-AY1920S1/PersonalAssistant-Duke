package sgtravel.logic.commands;

import sgtravel.logic.commands.results.CommandResultText;
import sgtravel.model.Model;

/**
 * Lists items in the Event list.
 */
public class ListCommand extends Command {
    /**
     * Executes this command and returns a text result.
     *
     * @param model The model object containing event list.
     */
    @Override
    public CommandResultText execute(Model model) {
        return new CommandResultText(model.getEvents());
    }
}
