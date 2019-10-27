package duke.logic.commands;

import duke.logic.commands.results.CommandResultText;
import duke.model.Model;

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
