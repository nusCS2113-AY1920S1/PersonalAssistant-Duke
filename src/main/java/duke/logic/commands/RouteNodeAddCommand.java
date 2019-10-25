package duke.logic.commands;

import duke.commons.exceptions.CorruptedFileException;
import duke.commons.exceptions.FileNotSavedException;
import duke.commons.exceptions.QueryOutOfBoundsException;
import duke.commons.exceptions.RouteNodeDuplicateException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;
import duke.model.transports.Route;
import duke.model.locations.RouteNode;

/**
 * Adds a RouteNode to RouteList.
 */
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
        this.indexRoute = indexRoute;
        this.indexNode = indexNode;
        this.isEmptyIndexNode = isEmptyIndexNode;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     * @return The CommandResultText.
     * @throws CorruptedFileException If the file is corrupted.
     * @throws FileNotSavedException If the file is not saved.
     * @throws RouteNodeDuplicateException If there is a duplicate RouteNode.
     * @throws QueryOutOfBoundsException If the query is out of bounds.
     */
    @Override
    public CommandResultText execute(Model model) throws CorruptedFileException, FileNotSavedException,
            RouteNodeDuplicateException, QueryOutOfBoundsException {
        Route route = model.getRoutes().get(indexRoute);

        if (isEmptyIndexNode) {
            route.addNode(node);
        } else if (indexNode >= 0) {
            route.addNode(node, indexNode);
        } else {
            throw new QueryOutOfBoundsException("ROUTE_NODE");
        }

        model.save();
        return new CommandResultText(MESSAGE_ADDITION + node.toString());
    }
}
