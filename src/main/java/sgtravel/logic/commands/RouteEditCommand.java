package sgtravel.logic.commands;

import sgtravel.commons.Messages;
import sgtravel.commons.exceptions.FileNotSavedException;
import sgtravel.commons.exceptions.OutOfBoundsException;
import sgtravel.commons.exceptions.UnknownFieldException;
import sgtravel.logic.commands.results.CommandResultText;
import sgtravel.model.Model;
import sgtravel.model.transports.Route;

/**
 * Edits a Route in RouteList.
 */
public class RouteEditCommand extends Command {
    private int index;
    private String field;
    private String newValue;

    /**
     * Creates a new RouteEditCommand with the given parameters.
     *
     * @param index The index of Route in RouteList.
     * @param field The variable of Route to edit.
     * @param newValue The value to assign
     */
    public RouteEditCommand(int index, String field, String newValue) {
        this.index = index;
        this.field = field;
        this.newValue = newValue;
    }

    /**
     * Executes this command on the given Route.
     *
     * @param model The model object containing information about the user.
     * @return The CommandResultText.
     * @throws FileNotSavedException If the file is not saved.
     * @throws UnknownFieldException If the queried field is not valid.
     * @throws OutOfBoundsException If the query is out of bounds.
     */
    @Override
    public CommandResultText execute(Model model) throws FileNotSavedException, UnknownFieldException,
            OutOfBoundsException {
        try {
            Route route = model.getRoutes().get(index);
            editField(route);
            model.save();
            return new CommandResultText(Messages.ROUTE_NODE_EDIT_SUCCESS);
        } catch (IndexOutOfBoundsException e) {
            throw new OutOfBoundsException();
        }
    }

    /**
     * Edits the field of the Route.
     *
     * @param route The Route to edit.
     * @throws UnknownFieldException If the field is unknown.
     */
    private void editField(Route route) throws UnknownFieldException {
        switch (field.toLowerCase()) {
        case "name":
            route.setName(newValue);
            break;
        case "description":
            route.setDescription(newValue);
            break;
        default:
            throw new UnknownFieldException();
        }
    }
}
