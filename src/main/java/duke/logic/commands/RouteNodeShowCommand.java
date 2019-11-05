package duke.logic.commands;

import duke.commons.Messages;
import duke.commons.exceptions.ApiException;
import duke.commons.exceptions.OutOfBoundsException;
import duke.logic.api.ApiParser;
import duke.logic.commands.results.CommandResultImage;
import duke.model.Model;
import duke.model.locations.RouteNode;
import duke.model.transports.Route;
import javafx.scene.image.Image;

/**
 * Creates a StaticMap image of a Route for visualization.
 */
public class RouteNodeShowCommand extends Command {
    private int indexRoute;
    private int indexNode;

    /**
     * Creates a new RouteNodeShowCommand with the given parameters.
     *
     * @param indexRoute The index of the Route in RouteList.
     * @param indexNode indexNode The index of the Node in RouteList.
     */
    public RouteNodeShowCommand(int indexRoute, int indexNode) {
        this.indexRoute = indexRoute;
        this.indexNode = indexNode;
    }

    /**
     * Executes this command on the given Route and user interface.
     *
     * @param model The model object containing information about the user.
     * @return The CommandResultRouteMap.
     * @throws OutOfBoundsException If the query is out of bounds.
     */
    @Override
    public CommandResultImage execute(Model model) throws OutOfBoundsException {
        try {
            Route route = model.getRoutes().get(indexRoute);
            RouteNode node = model.getRoutes().get(indexRoute).getNode(indexNode);

            try {
                Image image = ApiParser.generateRouteNodeShow(route, node, indexNode);
                return new CommandResultImage(Messages.PROMPT_ROUTE_SELECTOR_DISPLAY + node.getDisplayInfo(), image);
            } catch (ApiException e) {
                return new CommandResultImage(Messages.PROMPT_ROUTE_SELECTOR_DISPLAY + node.getDisplayInfo(), null);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new OutOfBoundsException();
        }
    }
}
