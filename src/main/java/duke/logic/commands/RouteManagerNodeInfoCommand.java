package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.results.CommandResultImage;
import duke.model.Model;

/**
 * Shows the help message in context for the Route Manager.
 */
public class RouteManagerNodeInfoCommand extends Command {
    /**
     * Executes this command and returns a text result.
     *
     * @param model The model object containing event list.
     */
    @Override
    public CommandResultImage execute(Model model) throws DukeException {
        int routeIndex = model.getRouteManager().getRouteIndex() - 1;
        int nodeIndex = model.getRouteManager().getNodeIndex() - 1;
        if (routeIndex != -1 || nodeIndex != -1) {
            RouteNodeShowCommand command = new RouteNodeShowCommand(routeIndex, nodeIndex);
            return command.execute(model);
        } else {
            throw new DukeException("Route node not selected.");
        }
    }
}
