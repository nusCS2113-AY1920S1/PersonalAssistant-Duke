package duke.logic.commands;

import duke.commons.Messages;
import duke.commons.exceptions.DukeException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;
import duke.model.transports.Route;
import duke.model.locations.RouteNode;

public class RouteNodeAddCommand extends Command {
    private RouteNode node;
    private int indexRoute;
    private int indexNode;
    private boolean isEmptyIndexNode;
    private static final String MESSAGE_ADDITION = "Got it. I've added this route node:\n  ";

    /**
     * Creates a new RouteNodeAddCommand with the given node.
     *
     * @param node The node to add.
     */
    public RouteNodeAddCommand(RouteNode node, int indexRoute, int indexNode, boolean isEmptyIndexNode) {
        this.node = node;
        this.indexRoute = indexRoute - 1;
        this.indexNode = indexNode - 1;
        this.isEmptyIndexNode = isEmptyIndexNode;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultText execute(Model model) throws DukeException {
        Route route = model.getRoutes().get(indexRoute);

        if (isEmptyIndexNode) {
            route.addNode(node);
        } else if (indexNode >= 0) {
            route.addNode(node, indexNode);
        } else {
            throw new DukeException(Messages.OUT_OF_BOUNDS);
        }

        model.save();
        return new CommandResultText(MESSAGE_ADDITION + node.toString());
    }
}