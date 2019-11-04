package duke.logic.commands;

import duke.commons.exceptions.ApiException;
import duke.commons.exceptions.FileNotSavedException;
import duke.commons.exceptions.QueryFailedException;
import duke.commons.exceptions.OutOfBoundsException;
import duke.commons.exceptions.DuplicateRouteNodeException;
import duke.logic.commands.results.CommandResultImage;
import duke.model.Model;
import duke.model.locations.BusStop;
import duke.model.locations.TrainStation;
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
     * @throws FileNotSavedException If the file is not saved.
     * @throws DuplicateRouteNodeException If there is a duplicate RouteNode.
     * @throws OutOfBoundsException If the query is out of bounds.
     * @throws QueryFailedException If the query fails.
     * @throws ApiException If the api call fails.
     */
    @Override
    public CommandResultImage execute(Model model) throws QueryFailedException, DuplicateRouteNodeException,
            OutOfBoundsException, FileNotSavedException, ApiException {
        if (node instanceof BusStop) {
            ((BusStop) node).fetchData(model);
        } else if (node instanceof TrainStation) {
            ((TrainStation) node).fetchData(model);
        }

        try {
            Route route = model.getRoutes().get(indexRoute);
            if (isEmptyIndexNode) {
                route.add(node);
                indexNode = route.size() - 1;
            } else if (indexNode >= 0) {
                route.addNode(node, indexNode);
            } else {
                throw new OutOfBoundsException();
            }

        } catch (IndexOutOfBoundsException e) {
            throw new OutOfBoundsException();
        }

        model.save();

        return new RouteNodeShowCommand(indexRoute, indexNode).execute(model);
    }
}
