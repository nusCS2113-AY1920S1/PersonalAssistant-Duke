package duke.logic.commands;

import duke.commons.exceptions.CorruptedFileException;
import duke.commons.exceptions.FileNotSavedException;
import duke.commons.exceptions.QueryOutOfBoundsException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;
import duke.model.transports.Route;

/**
 * Class representing a command to delete a RouteNode from RouteList.
 */
public class RouteNodeDeleteCommand extends Command {
    private int indexRoute;
    private int indexNode;
    private static final String MESSAGE_DELETION = "Got it. I've deleted this Route Node:\n  ";

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
     * @throws CorruptedFileException If the file is corrupted.
     * @throws FileNotSavedException If the file is not saved.
     * @throws QueryOutOfBoundsException If the query is out of bounds.
     */
    @Override
    public CommandResultText execute(Model model) throws CorruptedFileException, FileNotSavedException,
            QueryOutOfBoundsException {
        try {
            Route route = model.getRoutes().get(indexRoute);
            String address = route.getNode(indexNode).getAddress();
            route.remove(indexNode);
            model.save();
            return new CommandResultText(MESSAGE_DELETION + address);
        } catch (IndexOutOfBoundsException e) {
            throw new QueryOutOfBoundsException("ROUTE_NODE");
        }
    }
}
