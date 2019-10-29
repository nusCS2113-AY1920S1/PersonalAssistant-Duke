package duke.logic.commands;

import duke.logic.commands.results.CommandResultText;
import duke.model.Model;

/**
 * Lists items in the Event list.
 */
public class RouteManagerExitCommand extends Command {
    private static final String MESSAGE = "Exiting Route Manager...";

    /**
     * Executes this command and returns a text result.
     *
     * @param model The model object containing event list.
     */
    @Override
    public CommandResultText execute(Model model) {
        model.getRouteManager().turnOff();
        return new CommandResultText(MESSAGE);
    }
}
