package duke.logic.commands;

import duke.commons.Messages;
import duke.commons.exceptions.ApiException;
import duke.commons.exceptions.OutOfBoundsException;
import duke.logic.api.ApiParser;
import duke.logic.commands.results.CommandResultImage;
import duke.model.Model;
import duke.model.locations.BusStop;
import duke.model.locations.RouteNode;
import duke.model.locations.Venue;
import duke.model.transports.Route;
import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Creates a StaticMap image of a Route for visualization.
 */
public class RouteNodeShowCommand extends Command {
    private static final String DIMENSIONS = "512";
    private static final String ZOOM_LEVEL = "14";
    private static final String RED_VALUE_OTHER = "255";
    private static final String GREEN_VALUE_OTHER = "122";
    private static final String BLUE_VALUE_OTHER = "0";
    private static final String RED_VALUE_QUERY = "122";
    private static final String GREEN_VALUE_QUERY = "255";
    private static final String BLUE_VALUE_QUERY = "0";
    private static final String LINE_WIDTH = "2";
    private static final int NODE_MAX_SIZE = 9;
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
