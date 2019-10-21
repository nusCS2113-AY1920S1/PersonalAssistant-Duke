package duke.logic.commands;

import duke.commons.enumerations.Constraint;
import duke.commons.exceptions.DukeException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;
import duke.model.RouteList;
import duke.model.locations.Route;
import duke.model.locations.RouteNode;

public class RouteNodeAddCommand extends Command {
    private RouteNode node;
    private int indexRoute;
    private int indexNode;
    private static final String MESSAGE_ADDITION = "Got it. I've added this route node:\n  ";

    /**
     * Creates a new RouteNodeAddCommand with the given node.
     *
     * @param node The node to add.
     */
    public RouteNodeAddCommand(RouteNode node, int indexRoute, int indexNode) {
        this.node = node;
        this.indexRoute = indexRoute;
        this.indexNode = indexNode;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultText execute(Model model) throws DukeException {
        Route route = model.getRoutes().get(indexRoute - 1);
        if (indexNode != 0) {
            route.addNode(node, indexNode - 1);
        } else {
            route.addNode(node);
        }
        model.save();
        return new CommandResultText(MESSAGE_ADDITION + node.toString());
    }
}