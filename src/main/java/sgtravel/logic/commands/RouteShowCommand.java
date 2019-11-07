package sgtravel.logic.commands;

import sgtravel.commons.exceptions.OutOfBoundsException;
import sgtravel.logic.commands.results.CommandResultMap;
import sgtravel.model.Model;

/**
 * Creates a MapWindow of a Route for visualization.
 */
public class RouteShowCommand extends Command {
    private int index;

    /**
     * Creates a new RouteShowCommand with the given parameters.
     *
     * @param index The index of the Route in RouteList.
     */
    public RouteShowCommand(int index) {
        this.index = index;
    }

    /**
     * Executes this command on the given Route and user interface.
     *
     * @param model The model object containing information about the user.
     * @return The CommandResultRouteMap.
     * @throws OutOfBoundsException If the query is out of bounds.
     */
    @Override
    public CommandResultMap execute(Model model) throws OutOfBoundsException {
        try {
            return new CommandResultMap(model.getRoute(index));
        } catch (IndexOutOfBoundsException e) {
            throw new OutOfBoundsException();
        }
    }
}
