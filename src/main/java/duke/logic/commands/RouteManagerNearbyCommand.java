package duke.logic.commands;

import duke.commons.exceptions.ApiFailedRequestException;
import duke.commons.exceptions.ApiNullRequestException;
import duke.commons.exceptions.ApiTimeoutException;
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
    public CommandResultImage execute(Model model) throws ApiFailedRequestException, ApiTimeoutException,
            ApiNullRequestException {
        int routeIndex = model.getRouteManager().getRouteIndex() - 1;
        int nodeIndex = model.getRouteManager().getNodeIndex() - 1;
        RouteNodeNeighboursCommand command = new RouteNodeNeighboursCommand(routeIndex, nodeIndex);

        return command.execute(model);
    }
}
