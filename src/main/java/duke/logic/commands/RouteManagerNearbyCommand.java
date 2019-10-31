package duke.logic.commands;

import duke.commons.exceptions.ApiException;
import duke.commons.exceptions.QueryOutOfBoundsException;
import duke.logic.commands.results.CommandResultImage;
import duke.model.Model;

/**
 * Shows the help message in context for the Route Manager.
 */
public class RouteManagerNearbyCommand extends Command {
    /**
     * Executes this command and returns a text result.
     *
     * @param model The model object containing event list.
     */
    @Override
    public CommandResultImage execute(Model model) throws ApiException, QueryOutOfBoundsException {
        int routeIndex = model.getRouteManager().getRouteIndex() - 1;
        int nodeIndex = model.getRouteManager().getNodeIndex() - 1;
        RouteNodeNeighboursCommand command = new RouteNodeNeighboursCommand(routeIndex, nodeIndex);

        return command.execute(model);
    }
}
