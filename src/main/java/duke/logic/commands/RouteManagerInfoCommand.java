package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;

/**
 * Shows the help message in context for the Route Manager.
 */
public class RouteManagerInfoCommand extends Command {
    /**
     * Executes this command and returns a text result.
     *
     * @param model The model object containing event list.
     */
    @Override
    public CommandResultText execute(Model model) throws DukeException {
        int routeIndex = model.getRouteManager().getRouteIndex() - 1;
        int nodeIndex = model.getRouteManager().getNodeIndex() - 1;
        if (routeIndex >= 0 && nodeIndex <= -1) {
            RouteListCommand command = new RouteListCommand(routeIndex);
            return command.execute(model);
        } else if (routeIndex <= -1) {
            RouteListAllCommand command = new RouteListAllCommand();
            return command.execute(model);
        } else {
            throw new DukeException("Route not selected.");
        }
    }
}
