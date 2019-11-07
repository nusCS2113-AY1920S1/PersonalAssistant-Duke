package sgtravel.logic.commands;

import sgtravel.commons.Messages;
import sgtravel.commons.exceptions.FileNotSavedException;
import sgtravel.commons.exceptions.OutOfBoundsException;
import sgtravel.logic.commands.results.CommandResultText;
import sgtravel.model.Model;
import sgtravel.model.transports.Route;

/**
 * Deletes a RouteNode from RouteList.
 */
public class RouteNodeDeleteCommand extends Command {
    private int indexRoute;
    private int indexNode;

    /**
     * Deletes a Route Node at the given index and given Route.
     *
     * @param indexRoute The index of the Route.
     * @param indexNode The index of the Route Node.
     */
    public RouteNodeDeleteCommand(int indexRoute, int indexNode) {
        this.indexRoute = indexRoute;
        this.indexNode = indexNode;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     * @return The CommandResultText.
     * @throws FileNotSavedException If the file is not saved.
     * @throws OutOfBoundsException If the query is out of bounds.
     */
    @Override
    public CommandResultText execute(Model model) throws FileNotSavedException, OutOfBoundsException {
        try {
            Route route = model.getRoute(indexRoute);
            String address = route.getNode(indexNode).getAddress();
            route.remove(indexNode);
            model.save();
            return new CommandResultText(Messages.ROUTE_NODE_DELETE_SUCCESS + address);
        } catch (IndexOutOfBoundsException e) {
            throw new OutOfBoundsException();
        }
    }
}
