package sgtravel.logic.commands;

import sgtravel.commons.exceptions.ApiException;
import sgtravel.commons.exceptions.OutOfBoundsException;
import sgtravel.logic.api.ApiParser;
import sgtravel.logic.commands.results.CommandResultImage;
import sgtravel.model.Model;
import sgtravel.model.locations.RouteNode;
import sgtravel.model.locations.Venue;
import sgtravel.model.transports.Route;
import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Shows nearby neighbours of a given RouteNode.
 */
public class RouteNodeNeighboursCommand extends Command {
    private static final String MESSAGE_SUCCESS = "Here are some Nodes that are close to this:\n";
    private static final String MESSAGE_FAILURE = "I'm sorry, but it is out of bounds...\n";
    private int indexRoute;
    private int indexNode;

    /**
     * Creates a new RouteNodeNeighboursCommand with the given parameters.
     *
     * @param indexRoute The index of Route in RouteList.
     * @param indexNode The index of Node in RouteList.
     */
    public RouteNodeNeighboursCommand(int indexRoute, int indexNode) {
        this.indexRoute = indexRoute;
        this.indexNode = indexNode;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     * @return The CommandResultImage.
     */
    @Override
    public CommandResultImage execute(Model model) throws OutOfBoundsException {
        try {
            Route route = model.getRoutes().get(indexRoute);
            RouteNode node = model.getRoutes().get(indexRoute).getNode(indexNode);
            ArrayList<Venue> result = ApiParser.getNeighbour(model, node);

            try {
                Image image = ApiParser.generateStaticMapNeighbours(model, route, node, indexNode);
                return new CommandResultImage(image, MESSAGE_SUCCESS, result);
            } catch (ApiException e) {
                return new CommandResultImage(null, MESSAGE_SUCCESS, result);
            }
        } catch (IndexOutOfBoundsException | OutOfBoundsException e) {
            return new CommandResultImage(MESSAGE_FAILURE, null);
        }
    }
}
