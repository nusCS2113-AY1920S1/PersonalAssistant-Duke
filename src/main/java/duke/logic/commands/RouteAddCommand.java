package duke.logic.commands;

import duke.commons.exceptions.CorruptedFileException;
import duke.commons.exceptions.DukeDuplicateRouteException;
import duke.commons.exceptions.FileNotSavedException;
import duke.commons.exceptions.RouteNodeDuplicateException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;
import duke.model.lists.RouteList;
import duke.model.transports.Route;

/**
 * Adds a Route to RouteList.
 */
public class RouteAddCommand extends Command {
    private String name;
    private static final String MESSAGE_ADDITION = "Got it. I've added this route:\n  ";

    /**
     * Creates a new RouteAddCommand with the given name.
     *
     * @param name The index of the task.
     */
    public RouteAddCommand(String name) {
        this.name = name;
    }

    /**
     * Executes this command on the given Route List and user interface.
     *
     * @param model The model object containing information about the user.
     * @return The CommandResultText.
     * @throws DukeDuplicateRouteException If there is a duplicate route.
     * @throws RouteNodeDuplicateException If there is a duplicate route node.
     */
    @Override
    public CommandResultText execute(Model model) throws DukeDuplicateRouteException, RouteNodeDuplicateException,
            FileNotSavedException, CorruptedFileException {
        RouteList routes = model.getRoutes();
        routes.add(new Route(name, ""));
        model.save();
        return new CommandResultText(MESSAGE_ADDITION + name);
    }
}
