package sgtravel.logic.commands;

import sgtravel.commons.Messages;
import sgtravel.commons.exceptions.ApiException;
import sgtravel.commons.exceptions.FileNotSavedException;
import sgtravel.commons.exceptions.QueryFailedException;
import sgtravel.commons.exceptions.OutOfBoundsException;
import sgtravel.commons.exceptions.DuplicateRouteNodeException;
import sgtravel.logic.commands.results.CommandResultImage;
import sgtravel.model.Model;
import sgtravel.model.locations.BusStop;
import sgtravel.model.locations.TrainStation;
import sgtravel.model.transports.Route;
import sgtravel.model.locations.RouteNode;

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
        try {
            fetchNodeData(model);
        } catch (QueryFailedException e) {
            if (node instanceof BusStop) {
                return new CommandResultImage(Messages.ERROR_BUS_STOP_NOT_FOUND_STARTER + e.getQueriedItem()
                        + Messages.ERROR_BUS_STOP_NOT_FOUND_END, null);
            } else {
                return new CommandResultImage(Messages.ERROR_TRAIN_STATION_NOT_FOUND_STARTER + e.getQueriedItem()
                        + Messages.ERROR_TRAIN_STATION_NOT_FOUND_END, null);
            }
        }

        try {
            addToRoute(model);
        } catch (IndexOutOfBoundsException e) {
            throw new OutOfBoundsException();
        }

        model.save();

        return new RouteNodeShowCommand(indexRoute, indexNode).execute(model);
    }

    /**
     * Adds a RouteNode to the Route.
     *
     * @param model The model object containing information about the user.
     * @throws OutOfBoundsException If the indexes are out of bounds.
     * @throws DuplicateRouteNodeException If the RouteNode is a duplicate.
     */
    private void addToRoute(Model model) throws OutOfBoundsException, DuplicateRouteNodeException {
        Route route = model.getRoute(indexRoute);
        if (isEmptyIndexNode) {
            route.addNode(node);
            indexNode = route.size() - 1;
        } else if (indexNode >= 0) {
            route.addNode(node, indexNode);
        } else {
            throw new OutOfBoundsException();
        }
    }

    /**
     * Fetches the RouteNode data from the Model.
     *
     * @param model The model object containing information about the user.
     * @throws QueryFailedException If the fetching of data fails.
     */
    private void fetchNodeData(Model model) throws QueryFailedException {
        if (node instanceof BusStop) {
            ((BusStop) node).fetchData(model);
        } else if (node instanceof TrainStation) {
            ((TrainStation) node).fetchData(model);
        }
    }
}
