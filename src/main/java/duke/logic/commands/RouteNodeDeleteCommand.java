package duke.logic.commands;

import duke.commons.Messages;
import duke.commons.exceptions.DukeException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;
import duke.model.transports.Route;

/**
 * Deletes a RouteNode from RouteList.
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
     */
    @Override
    public CommandResultText execute(Model model) throws DukeException {
        try {
            Route route = model.getRoutes().get(indexRoute);
            String address = route.getNode(indexNode).getAddress();
            route.remove(indexNode);
            model.save();
            return new CommandResultText(MESSAGE_DELETION + address);
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException(Messages.OUT_OF_BOUNDS);
        }
    }
}
