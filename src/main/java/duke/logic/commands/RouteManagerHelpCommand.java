package duke.logic.commands;

import duke.logic.commands.results.CommandResultText;
import duke.model.Model;

/**
 * Shows the help message in context for the Route Manager.
 */
public class RouteManagerHelpCommand extends Command {

    /**
     * Executes this command and returns a text result.
     *
     * @param model The model object containing event list.
     */
    @Override
    public CommandResultText execute(Model model) {

        return new CommandResultText(model.getRouteManager().getHelpMessage());
    }
}
