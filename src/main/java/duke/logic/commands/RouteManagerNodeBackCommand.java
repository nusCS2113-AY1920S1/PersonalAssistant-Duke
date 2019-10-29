package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.results.CommandResultImage;
import duke.model.Model;

/**
 * Class representing a command to handle a RouteManagerNodeBackCommand.
 */
public class RouteManagerNodeBackCommand extends Command {

    /**
     * Executes this command and returns a n image result.
     *
     * @param model The model object containing event list.
     */
    @Override
    public CommandResultImage execute(Model model) throws DukeException {
        int routeIndex = model.getRouteManager().getRouteIndex() - 1;
        int nodeIndex = model.getRouteManager().getNodeIndex();
        if (nodeIndex != -1) {
            nodeIndex = Math.max(nodeIndex - 2, 0);
            model.getRouteManager().setNode(nodeIndex);
        }

        if (routeIndex != -1 || nodeIndex != -1) {
            RouteNodeShowCommand command = new RouteNodeShowCommand(routeIndex, nodeIndex);
            return command.execute(model);
        } else {
            throw new DukeException("Route node not selected.");
        }
    }
}
