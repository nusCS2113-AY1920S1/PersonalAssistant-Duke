package sgtravel.logic.commands;

import sgtravel.commons.exceptions.OutOfBoundsException;
import sgtravel.logic.commands.results.CommandResultText;
import sgtravel.model.Model;

/**
 * Lists a given Route in RouteList.
 */
public class RouteListCommand extends Command {
    private int index;

    /**
     * Creates a new RouteListCommand with the given index.
     *
     * @param index The index of the Route in RouteList.
     */
    public RouteListCommand(int index) {
        this.index = index;
    }

    /**
     * Executes this command on the given Route List and user interface.
     *
     * @param model The model object containing information about the user.
     * @return The CommandResultText.
     * @throws OutOfBoundsException If the query is out of bounds.
     */
    @Override
    public CommandResultText execute(Model model) throws OutOfBoundsException {
        try {
            return new CommandResultText(model.getRoutes().get(index));
        } catch (IndexOutOfBoundsException e) {
            throw new OutOfBoundsException();
        }
    }
}
