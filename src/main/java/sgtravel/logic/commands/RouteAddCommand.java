package sgtravel.logic.commands;

import sgtravel.commons.exceptions.FileNotSavedException;
import sgtravel.commons.exceptions.DuplicateRouteException;
import sgtravel.logic.commands.results.CommandResultText;
import sgtravel.model.Model;
import sgtravel.model.lists.RouteList;
import sgtravel.model.transports.Route;

/**
 * Adds a Route to RouteList.
 */
public class RouteAddCommand extends Command {
    private static final String MESSAGE_ADDITION = "Got it. I've added this route:\n";
    private String name;
    private String description;

    /**
     * Creates a new RouteAddCommand with the given name.
     *
     * @param name The name of the route.
     * @param description The description of the route.
     */
    public RouteAddCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Executes this command on the given Route List and user interface.
     *
     * @param model The model object containing information about the user.
     * @return The CommandResultText.
     * @throws DuplicateRouteException If there is a duplicate route node.
     * @throws FileNotSavedException If the data cannot be saved.
     */
    @Override
    public CommandResultText execute(Model model) throws DuplicateRouteException, FileNotSavedException {
        RouteList routes = model.getRoutes();
        routes.add(new Route(name, description));
        model.save();
        return new CommandResultText(MESSAGE_ADDITION + name);
    }
}
