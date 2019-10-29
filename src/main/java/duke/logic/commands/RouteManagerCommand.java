package duke.logic.commands;

import duke.commons.exceptions.EmptyVenueException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;

/**
 * Turns on the route selector mode on SGTravel.
 */
public class RouteManagerCommand extends Command {

    /**
     * Executes this command and returns a text result.
     *
     * @param model The model object containing event list.
     */
    @Override
    public CommandResultText execute(Model model) throws EmptyVenueException {
        model.getRouteManager().turnOn();
        return new CommandResultText(model.getRouteManager().getWelcomeMessage());
    }
}
