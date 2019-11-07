package sgtravel.logic.commands;

import sgtravel.commons.exceptions.FileNotSavedException;
import sgtravel.commons.exceptions.OutOfBoundsException;
import sgtravel.logic.commands.results.CommandResultText;
import sgtravel.model.Model;
import sgtravel.model.lists.RouteList;
import sgtravel.model.transports.Route;

/**
 * Deletes a Route from RouteList.
 */
public class RouteDeleteCommand extends Command {
    private int index;
    private static final String MESSAGE_DELETION = "Got it. I've deleted this Route:\n  ";

    /**
     * Deletes a Route at the given index in Route List.
     *
     * @param index The index of the Route.
     */
    public RouteDeleteCommand(int index) {
        this.index = index;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     * @return The CommandResultText.
     * @throws FileNotSavedException If the file cannot be saved.
     * @throws OutOfBoundsException If the query is out of bounds.
     */
    @Override
    public CommandResultText execute(Model model) throws FileNotSavedException,
            OutOfBoundsException {
        try {
            RouteList routes = model.getRoutes();
            Route route = routes.get(index);
            String routeName = route.getName();
            routes.remove(index);
            model.save();
            return new CommandResultText(MESSAGE_DELETION + routeName);
        } catch (IndexOutOfBoundsException e) {
            throw new OutOfBoundsException();
        }
    }
}
